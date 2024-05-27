package me.leopold95.vulcano.core;

import me.leopold95.vulcano.Vulcano;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Random;

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

        for(Player player : Bukkit.getOnlinePlayers()){
            player.sendMessage(Config.getMessage("event-global-begging"));
        }

        beginEventTask(eventLocation);
    }

    private static void beginEventTask(Location animationLocation){
        int duration = Config.getInt("event-animation-duration");
        int radius = Config.getInt("dropping-radius");


        new RepeatingTask(Vulcano.getPlugin(), 0, 20) {
            int taskTicks = 0;
            @Override
            public void run() {
                taskTicks++;

                playAnimation(animationLocation);
                dropPoints(animationLocation, radius);
                dropMoney(animationLocation, radius);

                if(taskTicks == duration)
                    canncel();
            }
        };
    }

    private static void playAnimation(Location location){
        double radius = 0.7; // Radius of the circle
        double yIncrement = 0.4; // Increment for the y-coordinate

        for (int i = 0; i < 360; i += 10) { // Create a circle of particles
            double angle = Math.toRadians(i);
            double x = location.getX() + radius * Math.cos(angle);
            double z = location.getZ() + radius * Math.sin(angle);
            for (double y = location.getY(); y < location.getY() + 2; y += yIncrement) { // Make the particles go upwards
                location.getWorld().spawnParticle(Particle.LAVA, x, y, z, 0, 0.1, 0.1, 0.1, 0.05);
            }
        }
    }

    private static void dropMoney(Location location, int radius){
        int randomPos1 = new Random().nextInt((radius) + 1);
        int randomPos2 = new Random().nextInt((radius) + 1);

        Location loc = location.clone();
        loc.setX(loc.getX() + randomPos1);
        loc.setZ(loc.getZ() + randomPos2);
        loc.setY(loc.getY() + 3);

        location.getWorld().dropItemNaturally(loc, Items.createMoneyItem());
    }

    private static void dropPoints(Location location, int radius){
        int randomPos1 = new Random().nextInt((radius) + 1);
        int randomPos2 = new Random().nextInt((radius) + 1);

        Location loc = location.clone();
        loc.setX(loc.getX() + randomPos1);
        loc.setZ(loc.getZ() + randomPos2);
        loc.setY(loc.getY() + 3);

        location.getWorld().dropItemNaturally(location, Items.createPlayerPointsItem());
    }
}
