package com.auntodev.Acorn.Events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.UUID;

public class Knockback implements Listener {
    static Plugin plugin = Bukkit.getPluginManager().getPlugin("Acorn");
    static HashMap<UUID, Long> damages = new HashMap<UUID, Long>();
    static HashMap<UUID, Long> logs = new HashMap<UUID, Long>();

    @EventHandler (priority = EventPriority.MONITOR, ignoreCancelled = true)
    public static void onEntityDamageByEntityEvent (EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) return;

        Player player = Bukkit.getPlayer(event.getEntity().getUniqueId());
        if (!player.isValid() || !player.isOnline()) return;

        damages.put(player.getUniqueId(), System.currentTimeMillis());
    }

    @EventHandler (priority = EventPriority.MONITOR)
    public static void onPlayerVelocityEvent (PlayerVelocityEvent event) {
        final Player player = event.getPlayer();

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            public void run() {
                if (!damages.containsKey(player.getUniqueId())) return;
                if (System.currentTimeMillis() - damages.get(player.getUniqueId()) > 500) return;
                logs.put(player.getUniqueId(), System.currentTimeMillis());
            }
        }, 5L);
    }

    @EventHandler (priority = EventPriority.MONITOR, ignoreCancelled = true)
    public static void onPlayerQuitEvent (PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (damages.containsKey(player.getUniqueId())) damages.remove(player.getUniqueId());
        if (logs.containsKey(player.getUniqueId())) logs.remove(player.getUniqueId());
    }

    public static Long qet (Player player) {
        if (!logs.containsKey(player.getUniqueId())) return 0L;
        return logs.get(player.getUniqueId());
    }

    public static boolean recently (Player player) {
        if (!logs.containsKey(player.getUniqueId())) return false;
        return System.currentTimeMillis() - logs.get(player.getUniqueId()) <= 1000;
    }
}
