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

public class HealCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, @NonNull Command command, @NonNull String label, @Nonnull String[] args) {

        ConfigurationReader config = YatsuuPlugin.getConfigReader();

        if (!sender.hasPermission("yatsuuplugin.command.heal")) {

            String no_perm = Objects.requireNonNull(config.getConfiguration().getString("no_permission")).replace("{permission}", "yatsuuplugin.command.heal");
            sender.sendMessage(ChatColor.RED + no_perm);

        } else {

            Player target;

            if (args.length > 0) {

                target = Bukkit.getPlayer(args[0]);

                if (target == null) {

                    sender.sendMessage(ChatColor.RED + config.getConfiguration().getString("no_target"));
                    return true;

                }

            } else if (sender instanceof Player) {

                target = (Player) sender;

            } else {

                sender.sendMessage(ChatColor.RED + config.getConfiguration().getString("target_required"));
                return true;

            }

            target.setHealth(target.getMaxHealth());

            if (sender != target) {

                sender.sendMessage(ChatColor.GREEN + Objects.requireNonNull(config.getConfiguration().getString("target_success_heal")).replace("%target%", target.getName()));
                target.sendMessage(ChatColor.GREEN + config.getConfiguration().getString("success_heal"));

            } else {

                sender.sendMessage(ChatColor.GREEN + config.getConfiguration().getString("success_heal"));
            }

        }
        return true;
    }
}
