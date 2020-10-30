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

    >> https://github.com/Aunto-Development-Group/Acorn <<

 */

package com.auntodev.Acorn.Checks.Movement.Speed;

import com.auntodev.Acorn.Checks.Check;
import com.auntodev.Acorn.Events.Teleport;
import com.auntodev.Acorn.Functions.Config;
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
import org.bukkit.potion.PotionEffectType;

/*

    [MOVEMENT] Speed.Insanity

     -> Prevents players from going WAY too fast
        in a short amount of time.
     -> This check is not meant to be used alone
        as it only detects insane amount of
        speed.

 */

public class Insanity extends Check implements Listener {
    @EventHandler (priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerMoveEvent (PlayerMoveEvent event) {
        FileConfiguration config = Config.get();
        Player player = event.getPlayer();
        Location from = event.getFrom();
        Location to = event.getTo();
        Block below = player.getWorld().getBlockAt(to.getBlockX(), to.getBlockY() - 1, to.getBlockZ());
        double distance = from.distance(to);

        if (Teleport.recently(player)) return;
        if (distance < config.getDouble("speed.insanity.max")) return;
        if (player.getGameMode() == GameMode.SPECTATOR) return;
        if (player.hasPotionEffect(PotionEffectType.SPEED)) return;
        if (player.isFlying()) return;
        if (below.isEmpty()) return;
        if (below.isLiquid()) return;

        flag(player, "Movement.Speed.Insanity", "Attempted to move " + distance + " blocks in a tick.");
        if (config.getInt("speed.insanity.mitigation.cancel") > 0) moveCancel(player, "Movement.Speed.Insanity", event);
    }
}
