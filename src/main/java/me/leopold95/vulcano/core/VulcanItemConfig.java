package me.leopold95.vulcano.core;

import me.leopold95.vulcano.utils.NewPair;
import me.leopold95.vulcano.utils.Pair;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VulcanItemConfig {
    private static final String VULCAN_ITEMS_LIST_NAME = "vulcan-items-list";
    private static final String VULCAN_ITEMS_LIST_CHANCE = "vulcan-item-chance";

    private static File filee;
    private static FileConfiguration config;


    public static void addItem(ItemStack item, Player admin) throws IOException{
        List<ItemStack> items = loadItems(VULCAN_ITEMS_LIST_NAME);

        if(items.contains(item)){
            admin.sendMessage(Config.getMessage("add-event-item-exists"));
            return;
        }

        List<String> itemPercents = Config.getStringList(VULCAN_ITEMS_LIST_CHANCE);

        items.add(item);
        itemPercents.add((items.size() - 1) + ":0");

        Config.setConfig(VULCAN_ITEMS_LIST_CHANCE, itemPercents);

        admin.sendMessage("предмет доавблен в список. отредактиройте шанс выпадения в конфиге в Х");

        saveItems(items);
    }

    private static void saveItems(List<ItemStack> items) throws IOException {
        ConfigurationSection section = config.createSection(VULCAN_ITEMS_LIST_NAME);
        for (int i = 0; i < items.size(); i++) {
            section.set(String.valueOf(i), items.get(i));
        }
        config.save(filee);
    }

    public static void removeItem(ItemStack item, Player admin) throws IOException {
        List<ItemStack> items = loadItems(VULCAN_ITEMS_LIST_NAME);
        List<String> itemPercents = Config.getStringList(VULCAN_ITEMS_LIST_CHANCE);

        if(items.contains(item)){
            List<ItemStack> itemsCopy = new ArrayList<>();
            List<String> percentsCopy = new ArrayList<>();

            int newPercentsIndex = 0;

            for(int i = 0; i < items.size(); i++){
                if(items.get(i).equals(item)){
//                    items.remove(i);
//                    itemPercents.remove(i);
                }
                else {
                    itemsCopy.add(items.get(i));
                    String[] oldInfo = itemPercents.get(i).split(":");
                    percentsCopy.add(newPercentsIndex + ":" + oldInfo[1]);
                    newPercentsIndex++;
                }
            }

            Bukkit.getConsoleSender().sendMessage(String.valueOf(itemsCopy.size()));
            Bukkit.getConsoleSender().sendMessage(String.valueOf(percentsCopy.size()));

            saveItems(itemsCopy);
            Config.setConfig(VULCAN_ITEMS_LIST_CHANCE, percentsCopy);

            admin.sendMessage("item removed");
        }
    }

    private static List<ItemStack> loadItems(String path) {
        ConfigurationSection section = config.getConfigurationSection(path);
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