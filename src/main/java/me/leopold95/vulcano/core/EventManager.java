package me.leopold95.vulcano.core;

import me.leopold95.vulcano.Vulcano;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class EventManager {
    private static BossBar eventBossBar;
    private static int duration;
    private static int timeRemaining;

    public static void beginEvent(String[] position, Player admin){
        int x = Integer.parseInt(position[0]);
        int y = Integer.parseInt(position[1]);
        int z = Integer.parseInt(position[2]);
        String worldName = position[3];

        Location eventLocation = new Location(Bukkit.getWorld(worldName), x, y, z);

        int animationDuration = Config.getInt("event-animation-duration");

        for(Player player : Bukkit.getOnlinePlayers()){
            player.sendMessage(Config.getMessage("event-global-begging"));
        }

        beginEventTask(eventLocation);

        Bukkit.getScheduler().runTaskLater(Vulcano.getPlugin(), () -> {

        }, 20*10);


        Bukkit.getScheduler().runTaskTimer(Vulcano.getPlugin(), () -> {
            eventLocation.getWorld().dropItemNaturally(eventLocation, Items.createPlayerPointsItem());
        }, 0, 20);




        String message = Config.getMessage("event-begging");
        admin.sendMessage(message + ": " + position);
    }

    private static void beginEventTask(Location animationLocation){
        Bukkit.getScheduler().runTaskTimer(Vulcano.getPlugin(), new BukkitRunnable() {
            int taskTicks = 0;

            @Override
            public void run() {
                taskTicks++;

                playAnimation(animationLocation);
                dropPoints(animationLocation);
                dropMoney(animationLocation);

                if(taskTicks >= 100)
                    cancel();
            }
        },0, 20);
    }

    private static void playAnimation(Location location){

    }

    private static void dropMoney(Location location){

    }

    private static void dropPoints(Location location){

    }
}
