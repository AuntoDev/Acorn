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

package com.auntodev.Acorn.Functions;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class OnGroundCheck {
    public static boolean isOnGround (Player player) {
        Location loc = player.getLocation();

        loc.setY(loc.getY() - 0.3);
        Block in = player.getWorld().getBlockAt(loc);
        if (whitelisted(in.getType())) return true;

        loc.setY(loc.getY() + 0.3);

        if (Math.abs(loc.getY() - loc.getBlockY()) < 0.3) {
            loc.setY(loc.getBlockY() - 1);
            Block block = player.getWorld().getBlockAt(loc);
            if (whitelisted(block.getType())) return true;

            return !block.isLiquid() && !block.isEmpty() && block.getType() != Material.AIR;
        }

        return false;
    }

    public static boolean whitelisted (Material material) {
        Material[] array = {Material.ACACIA_FENCE, Material.ACACIA_FENCE_GATE, Material.BIRCH_FENCE, Material.BIRCH_FENCE_GATE, Material.DARK_OAK_FENCE, Material.DARK_OAK_FENCE_GATE, Material.FENCE, Material.FENCE_GATE, Material.IRON_FENCE, Material.JUNGLE_FENCE, Material.JUNGLE_FENCE_GATE, Material.NETHER_FENCE, Material.SPRUCE_FENCE, Material.SPRUCE_FENCE_GATE, Material.COBBLE_WALL, Material.SOIL, Material.CACTUS, Material.SOUL_SAND, Material.CHEST, Material.ENDER_CHEST, Material.TRAPPED_CHEST, Material.ENCHANTMENT_TABLE, Material.BED_BLOCK, Material.SKULL, Material.WATER_LILY, Material.TRAP_DOOR, Material.IRON_TRAPDOOR, Material.EYE_OF_ENDER, Material.STEP, Material.WOOD_STEP, Material.ACACIA_STAIRS, Material.BIRCH_WOOD_STAIRS, Material.JUNGLE_WOOD_STAIRS, Material.DARK_OAK_STAIRS, Material.COBBLESTONE_STAIRS, Material.BRICK_STAIRS, Material.NETHER_BRICK_STAIRS, Material.QUARTZ_STAIRS, Material.SANDSTONE_STAIRS, Material.RED_SANDSTONE_STAIRS, Material.SMOOTH_STAIRS, Material.WOOD_STAIRS};

        for (Material value : array) {
            if (material.toString().equals(value.toString())) return true;
        }

        return false;
    }
}
