package fr.yatsuu.yatsuuplugin.commands;

import fr.yatsuu.yatsuuplugin.YatsuuPlugin;
import fr.yatsuu.yatsuuplugin.config.ConfigurationReader;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ToggleHungerCommand implements CommandExecutor {

    private final YatsuuPlugin plugin;
    private ConfigurationReader config;
    private static final Logger logger = Bukkit.getLogger();

    public ToggleHungerCommand(YatsuuPlugin plugin) {

        this.plugin = plugin;

    }

    @Override
    public boolean onCommand(CommandSender sender, @NonNull Command command, @NonNull String label, @Nonnull String[] args) {

        if (!sender.hasPermission("yatsuuplugin.command.togglehunger")) {

            String no_perm = Objects.requireNonNull(config.getConfiguration().getString("no_permission")).replace("{permission}", "yatsuuplugin.command.togglehunger");
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', no_perm));

            return true;

        }

        Player target;

        if (args.length > 0) {

            target = Bukkit.getPlayer(args[0]);

            if (target == null) {

                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getConfiguration().getString("no_target"))));
                return true;

            }

        } else {

            target = (Player) sender;

        }

        String playerUUID = target.getUniqueId().toString();
        FileConfiguration playerStates = plugin.playerStates;
        config = YatsuuPlugin.getConfigReader();

        List<String> playerList = playerStates.getStringList("players");

        if (sender != target) {

            if (playerList.contains(playerUUID)) {

                playerList.remove(playerUUID);
                target.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getConfiguration().getString("hunger_enabled"))));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getConfiguration().getString("hunger_enabled_target")).replace("%target%", target.getName())));

            } else {

                playerList.add(playerUUID);
                target.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getConfiguration().getString("hunger_disabled"))));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getConfiguration().getString("hunger_disabled_target")).replace("%target%", target.getName())));

            }

        } else {

            if (playerList.contains(playerUUID)) {

                playerList.remove(playerUUID);
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getConfiguration().getString("hunger_enabled"))));

            } else {

                playerList.add(playerUUID);
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getConfiguration().getString("hunger_disabled"))));

            }

        }

        playerStates.set("players", playerList);

        try {

            playerStates.save(plugin.playerStatesFile);

        } catch (IOException e) {

            logger.log(Level.SEVERE, "An error occurred while saving the player states file", e);

        }

        return true;

    }

}