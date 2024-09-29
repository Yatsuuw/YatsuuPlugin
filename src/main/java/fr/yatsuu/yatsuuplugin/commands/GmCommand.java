package fr.yatsuu.yatsuuplugin.commands;

import fr.yatsuu.yatsuuplugin.YatsuuPlugin;
import fr.yatsuu.yatsuuplugin.config.ConfigurationReader;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.annotation.Nonnull;
import java.util.Objects;

public class GmCommand implements CommandExecutor {
    YatsuuPlugin plugin;

    public GmCommand(YatsuuPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, @NonNull Command command, @NonNull String label, @Nonnull String[] args) {

        ConfigurationReader config = YatsuuPlugin.getConfigReader();

        if (!sender.hasPermission("yatsuuplugin.command.gm")) {

            String no_perm = Objects.requireNonNull(config.getConfiguration().getString(ChatColor.RED + "no_permission")).replace("{permission}", "yatsuuplugin.command.gm");
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', no_perm));

        } else {

            if (args.length < 1 || args.length > 2) {

                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getConfiguration().getString("usage_gm"))));
                return true;

            }

            Player target;

            if (args.length == 2) {

                target = Bukkit.getPlayer(args[1]);

                if (target == null) {

                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getConfiguration().getString("no_target"))));
                    return true;

                }

            } else {

                if (sender instanceof Player) {
                    target = (Player) sender;

                } else {

                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getConfiguration().getString("target_required"))));
                    return true;

                }

            }

            String mode;

            switch (args[0]) {

                case "0" -> {

                    mode = config.getConfiguration().getString("gm0_name");
                    plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "gamemode survival %player%".replace("%player%", target.getName()));
                    assert mode != null;
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getConfiguration().getString("success_gm")).replace("%target%", target.getName()).replace("%mode%", mode)));

                }

                case "1" -> {

                    mode = config.getConfiguration().getString("gm1_name");
                    plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "gamemode creative %player%".replace("%player%", target.getName()));
                    assert mode != null;
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getConfiguration().getString("success_gm")).replace("%target%", target.getName()).replace("%mode%", mode)));

                }

                case "2" -> {

                    mode = config.getConfiguration().getString("gm2_name");
                    plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "gamemode adventure %player%".replace("%player%", target.getName()));
                    assert mode != null;
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getConfiguration().getString("success_gm")).replace("%target%", target.getName()).replace("%mode%", mode)));

                }

                case "3" -> {

                    mode = config.getConfiguration().getString("gm3_name");
                    plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "gamemode spectator %player%".replace("%player%", target.getName()));
                    assert mode != null;
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getConfiguration().getString("success_gm")).replace("%target%", target.getName()).replace("%mode%", mode)));

                }

            }

        }
        return true;
    }

}
