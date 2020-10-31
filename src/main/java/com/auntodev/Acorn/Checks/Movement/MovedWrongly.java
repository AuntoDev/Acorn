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

package com.auntodev.Acorn.Checks.Movement;

import com.auntodev.Acorn.Checks.Check;
import com.auntodev.Acorn.Events.Knockback;
import com.auntodev.Acorn.Events.Teleport;
import com.auntodev.Acorn.Functions.Config;
import com.auntodev.Acorn.Functions.OnGroundCheck;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MovedWrongly extends Check implements Listener {
    @EventHandler (priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerMoveEvent (PlayerMoveEvent event) {
        FileConfiguration config = Config.get();
        Player player = event.getPlayer();
        Location from = event.getFrom();
        Location to = event.getTo();
        Location loc = new Location(to.getWorld(), to.getX(), to.getY() - 0.4, to.getZ());
        Location loc2 = new Location(to.getWorld(), to.getX(), to.getY() - 1.1, to.getZ());
        Block below = player.getWorld().getBlockAt(loc);
        Block below2 = player.getWorld().getBlockAt(loc2);

        if (!below.isEmpty()) return;
        if (below2.isEmpty() || below2.isLiquid()) return;
        if (player.isFlying() || player.isSleeping()) return;
        if (Math.abs(from.getY() - to.getY()) == 0) return;
        if (Teleport.recently(player) || Knockback.recently(player)) return;
        if (Math.abs(from.getY() - to.getY()) > config.getDouble("moved-wrongly.threshold")) return;
        if (OnGroundCheck.whitelisted(below.getType())) return;

        flag(player, "Movement.MovedWrongly", "none");
        if (config.getInt("moved-wrongly.mitigation.cancel") > 0) moveCancel(player, "Movement.MovedWrongly", event);
    }
}
