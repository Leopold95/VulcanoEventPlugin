package me.leopold95.vulcano.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Config {
    private File messagesConfigFile;
    private FileConfiguration messagesConfig;
    private File configFile;
    private FileConfiguration config;


    private final JavaPlugin plugin;

    public Config(JavaPlugin plugin){
        this.plugin = plugin;
        register();
    }

    public boolean existsConfig(String path){
        return config.contains(path);
    }

    public boolean existsMessage(String path){
        return config.contains(path);
    }

    public void setConfig(String path, Object object) {
        try {
            config.set(path, object);
            config.save(configFile);
        }
        catch (Exception exp){
            exp.printStackTrace();
        }
    }

    public void setMessage(String path, Object object) throws IOException {
        messagesConfig.set(path, object);
        messagesConfig.save(messagesConfigFile);
    }

    public List<?> getList(String path) {
        return config.getList(path);
    }

    public String getString(String path) {
        return ChatColor.translateAlternateColorCodes('&', config.getString(path));
    }

    public int getInt(String path) {
        return config.getInt(path);
    }

    public boolean getBoolean(String path) {
        return config.getBoolean(path);
    }

    public double getDouble(String path) {
        return config.getDouble(path);
    }

    public String getMessage(String path) {
        return ChatColor.translateAlternateColorCodes('&', messagesConfig.getString(path));
    }

    private void register() {
        createMessagesConfig("messages.yml");
        createConfig("config.yml");
    }

    private void createMessagesConfig(String file) {
        messagesConfigFile = new File(plugin.getDataFolder(), file);
        if (!messagesConfigFile.exists()) {
            messagesConfigFile.getParentFile().mkdirs();
            plugin.saveResource(file, false);
        }
        messagesConfig = YamlConfiguration.loadConfiguration(messagesConfigFile);
        try {
            messagesConfig.save(messagesConfigFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createConfig(String file) {
        configFile = new File(plugin.getDataFolder(), file);
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            plugin.saveResource(file, false);
        }
        config = YamlConfiguration.loadConfiguration(configFile);
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteFilesIfExist(String name) {
        File file = new File(plugin.getDataFolder(), name);
        if (file.exists())
            file.delete();
    }
}
