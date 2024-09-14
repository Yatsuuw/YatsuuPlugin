package fr.yatsuu.yatsuuplugin.events;

import fr.yatsuu.yatsuuplugin.YatsuuPlugin;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.Objects;

public class PlayerJoinQuitListener implements Listener {

    private final YatsuuPlugin plugin;
    private Chat chat;

    public PlayerJoinQuitListener(YatsuuPlugin plugin) {

        this.plugin = plugin;

        setupChat();

    }

    private void setupChat() {

        RegisteredServiceProvider<Chat> rsp = Bukkit.getServicesManager().getRegistration(Chat.class);

        if (rsp != null) {

            chat = rsp.getProvider();

        }

    }

    private String getPlayerPrefix(Player player) {

        if (chat != null) {

            String prefix = chat.getPlayerPrefix(player);

            return (prefix != null) ? prefix : "";

        }

        return "";

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        String prefix = getPlayerPrefix(player);
        String playerName = player.getName();

        String joinMessage = Objects.requireNonNull(plugin.getConfig().getString("join_message")).replace("%prefix%", prefix).replace("%name%", playerName);

        event.setJoinMessage(ChatColor.translateAlternateColorCodes('&', joinMessage));

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

        Player player = event.getPlayer();

        String prefix = getPlayerPrefix(player);
        String playerName = player.getName();

        String quitMessage = Objects.requireNonNull(plugin.getConfig().getString("quit_message")).replace("%prefix%", prefix).replace("%name%", playerName);

        event.setQuitMessage(ChatColor.translateAlternateColorCodes('&', quitMessage));

    }

}
