package fr.yatsuu.yatsuuplugin.events;

import fr.yatsuu.yatsuuplugin.YatsuuPlugin;
import fr.yatsuu.yatsuuplugin.config.ConfigurationReader;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;
import java.util.Objects;

public class HungerEvent implements Listener {

    private static YatsuuPlugin plugin;
    private static FileConfiguration playerStates;
    private static List<String> playerList;

    public HungerEvent(YatsuuPlugin plugin) {

        HungerEvent.plugin = plugin;

    }

    @EventHandler
    public static void onHungerLoss(FoodLevelChangeEvent event) {

        if (! (event.getEntity() instanceof Player player)) {

            return;

        }

        String playerUUID = player.getUniqueId().toString();

        playerStates = plugin.playerStates;
        playerList = playerStates.getStringList("players");

        if (playerList.contains(playerUUID)) {

            event.setFoodLevel(20);

        }

    }

    @EventHandler
    public static void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        String playerUUID = player.getUniqueId().toString();

        playerStates = plugin.playerStates;
        playerList = playerStates.getStringList("players");
        ConfigurationReader config = YatsuuPlugin.getConfigReader();

        if (Boolean.parseBoolean(config.getConfiguration().getString("message_on_join"))) {

            if (playerList.contains(playerUUID)) {

                player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getConfiguration().getString("hunger_disabled"))));

            } else {

                player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getConfiguration().getString("hunger_enabled"))));

            }

        }

    }

}
