package fr.yatsuu.yatsuuplugin.events;

import fr.yatsuu.yatsuuplugin.YatsuuPlugin;
import org.bukkit.entity.Player;
import org.bukkit.Particle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class FlyParticleListener implements Listener {

    private final YatsuuPlugin plugin;

    public FlyParticleListener(YatsuuPlugin plugin) {

        this.plugin = plugin;

    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {

        Player player = event.getPlayer();

        if (player.getAllowFlight() && player.isFlying()) {

            // Generate particles under the player
            new BukkitRunnable() {

                @Override
                public void run() {

                    if (! (player.isFlying())) {

                        cancel(); // Stop if the player is no longer flying
                        return;

                    }

                    player.getWorld().spawnParticle(
                            Particle.CLOUD, // Type of particle
                            player.getLocation().subtract(0, 0.5, 0), // Location (under the player)
                            1, // Number of particles
                            0.3, 0.1, 0.3, // Offset for the spread
                            0 // Speed of particles (0 for default speed)
                    );

                }

            }.runTaskTimer(plugin, 0L, 70L);

        }

    }

}
