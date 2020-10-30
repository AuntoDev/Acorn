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

package com.auntodev.Acorn.Functions;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class PlayerUtil {
    public static boolean nearSolid (Player player) {
        Location loc = player.getLocation();
        Block xz = player.getWorld().getBlockAt(loc.getBlockX() - 1, loc.getBlockY() - 1, loc.getBlockZ() - 1);
        Block XZ = player.getWorld().getBlockAt(loc.getBlockX() + 1, loc.getBlockY() - 1, loc.getBlockZ() + 1);
        Block xZ = player.getWorld().getBlockAt(loc.getBlockX() - 1, loc.getBlockY() - 1, loc.getBlockZ() + 1);
        Block Xz = player.getWorld().getBlockAt(loc.getBlockX() + 1, loc.getBlockY() - 1, loc.getBlockZ() - 1);

        Material AIR = Material.AIR;
        Material WATER = Material.WATER;
        Material STATIONARY_WATER = Material.STATIONARY_WATER;

        return (xz.getType() != AIR && xz.getType() != WATER && xz.getType() != STATIONARY_WATER) || (XZ.getType() != AIR && XZ.getType() != WATER && XZ.getType() != STATIONARY_WATER) || (xZ.getType() != AIR && xZ.getType() != WATER && xZ.getType() != STATIONARY_WATER) || (Xz.getType() != AIR && Xz.getType() != WATER && Xz.getType() != STATIONARY_WATER);
    }
}
