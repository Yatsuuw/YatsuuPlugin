package fr.yatsuu.yatsuuplugin.commands;

import fr.yatsuu.yatsuuplugin.YatsuuPlugin;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.Objects;

public class FlyCommand implements CommandExecutor {

    private final YatsuuPlugin plugin;

    public FlyCommand(YatsuuPlugin plugin) {

        this.plugin = plugin;

    }

    @Override
    public boolean onCommand( @Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {

        if (!sender.hasPermission("yatsuuplugin.command.fly")) {

            String no_perm = Objects.requireNonNull(plugin.getConfig().getString("no_permission")).replace("{permission}", "yatsuuplugin.command.fly");
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', no_perm));

            return true;

        }

        Player targetPlayer;

        if (args.length > 0) {

            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);

            if (!offlinePlayer.isOnline()) {

                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.getConfig().getString("no_target"))));

                return true;

            }

            targetPlayer = offlinePlayer.getPlayer();

        } else {

            if (! (sender instanceof Player)) {

                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.getConfig().getString("target_required"))));

                return true;

            }

            targetPlayer = (Player) sender;

        }

        assert targetPlayer != null;
        boolean isFlying = targetPlayer.getAllowFlight();

        targetPlayer.setAllowFlight(!isFlying);
        targetPlayer.setFlying(!isFlying);

        String flyStatusMessage = isFlying ? plugin.getConfig().getString("fly_disabled") : plugin.getConfig().getString("fly_enabled");
        targetPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(flyStatusMessage)));

        if (targetPlayer != sender) {

            String senderMessage = isFlying ? plugin.getConfig().getString("fly_target_disabled") + targetPlayer.getName() + "." : plugin.getConfig().getString("fly_target_enabled") + targetPlayer.getName() + ".";
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', senderMessage));

        }

        return true;

    }
}
