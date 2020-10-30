package com.auntodev.Acorn.Checks.Movement.Fly;

import com.auntodev.Acorn.Checks.Check;
import com.auntodev.Acorn.Functions.Config;
import com.auntodev.Acorn.Functions.OnGroundCheck;
import com.auntodev.Acorn.Functions.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class Mineplex extends Check implements Listener {
    static Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("Acorn");

    @EventHandler (priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerMoveEvent (PlayerMoveEvent event) {
        FileConfiguration config = Config.get();
        Player player = event.getPlayer();
        Location from = event.getFrom();
        Location to = event.getTo();

        if (config.getString("fly.priority").equals("low")) {
            if (from.getBlockX() == to.getBlockX() && from.getBlockY() == to.getBlockY() && from.getBlockZ() == to.getBlockZ()) return;
        }

        if (player.isFlying() || player.isInsideVehicle() || player.isSleeping()) return;
        if (OnGroundCheck.isOnGround(player)) return;
        if (PlayerUtil.nearSolid(player)) return;

        Double abs = Math.abs(from.getY() - to.getY());
        List<Double> list = config.getDoubleList("fly.mineplex.fingerprints");
        if (!list.contains(abs)) return;

        flag(player, "Movement.Fly.Mineplex", "Fingerprint: " + abs);
        if (config.getInt("fly.mineplex.mitigation.cancel") > 0) moveCancel(player, "Movement.Fly.Mineplex", event);
    }
}
