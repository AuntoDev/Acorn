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

/*

    [MOVEMENT] Speed.Changes

     -> Prevents players from changing their speed
        too quickly or rapidly. In essence, this
        stops players from "toggling on" speed
        hacks without setting off alarm bells.
     -> This check logs all speed changes and uses
        the data it has logged on players to make
        decisions.
     -> Movement.Speed.Changes should be decently
        reliable for a speed check but must catch
        the user AS SOON as they have started using
        speed hacks.
     -> This check should be used in conjunction
        with other checks.

 */

import com.auntodev.Acorn.Checks.Check;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.UUID;

public class Changes extends Check implements Listener {
    HashMap<UUID, Double> log = new HashMap<UUID, Double>();

    @EventHandler (priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerMoveEvent (PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location from = event.getFrom();
        Location to = event.getTo();
        double distance = from.distance(to);

        if (player.isFlying() || player.isInsideVehicle()) return;
        if (player.hasPotionEffect(PotionEffectType.SPEED)) return;

        if (log.containsKey(player.getUniqueId())) {
            double last = log.get(player.getUniqueId());
            double abs = Math.abs(distance - last);

            Bukkit.broadcastMessage(distance + " " + abs);
            if (abs < 2) return;

            //event.setCancelled(true);
            Bukkit.broadcastMessage("Sped up too quickly!");
        }

        log.put(player.getUniqueId(), distance);
    }
}
