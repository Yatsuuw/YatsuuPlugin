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

public class FlySpeedCommand implements CommandExecutor {

    private final YatsuuPlugin plugin;

    public FlySpeedCommand(YatsuuPlugin plugin) {

        this.plugin = plugin;

    }

    @Override
    public boolean onCommand(CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {

        if (!sender.hasPermission("yatsuuplugin.command.flyspeed")) {

            String no_perm = Objects.requireNonNull(plugin.getConfig().getString("no_permission")).replace("{permission}", "yatsuuplugin.command.flyspeed");
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', no_perm));

            return true;

        }

        Player targetPlayer;

        if (args.length == 0) {

            if (! (sender instanceof Player)) {

                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.getConfig().getString("target_required"))));

                return true;

            }

            targetPlayer = (Player) sender;
            float currentSpeed = targetPlayer.getFlySpeed();

            String currentSpeedMessage = String.format(Objects.requireNonNull(plugin.getConfig().getString("flyspeed_value")).replace("{speed}", String.valueOf(currentSpeed)));
            targetPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', currentSpeedMessage));

            return true;

        }

        float speed;

        try {

            speed = Float.parseFloat(args[0]);

            if (speed < 0 || speed > 1) {

                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.getConfig().getString("flyspeed_invalid"))));

                return true;

            }

        } catch (NumberFormatException e) {

            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.getConfig().getString("flyspeed_invalid"))));

            return true;

        }

        if (args.length == 2) {

            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);

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
        targetPlayer.setFlySpeed(speed);

        String successMessage = Objects.requireNonNull(plugin.getConfig().getString("flyspeed_success")).replace("{speed}", String.valueOf(speed));
        targetPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', successMessage));

        if (targetPlayer != sender) {

            String successMessageTarget = Objects.requireNonNull(plugin.getConfig().getString("flyspeed_success_target")).replace("{player}", targetPlayer.getName()).replace("{speed}", String.valueOf(speed));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', successMessageTarget));

        }

        return true;

    }

}
