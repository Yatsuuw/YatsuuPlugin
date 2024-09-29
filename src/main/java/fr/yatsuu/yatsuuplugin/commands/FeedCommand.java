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

public class FeedCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, @NonNull Command command, @NonNull String label, @Nonnull String[] args) {

        ConfigurationReader config = YatsuuPlugin.getConfigReader();

        if (!sender.hasPermission("yatsuuplugin.command.feed")) {

            String no_perm = Objects.requireNonNull(config.getConfiguration().getString("no_permission")).replace("{permission}", "yatsuuplugin.command.feed");
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', no_perm));

        } else {

            Player target;

            if (args.length > 0) {

                target = Bukkit.getPlayer(args[0]);

                if (target == null) {

                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getConfiguration().getString("no_target"))));
                    return true;

                }

            } else if (sender instanceof Player) {

                target = (Player) sender;

            } else {

                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getConfiguration().getString("target_required"))));
                return true;

            }

            target.setFoodLevel(20);
            target.setSaturation(5);

            if (sender != target) {

                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getConfiguration().getString("target_success_feed")).replace("%target%", target.getName())));
                target.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getConfiguration().getString("success_feed"))));

            } else {

                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getConfiguration().getString("success_feed"))));

            }

        }
        return true;
    }

}
