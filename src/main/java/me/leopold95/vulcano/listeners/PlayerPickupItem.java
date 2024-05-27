package me.leopold95.vulcano.listeners;

import me.leopold95.vulcano.Vulcano;
import me.leopold95.vulcano.core.Config;
import me.leopold95.vulcano.core.Keys;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Sound;
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

        if(event.getItem().getItemStack().getItemMeta().getPersistentDataContainer().has(Keys.PLAYER_POINTS_ITEM, PersistentDataType.STRING));{
            event.setCancelled(true);
            event.getItem().remove();

            Player player = (Player) event.getEntity();
            int pointsAmount = Config.getInt("payer-points-item-cost");
            Vulcano.getPlugin().getPpAPI().give(player.getUniqueId(), pointsAmount);

            String pickedMessage = Config.getMessage("player-points-pickedup")
                    .replace("%amount%", String.valueOf(pointsAmount))
                    .replace("%global%", String.valueOf(plugin.getPpAPI().look(player.getUniqueId())));

            player.sendMessage(pickedMessage);

            try {
                String sound = Config.getString("payer-points-pickedup-sound");
                int volume = Config.getInt("payer-points-pickedup-sound-volume");
                player.playSound(player.getLocation(), Sound.valueOf(sound), volume, 1);
            }
            catch (Exception ignored){ }
        }

        if(event.getItem().getPersistentDataContainer().has(Keys.PLAYER_MONEY_ITEM, PersistentDataType.STRING)){
            event.setCancelled(true);
            event.getItem().remove();

            Player player = (Player) event.getEntity();
            double cost = Config.getDouble("gold-drop-cost");
            EconomyResponse r = plugin.getEconomy().depositPlayer(player, cost);
            if(r.transactionSuccess()) {
                player.sendMessage(String.format("You were given %s and now have %s", plugin.getEconomy().format(r.amount), plugin.getEconomy().format(r.balance)));
            } else {
                player.sendMessage(String.format("An error occured: %s", r.errorMessage));
            }
            return;
        }
    }
}
