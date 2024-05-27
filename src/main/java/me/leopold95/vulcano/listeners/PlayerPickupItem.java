package me.leopold95.vulcano.listeners;

import me.leopold95.vulcano.Vulcano;
import me.leopold95.vulcano.core.Config;
import me.leopold95.vulcano.core.Keys;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.persistence.PersistentDataType;

import java.security.Key;

public class PlayerPickupItem implements Listener {
    private Vulcano plugin;
    public PlayerPickupItem(Vulcano plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerPickedupItem(EntityPickupItemEvent event){
        if(!(event.getEntity() instanceof Player))
            return;

        if(event.getItem().getItemStack().getItemMeta().getPersistentDataContainer().has(Keys.PLAYER_POINTS_ITEM, PersistentDataType.STRING));{
            event.setCancelled(true);
            Player player = (Player) event.getEntity();
            int pointsAmount = Config.getInt("payer-points-item-cost");
            Vulcano.getPlugin().getPpAPI().give(player.getUniqueId(), pointsAmount);
            //Vulcano.getPlugin().getPpAPI().look(
            event.getItem().remove();


        }

        if(event.getItem().getPersistentDataContainer().has(Keys.PLAYER_MONEY_ITEM, PersistentDataType.STRING));{

        }
    }
}
