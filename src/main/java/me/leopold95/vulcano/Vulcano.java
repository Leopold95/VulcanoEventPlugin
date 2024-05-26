package me.leopold95.vulcano;

import me.leopold95.vulcano.commands.VulcanEventCommand;
import me.leopold95.vulcano.commands.VulcanEventTabComplete;
import me.leopold95.vulcano.utils.Config;
import me.leopold95.vulcano.utils.Utils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Vulcano extends JavaPlugin {
    private Config config;
    private Utils utils;

    @Override
    public void onEnable() {
        Objects.requireNonNull(getCommand("vulcanevent")).setExecutor(new VulcanEventCommand(this));
        getCommand("vulcanevent").setTabCompleter(new VulcanEventTabComplete());
        config = new Config(this);
        utils = new Utils(this);

//        try {
//            for(Pair pair : ConfigFiller.getConfigFills()){
//                config.setConfig(pair.path, pair.value);
//            }
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }
    public Config getCustomConfig(){
        return config;
    }

    public Utils getUtils(){
        return utils;
    }

    @Override
    public void onDisable() {
    }
}
