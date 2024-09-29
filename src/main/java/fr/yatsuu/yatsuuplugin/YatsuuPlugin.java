package fr.yatsuu.yatsuuplugin;

import fr.yatsuu.yatsuuplugin.commands.*;
import fr.yatsuu.yatsuuplugin.config.ConfigurationReader;
import fr.yatsuu.yatsuuplugin.events.FlyParticleListener;
import fr.yatsuu.yatsuuplugin.events.HungerEvent;
import fr.yatsuu.yatsuuplugin.events.PlayerJoinQuitListener;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class YatsuuPlugin extends JavaPlugin implements Listener {

    public File playerStatesFile = new File(getDataFolder(), "playerstates.yml");

    public FileConfiguration playerStates = YamlConfiguration.loadConfiguration(playerStatesFile);

    public static ConfigurationReader config;

    @Override
    public void onEnable() {

        // Plugin startup logic
        config = new ConfigurationReader(this, "config.yml");

        saveDefaultConfig();

        getLogger().info(config.getConfiguration().getString("plugin_on"));
        getServer().getPluginManager().registerEvents(this, this);

        registerCommands();
        registerEvents();

    }

    public static ConfigurationReader getConfigReader() {

        return config;

    }

    @Override
    public void onDisable() {

        // Plugin shutdown logic
        getLogger().info(config.getConfiguration().getString("plugin_off"));

    }

    @SuppressWarnings("ConstantConditions")
    private void registerCommands() {

        this.getCommand("day").setExecutor(new DayCommand());
        this.getCommand("night").setExecutor(new NightCommand());

        this.getCommand("sun").setExecutor(new SunCommand());
        this.getCommand("rain").setExecutor(new RainCommand());
        this.getCommand("thunder").setExecutor(new ThunderCommand());

        this.getCommand("feed").setExecutor(new FeedCommand());
        this.getCommand("togglehunger").setExecutor(new ToggleHungerCommand(this));
        this.getCommand("heal").setExecutor(new HealCommand());

        this.getCommand("yphelp").setExecutor(new YphelpCommand(this));
        this.getCommand("ypload").setExecutor(new YploadCommand(config));

        this.getCommand("gm").setExecutor(new GmCommand(this));

        this.getCommand("spawn").setExecutor(new SpawnCommand());
        this.getCommand("setspawn").setExecutor(new SetSpawnCommand(this, config));

        this.getCommand("fly").setExecutor(new FlyCommand(this));
        this.getCommand("flyspeed").setExecutor(new FlySpeedCommand(this));

    }

    private void registerEvents() {

        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new HungerEvent(this), this);
        pm.registerEvents(new FlyParticleListener(this), this);
        pm.registerEvents(new PlayerJoinQuitListener(this), this);

    }

}
