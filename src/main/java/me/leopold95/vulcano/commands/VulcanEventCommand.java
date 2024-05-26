package me.leopold95.vulcano.commands;

import me.leopold95.vulcano.Vulcano;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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

        if(args.length == 0){
            sender.sendMessage("");
            return false;
        }

        Player player = (Player) sender;

        if(args[0].equals("setposition")){
            int x = player.getLocation().getBlockX();
            int y = player.getLocation().getBlockY();
            int z = player.getLocation().getBlockZ();

            //plugin.getUtils().setNextEventLocation(x, y, z);
            plugin.getCustomConfig().setConfig("next-event-location", x + " " + y + " " + z);
            String message = plugin.getCustomConfig().getMessage("event-coords-is");
            sender.sendMessage(message + ": " + x + " " + y + " " + z);

            return true;
        }

        if(args[0].equals("start")){
            String strEventLocation;

            try {
                strEventLocation = plugin.getCustomConfig().getString("next-event-location");
            }
            catch (Exception e){
                strEventLocation = null;
            }

            if(strEventLocation == null || strEventLocation.isEmpty()){
                String message = plugin.getCustomConfig().getMessage("event-coords-bad");
                sender.sendMessage(message);
                return true;
            }

            String message = plugin.getCustomConfig().getMessage("event-begging");
            sender.sendMessage(message + ": " + strEventLocation);

            return true;
        }



        sender.sendMessage("u send command");


        return true;
    }
}
