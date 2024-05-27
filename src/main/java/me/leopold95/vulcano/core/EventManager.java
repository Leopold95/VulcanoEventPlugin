package me.leopold95.vulcano.core;

import me.leopold95.vulcano.Vulcano;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class EventManager {
    private static BossBar eventBossBar;
    private static int duration;
    private static int timeRemaining;

    public static void beginEvent(String[] position, Player admin){
        int x = Integer.parseInt(position[0]);
        int y = Integer.parseInt(position[1]);
        int z = Integer.parseInt(position[2]);
        String worldName = position[3];

        int animationDuration = Config.getInt("event-animation-duration");

        for(Player player : Bukkit.getOnlinePlayers()){
            player.sendMessage(Config.getMessage("event-global-begging"));
        }





        String message = Config.getMessage("event-begging");
        admin.sendMessage(message + ": " + position);
    }

    private static void animationPlayer(Location animationLocation){

    }
}
