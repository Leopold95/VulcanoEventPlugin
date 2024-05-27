package me.leopold95.vulcano.listeners;

import me.leopold95.vulcano.Vulcano;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.persistence.PersistentDataType;

public class PlayerPickupItem implements Listener {
    private Vulcano plugin;
    public PlayerPickupItem(Vulcano plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerPickedupItem(EntityPickupItemEvent event){
        if(!(event.getEntity() instanceof Player))
            return;

        if(!event.getItem().getPersistentDataContainer().has(plugin.getKeys().PLAYER_POINTS_ITEM, PersistentDataType.STRING));{

        }
    }
}
