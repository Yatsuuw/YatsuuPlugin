package fr.yatsuu.yatsuuplugin.commands;

import fr.yatsuu.yatsuuplugin.YatsuuPlugin;
import fr.yatsuu.yatsuuplugin.config.ConfigurationReader;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.annotation.Nonnull;
import java.util.Objects;

public class YploadCommand implements CommandExecutor {

    private final ConfigurationReader configReader;

    public YploadCommand (ConfigurationReader configReader) {
        this.configReader = configReader;
    }

    @Override
    public boolean onCommand(CommandSender sender, @NonNull Command command, @NonNull String label, @Nonnull String[] args) {
        ConfigurationReader configFile = YatsuuPlugin.getConfigReader();

        if (!sender.hasPermission("yatsuuplugin.command.ypload")) {

            String no_perm = Objects.requireNonNull(configFile.getConfiguration().getString("no_permission")).replace("{permission}", "yatsuuplugin.command.ypload");
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', no_perm));

        } else {

            ConfigurationReader config = YatsuuPlugin.getConfigReader();

            configReader.reload();
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getConfiguration().getString("success_reload"))));

        }

        return true;

    }

}
