package me.leopold95.vulcano.core;

import me.leopold95.vulcano.Vulcano;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EventManager {
    private Vulcano plugin;
    public EventManager(Vulcano plugin){
        this.plugin = plugin;
    }

    private BossBar eventBossBar = null;
    private Location eventLocation = null;
    private Double eventVisibleRadius = null;

    public void  beginEvent(String[] position, Player admin){
        if(eventBossBar != null){
            admin.sendMessage(Config.getMessage("event-exists"));
            return;
        }

        if(VulcanItemConfig.itemsCount() == 0){
            admin.sendMessage(Config.getMessage("event-bad-item-count"));
            return;
        }

        if(!VulcanItemConfig.checkAllPercents()){
            admin.sendMessage(Config.getMessage("event-bad-item-percents"));
            return;
        }

        int x = Integer.parseInt(position[0]);
        int z = Integer.parseInt(position[2]);
        int y = Integer.parseInt(position[1]);
        String worldName = position[3];
        eventLocation = new Location(Bukkit.getWorld(worldName), x, y, z);

        for(Player player : Bukkit.getOnlinePlayers()){
            String messageBegin = Config.getMessage("event-global-begging").
                    replace("%x%", String.valueOf(eventLocation.getX())).
                    replace("%y%", String.valueOf(eventLocation.getY())).
                    replace("%z%", String.valueOf(eventLocation.getZ()));
            player.sendMessage(messageBegin);
        }

        String bbTitle = Config.getString("boss-bar-title");
        eventVisibleRadius = Config.getDouble("boss-bar-radius");
        eventBossBar = Bukkit.createBossBar(bbTitle, BarColor.valueOf(Config.getString("boss-bar-color")), BarStyle.SOLID);

        beginEventTask(eventLocation);
    }

    private void beginEventTask(Location animationLocation){
        new RepeatingTask(Vulcano.getPlugin(), 0, 20) {
            int taskTicks = 0;
            final int duration = Config.getInt("event-animation-duration");
            final int randomDropCount = Config.getInt("random-items-drop-count");
            final int itemsDropRadius = Config.getInt("dropping-radius");

            @Override
            public void run() {
                taskTicks++;

                updatePlayersBossBar(animationLocation, eventVisibleRadius);
                playSound(animationLocation);
                playAnimation(animationLocation);

//                dropPoints(animationLocation, itemsDropRadius);
//                dropMoney(animationLocation, itemsDropRadius);

                if(taskTicks == duration){
                    eventBossBar.removeAll();
                    eventBossBar = null;
                    eventLocation = null;
                    eventVisibleRadius = null;
                    dropFinalItem(animationLocation, itemsDropRadius, randomDropCount);
                    canncel();
                }
            }
        };
    }

    private void updatePlayersBossBar(Location center, double radius){
        for (Player player: Bukkit.getOnlinePlayers()){
            if(player.getLocation().distance(center) <= radius){
                if(!eventBossBar.getPlayers().contains(player))
                    eventBossBar.addPlayer(player);
            }
            else {
                eventBossBar.removePlayer(player);
            }
        }
    }

    private void playSound(Location location){
        try {
            String soundStr = Config.getString("vulcan-tick-sound");
            int soundVolume = Config.getInt("vulcan-tick-sound-volume");

            for(Player player : eventBossBar.getPlayers()){
                player.playSound(location, Sound.valueOf(soundStr), soundVolume, 1);
            }
        }
        catch (Exception exp){
            Bukkit.getConsoleSender().sendMessage(Config.getMessage("global-sound-bad").replace("%sound%", exp.getMessage()));
        }
    }

    private void playAnimation(Location location){
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

//    private static void dropMoney(Location location, int radius){
//        int randomPos1 = new Random().nextInt((radius) + 1);
//        int randomPos2 = new Random().nextInt((radius) + 1);
//
//        Location loc = location.clone();
//        loc.setX(loc.getX() + randomPos1);
//        loc.setZ(loc.getZ() + randomPos2);
//        loc.setY(loc.getY() + 3);
//
//        location.getWorld().dropItemNaturally(loc, Items.createMoneyItem());
//    }
//
//    private static void dropPoints(Location location, int radius){
//        int randomPos1 = new Random().nextInt((radius) + 1);
//        int randomPos2 = new Random().nextInt((radius) + 1);
//
//        Location loc = location.clone();
//        loc.setX(loc.getX() + randomPos1);
//        loc.setZ(loc.getZ() + randomPos2);
//        loc.setY(loc.getY() + 3);
//
//        location.getWorld().dropItemNaturally(location, Items.createPlayerPointsItem());
//    }

    private void dropFinalItem(Location location, int dropRadius,  int randomCount){
        for(int i = 1; i <= randomCount; i++){
            ItemStack item = VulcanItemConfig.randomItem();
            Location dropRandomLocation = getRandomLocation(location, dropRadius);
            location.getWorld().dropItemNaturally(dropRandomLocation, item);
        }
    }

    private Location getRandomLocation(Location originalLocation, int radius) {
        Random random = new Random();

        // Generate random angle and distance
        double angle = random.nextDouble() * 2 * Math.PI; // Random angle between 0 and 2*PI
        double distance = random.nextDouble() * radius; // Random distance within the radius

        // Calculate new x and z based on the angle and distance
        double xOffset = distance * Math.cos(angle);
        double zOffset = distance * Math.sin(angle);

        // Create new location with the random offsets
        Location randomLocation = originalLocation.clone();
        randomLocation.add(xOffset, 0, zOffset);

        return randomLocation;
    }
}
