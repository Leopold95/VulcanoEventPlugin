package me.leopold95.vulcano.core;

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
    private static File messagesConfigFile;
    private static FileConfiguration messagesConfig;
    private static File configFile;
    private static FileConfiguration config;


    public static boolean existsConfig(String path){
        return config.contains(path);
    }

    public static boolean existsMessage(String path){
        return config.contains(path);
    }

    public static void setConfig(String path, Object object) {
        try {
            config.set(path, object);
            config.save(configFile);
        }
        catch (Exception exp){
            exp.printStackTrace();
        }
    }

    public static void setMessage(String path, Object object) throws IOException {
        messagesConfig.set(path, object);
        messagesConfig.save(messagesConfigFile);
    }

    public static List<?> getList(String path) {
        return config.getList(path);
    }

    public static String getString(String path) {
        return ChatColor.translateAlternateColorCodes('&', config.getString(path));
    }

    public static int getInt(String path) {
        return config.getInt(path);
    }

    public static boolean getBoolean(String path) {
        return config.getBoolean(path);
    }

    public static double getDouble(String path) {
        return config.getDouble(path);
    }

    public static String getMessage(String path) {
        return ChatColor.translateAlternateColorCodes('&', messagesConfig.getString(path));
    }

    public static void register(JavaPlugin plugin) {
        createMessagesConfig("messages.yml", plugin);
        createConfig("config.yml", plugin);
    }

    private static void createMessagesConfig(String file, JavaPlugin plugin) {
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

    private static void createConfig(String file, JavaPlugin plugin) {
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

    private static void deleteFilesIfExist(String name, JavaPlugin plugin) {
        File file = new File(plugin.getDataFolder(), name);
        if (file.exists())
            file.delete();
    }
}
