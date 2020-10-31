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
import com.auntodev.Acorn.Events.Teleport;
import com.auntodev.Acorn.Functions.Config;
import com.auntodev.Acorn.Functions.PlayerUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/*

    [MOVEMENT] Jesus

     -> Prevents players from walking on water
        but can sometimes throw false positives.
     -> This check is fairly reliable.

 */

public class Jesus extends Check implements Listener {
    @EventHandler (priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerMoveEvent (PlayerMoveEvent event) {
        FileConfiguration config = Config.get();
        Player player = event.getPlayer();
        Location from = event.getFrom();
        Location to = event.getTo();
        Block below = player.getWorld().getBlockAt(to.getBlockX(), to.getBlockY() - 1, to.getBlockZ());
        Block in = player.getWorld().getBlockAt(to.getBlockX(), to.getBlockY(), to.getBlockZ());

        if (Teleport.recently(player)) return;
        if (player.isFlying() || player.isInsideVehicle()) return;
        if (in.getType() == Material.WATER || in.getType() == Material.STATIONARY_WATER || in.getType() == Material.LAVA || in.getType() == Material.STATIONARY_LAVA) return;
        if (below.getType() != Material.WATER && below.getType() != Material.STATIONARY_WATER) return;
        if (Math.abs(from.getY() - to.getY()) > 0.15) return;
        if (PlayerUtil.nearSolid(player)) return;

        flag(player, "Movement.Jesus", "none");
        if (config.getInt("jesus.mitigation.cancel") > 0) moveCancel(player, "Movement.Jesus", event);
    }
}
