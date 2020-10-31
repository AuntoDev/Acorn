/*

    Copyright (C) Aunto Development 2020.
    This code is part of the Acorn Anti-Cheat
    project by Aunto Development, led by Ollie.

    Licensed under:
    GNU General Public License v3.0
      -> Permissions of this strong copyleft
         license are conditioned on making
         available complete source code of
         licensed works and modifications,
         which include larger works using a
         licensed work, under the same license.
         Copyright and license notices must be
         preserved. Contributors provide an
         express grant of patent rights.

    >> https://github.com/AuntoDev/Acorn <<

 */

package com.auntodev.Acorn.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.HashMap;
import java.util.UUID;

public class Teleport implements Listener {
    private static final HashMap<UUID, Long> logs = new HashMap<UUID, Long>();

    @EventHandler (priority = EventPriority.MONITOR, ignoreCancelled = true)
    public static void onPlayerTeleportEvent (PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        logs.put(player.getUniqueId(), System.currentTimeMillis());
    }

    @EventHandler (priority = EventPriority.MONITOR, ignoreCancelled = true)
    public static void onPlayerQuitEvent (PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (logs.containsKey(player.getUniqueId())) logs.remove(player.getUniqueId());
    }

    public static Long qet (Player player) {
        if (!logs.containsKey(player.getUniqueId())) return 0L;
        return logs.get(player.getUniqueId());
    }

    public static boolean recently (Player player) {
        if (!logs.containsKey(player.getUniqueId())) return false;
        return System.currentTimeMillis() - logs.get(player.getUniqueId()) <= 650;
    }
}
