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
        NamespacedKey costKey = Keys.POINTS_ITEM_COST;
        int cost = Config.getInt("payer-points-item-cost");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, key.getKey());
        meta.getPersistentDataContainer().set(costKey, PersistentDataType.INTEGER, cost);

        meta.setDisplayName("Поинтов содержится: " + cost);

        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createMoneyItem(){
        ItemStack item = new ItemStack(Material.GOLD_NUGGET, 1);

        ItemMeta meta = item.getItemMeta();

        NamespacedKey key = Keys.PLAYER_MONEY_ITEM;
        NamespacedKey costKey = Keys.MONEY_ITEM_COST;
        double cost = Config.getDouble("money-drop-cost");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, key.getKey());
        meta.getPersistentDataContainer().set(costKey, PersistentDataType.DOUBLE, cost);

        meta.setDisplayName("Денег содержится: " + cost);

        item.setItemMeta(meta);
        return item;
    }
}
