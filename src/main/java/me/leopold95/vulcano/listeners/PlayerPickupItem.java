package me.leopold95.vulcano.listeners;

import me.leopold95.vulcano.Vulcano;
import me.leopold95.vulcano.core.Config;
import me.leopold95.vulcano.core.Keys;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
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

        ItemMeta itemMeta = event.getItem().getItemStack().getItemMeta();

        if(itemMeta == null)
            return;

        if(itemMeta.getPersistentDataContainer().has(Keys.PLAYER_POINTS_ITEM, PersistentDataType.STRING)){
            event.setCancelled(true);
            event.getItem().remove();

            Player player = (Player) event.getEntity();
            int pointsAmount = itemMeta.getPersistentDataContainer().get(Keys.POINTS_ITEM_COST, PersistentDataType.INTEGER);
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
            catch (Exception exp){
                Bukkit.getConsoleSender().sendMessage(Config.getMessage("global-sound-bad").replace("%sound%", exp.getMessage()));
            }
        }

        if(itemMeta.getPersistentDataContainer().has(Keys.PLAYER_MONEY_ITEM, PersistentDataType.STRING)){
            event.setCancelled(true);
            event.getItem().remove();

            Player player = (Player) event.getEntity();
            double cost = itemMeta.getPersistentDataContainer().get(Keys.MONEY_ITEM_COST, PersistentDataType.DOUBLE);

            EconomyResponse r = plugin.getEconomy().depositPlayer(player, cost);

            if(r.transactionSuccess()) {
                String pickedMessage = Config.getMessage("money-picked-up")
                        .replace("%amount%", String.valueOf(plugin.getEconomy().format(r.amount)))
                        .replace("%global%", String.valueOf(plugin.getEconomy().format(r.balance)));

                player.sendMessage(pickedMessage);

                try {
                    String sound = Config.getString("money-pickedup-sound");
                    int volume = Config.getInt("money-pickedup-sound-volume");
                    player.playSound(player.getLocation(), Sound.valueOf(sound), volume, 1);
                }
                catch (Exception exp){
                    Bukkit.getConsoleSender().sendMessage(Config.getMessage("global-sound-bad").replace("%sound%", exp.getMessage()));
                }

                //player.sendMessage(String.format("You were given %s and now have %s", plugin.getEconomy().format(r.amount), plugin.getEconomy().format(r.balance)));
            } else {
                Bukkit.getConsoleSender().sendMessage(Config.getMessage("money-picked-up-bad"));
            }
        }
    }
}
