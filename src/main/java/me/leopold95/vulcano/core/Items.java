package me.leopold95.vulcano.core;

import me.leopold95.vulcano.Vulcano;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class Items {
    public static ItemStack createPlayerPointsItem(){
        ItemStack item = new ItemStack(Material.GHAST_TEAR, 1);

        ItemMeta meta = item.getItemMeta();

        NamespacedKey key = Keys.PLAYER_POINTS_ITEM;
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, key.getKey());

        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createMoneyItem(){
        ItemStack item = new ItemStack(Material.GOLD_NUGGET, 1);

        ItemMeta meta = item.getItemMeta();

        NamespacedKey key = Keys.PLAYER_MONEY_ITEM;
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, key.getKey());

        item.setItemMeta(meta);
        return item;
    }
}
