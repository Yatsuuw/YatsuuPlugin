package fr.yatsuu.yatsuuplugin.commands;

import fr.yatsuu.yatsuuplugin.YatsuuPlugin;
import fr.yatsuu.yatsuuplugin.config.ConfigurationReader;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Objects;

public class SpawnCommand implements CommandExecutor {

    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, String[] args) {

        ConfigurationReader config = YatsuuPlugin.getConfigReader();

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

        try {

            target.sendMessage(ChatColor.GREEN + config.getConfiguration().getString("teleport_spawn_message"));

            if (sender != target) {

                sender.sendMessage(ChatColor.GREEN + Objects.requireNonNull(config.getConfiguration().getString("sender_teleport_spawn_message")).replace("%player%", target.getName()));
                target.sendMessage(ChatColor.GREEN + config.getConfiguration().getString("teleport_spawn_message_target"));

            } else {

                target.sendMessage(ChatColor.GREEN + config.getConfiguration().getString("teleport_spawn_message_target"));

            }

            Thread.sleep(0);

        } catch (InterruptedException ignored) {

        }

        double x = config.getConfiguration().getDouble("spawn.x");
        double y = config.getConfiguration().getDouble("spawn.y");
        double z = config.getConfiguration().getDouble("spawn.z");

        float yaw = (float) config.getConfiguration().getDouble("spawn.yaw");
        float pitch = (float) config.getConfiguration().getDouble("spawn.pitch");

        String worldName = config.getConfiguration().getString("spawn.world");

        assert worldName != null;
        Location spawn = new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);

        target.teleport(spawn);

        return true;
    }

}
