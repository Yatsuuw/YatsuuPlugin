package fr.yatsuu.yatsuuplugin.commands;

import fr.yatsuu.yatsuuplugin.YatsuuPlugin;
import fr.yatsuu.yatsuuplugin.config.ConfigurationReader;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.annotation.Nonnull;
import java.util.Objects;

public class NightCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, @NonNull Command command, @NonNull String label, @Nonnull String[] args) {

        ConfigurationReader config = YatsuuPlugin.getConfigReader();

        if (!sender.hasPermission("yatsuuplugin.command.night")) {

            String no_perm = Objects.requireNonNull(config.getConfiguration().getString("no_permission")).replace("{permission}", "yatsuuplugin.command.night");
            sender.sendMessage(ChatColor.RED + no_perm);

        } else {

            Bukkit.getWorlds().forEach(world -> world.setTime(13000));
            sender.sendMessage(ChatColor.GREEN + config.getConfiguration().getString("success_night"));

        }
        return true;
    }
}
