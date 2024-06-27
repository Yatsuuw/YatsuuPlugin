package fr.yatsuu.yatsuuplugin.commands;

import fr.yatsuu.yatsuuplugin.YatsuuPlugin;
import fr.yatsuu.yatsuuplugin.config.ConfigurationReader;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.annotation.Nonnull;
import java.util.Objects;

public class YplCommand implements CommandExecutor {
    private final YatsuuPlugin plugin;

    public YplCommand(YatsuuPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, @NonNull Command command, @NonNull String label, @Nonnull String[] args) {
        ConfigurationReader config = YatsuuPlugin.getConfigReader();

        if (!sender.hasPermission("yatsuuplugin.command.ypl")) {

            String no_perm = Objects.requireNonNull(config.getConfiguration().getString("no_permission")).replace("{permission}", "yatsuuplugin.command.ypl");
            sender.sendMessage(ChatColor.RED + no_perm);

        } else {

            sender.sendMessage(ChatColor.GREEN + config.getConfiguration().getString("commands"));

            plugin.getDescription().getCommands().forEach((cmd, desc) -> sender.sendMessage(ChatColor.YELLOW + "/" + cmd + ": " + desc.get("description") ));

        }
        return true;
    }
}
