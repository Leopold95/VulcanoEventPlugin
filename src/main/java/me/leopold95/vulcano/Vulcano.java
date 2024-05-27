package me.leopold95.vulcano;

import me.leopold95.vulcano.commands.VulcanEventCommand;
import me.leopold95.vulcano.commands.VulcanEventTabComplete;
import me.leopold95.vulcano.core.Config;
import me.leopold95.vulcano.core.Keys;
import me.leopold95.vulcano.core.VulcanItemConfig;
import me.leopold95.vulcano.listeners.PlayerPickupItem;
import me.leopold95.vulcano.utils.Utils;
import net.milkbowl.vault.economy.Economy;
import org.black_ixx.playerpoints.PlayerPoints;
import org.black_ixx.playerpoints.PlayerPointsAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.security.Key;
import java.util.Objects;

public final class Vulcano extends JavaPlugin {
    private Utils utils;

    private PlayerPointsAPI ppAPI;
    private Economy economy;

    private static Vulcano plugin;

    @Override
    public void onEnable() {
        plugin = this;
        getCommand("vulcanevent").setExecutor(new VulcanEventCommand());
        getCommand("vulcanevent").setTabCompleter(new VulcanEventTabComplete());

        getServer().getPluginManager().registerEvents(new PlayerPickupItem(this), this);

        Config.register(this);
        VulcanItemConfig.register(this);
        utils = new Utils(this);
        setupEconomy();

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

    //code from official wiki
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }

    public Economy getEconomy(){
        return economy;
    }

    public Utils getUtils(){
        return utils;
    }

    public static Vulcano getPlugin(){
        return plugin;
    }
    @Override
    public void onDisable() {
        plugin = null;
    }
}
