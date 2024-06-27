package fr.yatsuu.yatsuuplugin.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class ConfigurationReader {

    private final Plugin plugin;
    private final String configName;
    private FileConfiguration configuration;

    /**
     * Constructor of ConfigurationReader
     * @param plugin Plugin instance
     * @param configName Name of the configuration file (with path if not in src folder)
     */
    public ConfigurationReader(Plugin plugin, String configName) {

        this.plugin = plugin;
        this.configName = configName;

        this.reload();

    }

    /**
     * Reload the configuration file from disk
     */
    public void reload() {

        File configFile = new File(this.plugin.getDataFolder(), this.configName);

        if (!configFile.exists()) {
            this.plugin.saveResource(this.configName, false);
        }

        this.configuration = YamlConfiguration.loadConfiguration(configFile);

    }

    /**
     * Get local FileConfiguration
     * @return FileConfiguration
     */
    public FileConfiguration getConfiguration() {

        return this.configuration;

    }

}
