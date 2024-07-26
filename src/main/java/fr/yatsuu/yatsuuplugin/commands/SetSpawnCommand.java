package fr.yatsuu.yatsuuplugin.commands;

import fr.yatsuu.yatsuuplugin.YatsuuPlugin;
import fr.yatsuu.yatsuuplugin.config.ConfigurationReader;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.Objects;

public class SetSpawnCommand implements CommandExecutor {

    private final YatsuuPlugin plugin;
    private final ConfigurationReader configReader;

    public SetSpawnCommand(YatsuuPlugin plugin, ConfigurationReader configReader) {

        this.plugin = plugin;
        this.configReader = configReader;

    }

    @Override
    public boolean onCommand(CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {

        if (!sender.hasPermission("yatsuuplugin.command.setspawn")) {

            String no_perm = Objects.requireNonNull(plugin.getConfig().getString("no_permission")).replace("{permission}", "yatsuuplugin.command.setspawn");
            sender.sendMessage(ChatColor.RED + no_perm);

        } else {

            if (! (sender instanceof Player player)) {

                sender.sendMessage(ChatColor.RED + plugin.getConfig().getString("console_command_sender"));

                return true;

            }

            Location loc = player.getLocation();

            // Save the current location and orientation to the config
            plugin.getConfig().set("spawn.world", Objects.requireNonNull(loc.getWorld()).getName());

            plugin.getConfig().set("spawn.x", loc.getX());
            plugin.getConfig().set("spawn.y", loc.getY());
            plugin.getConfig().set("spawn.z", loc.getZ());

            plugin.getConfig().set("spawn.yaw", loc.getYaw());
            plugin.getConfig().set("spawn.pitch", loc.getPitch());

            plugin.saveConfig();

            player.sendMessage(ChatColor.YELLOW + plugin.getConfig().getString("spawn_set"));

            configReader.reload();

        }

        return true;

    }

}
