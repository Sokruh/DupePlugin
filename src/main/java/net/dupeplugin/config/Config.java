package net.dupeplugin.config;

import net.dupeplugin.DupePlugin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class Config {

    private final String configFileName;
    private DupePlugin plugin;
    private FileConfiguration configCFG;
    private File configFile;
    public Config(String configFileName, DupePlugin plugin) {
        this.configFileName = configFileName;
        this.plugin = plugin;
        initConfig();
    }

    private void initConfig() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        configFile = new File(plugin.getDataFolder(), (configFileName + ".yml"));
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
                Bukkit.getLogger().info(configFileName + ".yml created.");
            }catch (IOException e) {
                Bukkit.getServer().getLogger().warning(configFileName + ".yml couldn't be created.");
            }
        }

        configCFG = YamlConfiguration.loadConfiguration(configFile);
    }

    public FileConfiguration getConfig() {
        return configCFG;
    }

    public void saveConfig() {
        try {
            configCFG.save(configFile);
        }catch (IOException e) {
            Bukkit.getServer().getLogger().severe("Couldn't saved " + configFileName + ".yml");
        }
    }

    public void reloadConfig() {
        Bukkit.getServer().getLogger().info( "Reloading " + configFileName + ".yml");
        configCFG = YamlConfiguration.loadConfiguration(configFile);
        Bukkit.getServer().getLogger().info( configFileName + ".yml has been reloaded.");
    }

}