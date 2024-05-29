package me.leopold95.vulcano.core;

import me.leopold95.vulcano.utils.NewPair;
import me.leopold95.vulcano.utils.Pair;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class VulcanItemConfig {
    private static final String VULCAN_ITEMS_LIST_NAME = "vulcan-items-list";
    private static final String VULCAN_ITEMS_LIST_CHANCE = "vulcan-item-chance";

    private static File filee;
    private static FileConfiguration config;

    public  static ItemStack randomItem(){
        ItemStack item = new ItemStack(Material.AIR, 1);

        List<ItemStack> elements = loadItems();
        List<Integer> chances = loadPercents();

        if (elements.size() != chances.size()) {
            Bukkit.getConsoleSender().sendMessage(Config.getMessage("event-bad-item-chacnces"));
            return item;
        }

        // Create a cumulative distribution list
        List<Integer> cumulativeChances = new ArrayList<>();
        int sum = 0;
        for (int chance : chances) {
            sum += chance;
            cumulativeChances.add(sum);
        }

        // Generate a random number between 1 and 100
        Random random = new Random();
        int randomValue = random.nextInt(100) + 1;

        // Find the corresponding element based on the random value
        for (int i = 0; i < cumulativeChances.size(); i++) {
            if (randomValue <= cumulativeChances.get(i)) {
                return elements.get(i);
            }
        }

        return item;
    }

    public static void addItem(ItemStack item, Player admin) throws IOException {
        List<ItemStack> items = loadItems();

        if(items.contains(item)){
            admin.sendMessage(Config.getMessage("add-event-item-exists"));
            return;
        }

        List<Integer> itemPercents = loadPercents();

        items.add(item);
        itemPercents.add(0);

        saveItems(items, itemPercents);

        admin.sendMessage(Config.getMessage("event-item-saved"));
    }

    public static void removeItem(ItemStack item, Player admin) throws IOException {
        List<ItemStack> items = loadItems();

        if(items.contains(item)){
            List<Integer> itemPercents = loadPercents();

            List<ItemStack> itemsCopy = new ArrayList<>();
            List<Integer> percentsCopy = new ArrayList<>();

            for(int i = 0; i < items.size(); i++){
                //add item to temp updated list if
                if(!items.get(i).equals(item)){
                    itemsCopy.add(items.get(i));
                    percentsCopy.add(itemPercents.get(i));
                }
            }

            //save items config and save percents map section
            saveItems(itemsCopy, percentsCopy);

            admin.sendMessage(Config.getMessage("event-item-removed"));
        }
    }

    private static List<ItemStack> loadItems() {
        ConfigurationSection section = config.getConfigurationSection(VULCAN_ITEMS_LIST_NAME);
        List<ItemStack> items = new ArrayList<>();

        if (section == null) {
            return items; // Return an empty list if no items were saved
        }

        for (String key : section.getKeys(false)) {
            ItemStack item = section.getItemStack(key);
            if (item != null) {
                items.add(item);
            }
        }

        return items;
    }

    private static List<Integer> loadPercents() {
        ConfigurationSection section = config.getConfigurationSection(VULCAN_ITEMS_LIST_CHANCE);
        List<Integer> percents = new ArrayList<>();

        if (section == null) {
            return percents; // Return an empty list if no items were saved
        }

        for (String key : section.getKeys(false)) {
            percents.add(section.getInt(key));
        }

        System.out.println("percents read as: " + percents);

        return percents;
    }

    private static void saveItems(List<ItemStack> items, List<Integer> percents) throws IOException {
        ConfigurationSection section = config.createSection(VULCAN_ITEMS_LIST_NAME);
        ConfigurationSection section2 = config.createSection(VULCAN_ITEMS_LIST_CHANCE);

        for (int i = 0; i < items.size(); i++) {
            section.set(String.valueOf(i), items.get(i));
            section2.set(String.valueOf(i), percents.get(i));
        }

        System.out.println("percents saves as:" + percents);

        config.save(filee);
    }

    public static boolean checkAllPercents(){
        int sum = 0;

        for(Integer i: loadPercents()){
            sum += i;
        }

        if(sum == 100)
            return true;

        return false;
    }

//    public static void addVulcanItem(ItemStack item) throws IOException{
//        ArrayList<ItemStack> list;
//        if(!existsVulcanoList(VULCAN_ITEMS_LIST_NAME)){
//            list = new ArrayList<ItemStack>();
//        }
//        else {
//            list = vulcanItemsList();
//        }
//        list.add(item);
//        setItemsList(VULCAN_ITEMS_LIST_NAME, list);
//    }
//
//    public static void removeVulcanItem(ItemStack item){
//
//    }
//
//    public static ArrayList<ItemStack> vulcanItemsList(){
//        ArrayList<ItemStack> items = new ArrayList<>();
//
//        List<String> serializedStringItemsList = itemsConfig.getStringList(VULCAN_ITEMS_LIST_NAME);
//
//        for(String info: serializedStringItemsList){
//            items.add(ItemStack.deserialize(info));
//        }
//
//        return items;
//    }
//
//    public static void setItemsList(String path, Object object) throws IOException {
//        itemsConfig.set(path, object);
//        itemsConfig.save(itemsFile);
//    }


//    public static boolean existsVulcanoList(String path){
//        return itemsConfig.contains(path);
//    }
//    public static boolean existsVulcanoItem(String path){
//        return itemsConfig.contains(path);
//    }

    public static void register(JavaPlugin plugin) {
        createItemsConfig("vulcanoitems.yml", plugin);
    }

    private static void createItemsConfig(String file, JavaPlugin plugin) {
        filee = new File(plugin.getDataFolder(), file);
        if (!filee.exists()) {
            filee.getParentFile().mkdirs();
            plugin.saveResource(file, false);
        }
        config = YamlConfiguration.loadConfiguration(filee);
        try {
            config.save(filee);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}