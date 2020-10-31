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

package com.auntodev.Acorn.Checks.Movement.Speed;

import com.auntodev.Acorn.Checks.Check;
import com.auntodev.Acorn.Events.Teleport;
import com.auntodev.Acorn.Functions.Config;
import com.auntodev.Acorn.Functions.OnGroundCheck;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/*

    [MOVEMENT] Speed.Limit

     -> Limits how fast players can go.
     -> This will catch nearly every speed
        hack due to its nature.

 */

public class Limit extends Check implements Listener {
    @EventHandler (priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerMoveEvent (PlayerMoveEvent event) {
        FileConfiguration config = Config.get();
        Player player = event.getPlayer();
        Location from = event.getFrom();
        Location to = event.getTo();
        Location loc = new Location(player.getWorld(), to.getX(), to.getY() - 0.6, to.getZ());
        Block below = loc.getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
        double distance = from.distance(to);
        double max = 10.0;

        if (below.isLiquid()) return;
        if (player.isFlying()) return;
        if (Teleport.recently(player)) return;
        if (!OnGroundCheck.isOnGround(player)) return;
        if (player.getGameMode() == GameMode.SPECTATOR) return;
        if (below.isEmpty() && from.getY() <= to.getY() && config.getBoolean("speed.limit.ignore-falling")) return;

        if (Math.abs(from.getY() - to.getY()) < config.getDouble("speed.limit.required-difference")) {
            max = config.getDouble("speed.limit.limits.walking.normal");
            if (player.isSprinting()) max = config.getDouble("speed.limit.limits.sprinting.normal");
            if (player.isSneaking()) max = config.getDouble("speed.limit.limits.crouching.normal");
        } else {
            max = config.getDouble("speed.limit.limits.walking.with-velocity");
            if (player.isSprinting()) max = config.getDouble("speed.limit.limits.sprinting.with-velocity");
            if (player.isSneaking()) max = config.getDouble("speed.limit.limits.crouching.with-velocity");
        }

        if (distance >= max) {
            flag(player, "Movement.Speed.Limit", "Attempted to move " + distance + " blocks.");
            if (config.getInt("speed.limit.mitigation.cancel") > 0) moveCancel(player, "Movement.Speed.Limit", event);
        }
    }
}
