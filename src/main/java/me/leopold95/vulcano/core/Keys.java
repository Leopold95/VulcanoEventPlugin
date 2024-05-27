package me.leopold95.vulcano.core;

import me.leopold95.vulcano.Vulcano;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public class Keys {
    private Vulcano plugin;
    public Keys (Vulcano plugin){
        this.plugin = plugin;
    }

    public final NamespacedKey PLAYER_POINTS_ITEM = new NamespacedKey(plugin, "PLAYER_POINTS_ITEM");
}
