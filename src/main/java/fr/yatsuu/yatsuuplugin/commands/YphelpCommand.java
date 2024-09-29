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

public class YphelpCommand implements CommandExecutor {
    private final YatsuuPlugin plugin;

    public YphelpCommand(YatsuuPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, @NonNull Command command, @NonNull String label, @Nonnull String[] args) {
        ConfigurationReader config = YatsuuPlugin.getConfigReader();

        if (!sender.hasPermission("yatsuuplugin.command.yphelp")) {

            String no_perm = Objects.requireNonNull(config.getConfiguration().getString("no_permission")).replace("{permission}", "yatsuuplugin.command.yphelp");
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', no_perm));

        } else {

            StringBuilder response = new StringBuilder();

            response.append(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getConfiguration().getString("commands")))).append("\n");

            String commandFormat = config.getConfiguration().getString("command_format");

            plugin.getDescription().getCommands().forEach((cmd, desc) -> {

                assert commandFormat != null;
                String formattedCommand = commandFormat.replace("{command}", cmd).replace("{description}", (CharSequence) desc.get("description"));

                response.append(ChatColor.translateAlternateColorCodes('&', formattedCommand)).append("\n");

            });

            sender.sendMessage(response.toString());

        }
        return true;
    }
}
