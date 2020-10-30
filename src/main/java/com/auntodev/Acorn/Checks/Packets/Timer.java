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

package com.auntodev.Acorn.Checks.Packets;

import com.auntodev.Acorn.Checks.Check;
import com.auntodev.Acorn.Functions.Config;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

/*

    [PACKETS] Timer

     -> Prevents players from sending too many
        position packets in a short amount of
        time. Great for detecting Blink and Timer.
     -> Can flag laggy players so shouldn't ban
        players under any circumstances.

 */

public class Timer extends Check {
    static HashMap<String, Integer> blink = new HashMap<String, Integer>();
    static Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("Acorn");

    public static void timer (Player player, PacketEvent event) {
        FileConfiguration config = Config.get();
        final String name = player.getName();
        Integer current = 1;

        if (blink.containsKey(name)) {
            current = blink.get(name);
            blink.put(name, current + 1);
        } else blink.put(name, current);

        new BukkitRunnable(){
            public void run() {
                if (!blink.containsKey(name)) return;

                Integer updated = blink.get(name);
                if (updated > 0) blink.put(name, updated - 1);
            }
        }.runTaskLater(plugin, config.getInt("timer.removal"));

        if (current > config.getInt("timer.limit")) {
            flag(player, "Packets.Timer", "&7Attempted to send " + current + " position packets in a second.");
            if (config.getInt("timer.mitigation.cancel") > 0) packetSetback(player, "Packets.Timer", event);
        }
    }
}
