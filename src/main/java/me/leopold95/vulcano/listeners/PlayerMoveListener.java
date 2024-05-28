package me.leopold95.vulcano.listeners;

import me.leopold95.vulcano.core.EventManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {
    @EventHandler
    private void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        Location playerLocation = player.getLocation();

        if(EventManager.eventLocation == null || EventManager.eventVisibleRadius == null ||
                EventManager.eventBossBar == null)
            return;

        if (playerLocation.distance(EventManager.eventLocation) <= EventManager.eventVisibleRadius) { // Within 5 blocks
            EventManager.eventBossBar.addPlayer(player);
        } else {
            EventManager.eventBossBar.removePlayer(player);
        }

    }
}
