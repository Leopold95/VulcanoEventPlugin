package me.leopold95.vulcano;

import me.leopold95.vulcano.commands.VulcanEventCommand;
import me.leopold95.vulcano.commands.VulcanEventTabComplete;
import me.leopold95.vulcano.core.Config;
import me.leopold95.vulcano.core.Keys;
import me.leopold95.vulcano.utils.Utils;
import org.black_ixx.playerpoints.PlayerPoints;
import org.black_ixx.playerpoints.PlayerPointsAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.security.Key;
import java.util.Objects;

public final class Vulcano extends JavaPlugin {
    private Utils utils;
    private PlayerPointsAPI ppAPI;
    private Keys keys;

    private static Vulcano plugin;

    @Override
    public void onEnable() {
        Objects.requireNonNull(getCommand("vulcanevent")).setExecutor(new VulcanEventCommand());
        getCommand("vulcanevent").setTabCompleter(new VulcanEventTabComplete());
        utils = new Utils(this);
        keys = new Keys(this);
        Config.register(this);
        plugin = this;

        //PlayerPointsAPI init
        if (Bukkit.getPluginManager().isPluginEnabled("PlayerPoints")) {
            ppAPI = PlayerPoints.getInstance().getAPI();
        }
    }

    public PlayerPointsAPI getPpAPI(){
        if (ppAPI == null){
            Bukkit.getConsoleSender().sendMessage(Config.getMessage("player-points-api-error"));
            throw new RuntimeException(Config.getMessage("player-points-api-error"));
        }
        return ppAPI;
    }

    public Utils getUtils(){
        return utils;
    }

    public Keys getKeys() {
        return keys;
    }

    public static Vulcano getPlugin(){
        return plugin;
    }
    @Override
    public void onDisable() {
        plugin = null;
    }
}
