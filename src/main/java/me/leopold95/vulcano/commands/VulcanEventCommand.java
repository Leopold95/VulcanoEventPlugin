package me.leopold95.vulcano.commands;

import me.leopold95.vulcano.Vulcano;
import me.leopold95.vulcano.core.Config;
import me.leopold95.vulcano.core.EventManager;
import me.leopold95.vulcano.core.Items;
import me.leopold95.vulcano.core.VulcanItemConfig;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class VulcanEventCommand implements CommandExecutor {
    private Vulcano plugin;

    public VulcanEventCommand(Vulcano plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("");
            return false;
        }

        Player player = (Player) sender;

        //setposition argument
        if(args[0].equals("setposition")){
            //hash values
            int x = player.getLocation().getBlockX();
            int y = player.getLocation().getBlockY();
            int z = player.getLocation().getBlockZ();
            String worldName = player.getWorld().getName();

            //save next event location
            Config.setConfig("next-event-location", x + " " + y + " " + z + " " + worldName);

            //get next event location
            String[] location = Config.getString("next-event-location").split(" ");

            //notify sender about next event location
            String message = Config.getMessage("event-coords-is");
            sender.sendMessage(message + ": " + location[0] + " " + location[1] + " " + location[2] + " " + location[3]);

            return true;
        }

        //start argument
        if(args[0].equals("start")){
            String strEventLocation;

            try {
                strEventLocation = Config.getString("next-event-location");
            }
            catch (Exception e){
                strEventLocation = null;
            }

            if(strEventLocation == null || strEventLocation.isEmpty()){
                String message = Config.getMessage("event-coords-bad");
                sender.sendMessage(message);
                return true;
            }

            //get next event location
            String[] location = Config.getString("next-event-location").split(" ");

            //begin event
            plugin.getVulcanEvent().beginEvent(location, player);
            return true;
        }

        if(args[0].equals("itemadd")){
            ItemStack item = player.getInventory().getItemInMainHand();

            try {
                VulcanItemConfig.addItem(item, player);
            }
            catch (Exception e){
                e.printStackTrace();
                player.sendMessage(Config.getMessage("add-event-item-bad"));
                Bukkit.getConsoleSender().sendMessage(e.getMessage());
            }

            return true;
        }

        if(args[0].equals("itemremove")){
            ItemStack item = player.getInventory().getItemInMainHand();

            try {
                VulcanItemConfig.removeItem(item, player);
            }
            catch (Exception e){
                e.printStackTrace();
                player.sendMessage(Config.getMessage("event-item-remove-bad"));
                Bukkit.getConsoleSender().sendMessage(e.getMessage());
            }

            return true;
        }

        if(args[0].equals("giveplayerpoints")){
            player.getInventory().addItem(Items.createPlayerPointsItem());
            return true;
        }

        if(args[0].equals("givemoney")){
            player.getInventory().addItem(Items.createMoneyItem());
            return true;
        }

        return false;
    }
}
