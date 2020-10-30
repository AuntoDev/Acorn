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

package com.auntodev.Acorn.Checks;

import com.auntodev.Acorn.Functions.Config;
import com.auntodev.Acorn.Functions.Log;
import com.auntodev.Acorn.Functions.Util;
import com.auntodev.Acorn.Functions.Verbose;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Check {
    private static final Plugin plugin = Bukkit.getPluginManager().getPlugin("Acorn");
    private static final HashMap<String, Integer> timer = new HashMap<String, Integer>();
    private static final HashMap<String, Integer> vision = new HashMap<String, Integer>();
    private static final HashMap<String, Integer> spoof = new HashMap<String, Integer>();
    private static final HashMap<String, Integer> generic = new HashMap<String, Integer>();
    private static final HashMap<String, Integer> mineplex = new HashMap<String, Integer>();
    private static final HashMap<String, Integer> jesus = new HashMap<String, Integer>();
    private static final HashMap<String, Integer> insanity = new HashMap<String, Integer>();
    private static final HashMap<String, Integer> limit = new HashMap<String, Integer>();
    private static final HashMap<String, Integer> wrongly = new HashMap<String, Integer>();

    public static void flag (Player player, String check, String info) {
        FileConfiguration config = Config.get();

        action(player, check);
        verbose("&c" + player.getName() + " &7failed &c" + check + " &7in &c" + player.getWorld().getName() + " &7at &c" + player.getLocation().getBlockX() + " " + player.getLocation().getBlockY() + " " + player.getLocation().getBlockZ() + "&7.");

        if (config.getBoolean("log")) Log.addMessage(player.getName() + " failed the " + check + "check in " + player.getWorld().getName() + ".");

        if (config.getBoolean("debug")) {
            if (info.equals("none")) {
                broadcast("&7Player &e" + player.getName() + " &7failed &e" + check + "&7. They are currently in &e" + player.getWorld().getName() + " &7at &e" + player.getLocation().getBlockX() + " " + player.getLocation().getBlockY() + " " + player.getLocation().getBlockZ() + "&7.");
            } else broadcast("&7Player &e" + player.getName() + " &7failed &e" + check + "&7. " + info + " They are currently in &e" + player.getWorld().getName() + " &7at &e" + player.getLocation().getBlockX() + " " + player.getLocation().getBlockY() + " " + player.getLocation().getBlockZ() + "&7.");
        }
    }

    private static void action (Player player, final String check) {
        int current = 0;
        FileConfiguration config = Config.get();
        FileConfiguration messages = Config.messages();
        String configCheck = "none";
        final String name = player.getName();
        if (check.equals("Packets.Timer")) configCheck = "timer";
        if (check.equals("Movement.Fly.Generic")) configCheck = "fly.generic";
        if (check.equals("Movement.Fly.Mineplex")) configCheck = "fly.mineplex";
        if (check.equals("Movement.Fly.GroundSpoof")) configCheck = "fly.ground-spoof";
        if (check.equals("Movement.MovedWrongly")) configCheck = "moved-wrongly";
        if (check.equals("Movement.Jesus")) configCheck = "jesus";
        if (check.equals("Combat.OutOfVision")) configCheck = "out-of-vision";
        if (check.equals("Movement.Speed.Insanity")) configCheck = "speed.insanity";
        if (check.equals("Movement.Speed.Limit")) configCheck = "speed.limit";
        if (configCheck.equals("none")) return;

        if (check.equals("Packets.Timer")) {
            if (timer.containsKey(name)) {
                current = timer.get(name);
                timer.put(name, current + 1);
            } else timer.put(name, 1);

            current = timer.get(name);
        } else if (check.equals("Movement.Fly.Generic")) {
            if (generic.containsKey(name)) {
                current = generic.get(name);
                generic.put(name, current + 1);
            } else generic.put(name, 1);

            current = generic.get(name);
        } else if (check.equals("Movement.Fly.Mineplex")) {
            if (mineplex.containsKey(name)) {
                current = mineplex.get(name);
                mineplex.put(name, current + 1);
            } else mineplex.put(name, 1);

            current = mineplex.get(name);
        } else if (check.equals("Movement.Fly.GroundSpoof")) {
            if (spoof.containsKey(name)) {
                current = spoof.get(name);
                spoof.put(name, current + 1);
            } else spoof.put(name, 1);

            current = spoof.get(name);
        } else if (check.equals("Movement.MovedWrongly")) {
            if (wrongly.containsKey(name)) {
                current = wrongly.get(name);
                wrongly.put(name, current + 1);
            } else wrongly.put(name, 1);

            current = wrongly.get(name);
        } else if (check.equals("Movement.Jesus")) {
            if (jesus.containsKey(name)) {
                current = jesus.get(name);
                jesus.put(name, current + 1);
            } else jesus.put(name, 1);

            current = jesus.get(name);
        } else if (check.equals("Combat.OutOfVision")) {
            if (vision.containsKey(name)) {
                current = vision.get(name);
                vision.put(name, current + 1);
            } else vision.put(name, 1);

            current = vision.get(name);
        } else if (check.equals("Movement.Speed.Insanity")) {
            if (insanity.containsKey(name)) {
                current = insanity.get(name);
                insanity.put(name, current + 1);
            } else insanity.put(name, 1);

            current = insanity.get(name);
        } else if (check.equals("Movement.Speed.Limit")) {
            if (limit.containsKey(name)) {
                current = limit.get(name);
                limit.put(name, current + 1);
            } else limit.put(name, 1);

            current = limit.get(name);
        }

        new BukkitRunnable(){
            public void run() {
                if (check.equals("Packets.Timer")) {
                    if (!timer.containsKey(name)) return;

                    Integer updated = timer.get(name);
                    if (updated > 0) timer.put(name, updated - 1);
                } else if (check.equals("Movement.Fly.Generic")) {
                    if (!generic.containsKey(name)) return;

                    Integer updated = generic.get(name);
                    if (updated > 0) generic.put(name, updated - 1);
                } else if (check.equals("Movement.Fly.Mineplex")) {
                    if (!mineplex.containsKey(name)) return;

                    Integer updated = mineplex.get(name);
                    if (updated > 0) mineplex.put(name, updated - 1);
                } else if (check.equals("Movement.Fly.GroundSpoof")) {
                    if (!spoof.containsKey(name)) return;

                    Integer updated = spoof.get(name);
                    if (updated > 0) spoof.put(name, updated - 1);
                } else if (check.equals("Movement.MovedWrongly")) {
                    if (!wrongly.containsKey(name)) return;

                    Integer updated = wrongly.get(name);
                    if (updated > 0) wrongly.put(name, updated - 1);
                } else if (check.equals("Movement.Jesus")) {
                    if (!jesus.containsKey(name)) return;

                    Integer updated = jesus.get(name);
                    if (updated > 0) jesus.put(name, updated - 1);
                } else if (check.equals("Combat.OutOfVision")) {
                    if (!vision.containsKey(name)) return;

                    Integer updated = vision.get(name);
                    if (updated > 0) vision.put(name, updated - 1);
                } else if (check.equals("Movement.Speed.Insanity")) {
                    if (!insanity.containsKey(name)) return;

                    Integer updated = insanity.get(name);
                    if (updated > 0) insanity.put(name, updated - 1);
                } else if (check.equals("Movement.Speed.Limit")) {
                    if (!limit.containsKey(name)) return;

                    Integer updated = limit.get(name);
                    if (updated > 0) limit.put(name, updated - 1);
                }
            }
        }.runTaskLater(plugin, config.getInt(configCheck + ".decay"));

        if (config.getIntegerList(configCheck + ".alert").contains(current)) staff(messages.getString("alert-message").replace("%player%", player.getName()).replace("%world%", player.getWorld().getName()).replace("%check%", check).replace("%vl%", "" + current));

        if (config.getInt(configCheck + ".mitigation.kill") > 0 && current == config.getInt(configCheck + ".mitigation.kill")) {
            player.setHealth(0);
            player.sendMessage(messages.getString("kill-message"));
        }

        if (config.getInt(configCheck + ".mitigation.kick") > 0 && current >= config.getInt(configCheck + ".mitigation.kick")) kick(player, check);
    }

    private static void kick (final Player player, final String check) {
        final FileConfiguration config = Config.get();
        final FileConfiguration messages = Config.messages();

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            public void run() {
                if (check.startsWith("Movement.Fly.")) {
                    if (config.getBoolean("fly.hide-kicks")) {
                        player.kickPlayer("Flying is not enabled on this server");
                        return;
                    }
                }

                player.kickPlayer(ChatColor.translateAlternateColorCodes('&', Util.parseString(messages.getStringList("kick-message"))));
                if (config.getBoolean("broadcast")) broadcast(messages.getString("kick-broadcast"));
            }
        }, 1L);
    }

    private static void staff (String string) {
        string = ChatColor.translateAlternateColorCodes('&', "&6&lACORN  " + string);

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!player.hasPermission("acorn.notify") && !player.isOp()) return;
            player.sendMessage(string);
        }
    }

    private static void broadcast (String string) {
        string = ChatColor.translateAlternateColorCodes('&', string);
        Bukkit.broadcastMessage(string);
    }

    private static void verbose (String string) {
        for (UUID uuid : Verbose.get().keySet()) {
            Player alerter = Bukkit.getPlayer(uuid);

            if (alerter.isOnline()) {
                if (alerter.hasPermission("acorn.verbose")) {
                    alerter.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lACORN &3VERBOSE  " + string));
                } else Verbose.toggle(alerter);
            } else Verbose.toggle(alerter);
        }
    }

    public static void packetSetback (Player player, String check, PacketEvent event) {
        FileConfiguration config = Config.get();
        Integer current = 0;
        String configCheck = "none";
        String name = player.getName();
        if (check.equals("Packets.Timer")) configCheck = "timer";
        if (configCheck.equals("none")) return;

        if (configCheck.equals("timer")) {
            if (timer.containsKey(name)) {
                current = timer.get(name);
                timer.put(name, current + 1);
            } else timer.put(name, 1);

            current = timer.get(name);
        }

        if (current >= config.getInt(configCheck + ".mitigation.cancel")) {
            event.setCancelled(true);
            verbose("&4CANCEL: &c" + player.getName() + " &7sent a packet that was caught by Acorn and canceled. (&f" + check + "&7)");
            if (config.getBoolean("debug")) Bukkit.getLogger().warning("CANCEL " + player.getName() + " at " + player.getLocation().getX() + " " + player.getLocation().getY() + " " + player.getLocation().getZ() + " for failing " + check + ". PacketEvent has been canceled and the player pulled back by the server.");
        }
    }

    public static void attackCancel (Player player, String check, EntityDamageByEntityEvent event) {
        FileConfiguration config = Config.get();

        if (check.equals("Combat.OutOfVision")) {
            if (!vision.containsKey(player.getName())) return;
            if (vision.get(player.getName()) < config.getInt("out-of-vision.mitigation.cancel")) return;
        }

        event.setCancelled(true);
        verbose("&4CANCEL: &c" + player.getName() + " &7attempted to attack wrongly. Their action was caught by Acorn and canceled. (&f" + check + "&7)");
        if (config.getBoolean("debug")) Bukkit.getLogger().warning("CANCEL " + player.getName() + " at " + player.getLocation().getX() + " " + player.getLocation().getY() + " " + player.getLocation().getZ() + " for failing " + check + ". EntityDamageByEntityEvent has been canceled and the victim not damaged.");
    }

    public static void moveCancel (Player player, String check, PlayerMoveEvent event) {
        FileConfiguration config = Config.get();

        if (check.equals("Movement.Fly.Generic")) {
            if (!generic.containsKey(player.getName())) return;
            if (generic.get(player.getName()) < config.getInt("fly.generic.mitigation.cancel")) return;
        } else if (check.equals("Movement.Fly.Mineplex")) {
            if (!mineplex.containsKey(player.getName())) return;
            if (mineplex.get(player.getName()) < config.getInt("fly.mineplex.mitigation.cancel")) return;
        } else if (check.equals("Movement.Fly.GroundSpoof")) {
            if (!spoof.containsKey(player.getName())) return;
            if (spoof.get(player.getName()) < config.getInt("fly.ground-spoof.mitigation.cancel")) return;
        } else if (check.equals("Movement.MovedWrongly")) {
            if (!spoof.containsKey(player.getName())) return;
            if (spoof.get(player.getName()) < config.getInt("moved-wrongly.mitigation.cancel")) return;
        } else if (check.equals("Movement.Jesus")) {
            if (!jesus.containsKey(player.getName())) return;
            if (jesus.get(player.getName()) < config.getInt("jesus.mitigation.cancel")) return;
        } else if (check.equals("Movement.Speed.Insanity")) {
            if (!insanity.containsKey(player.getName())) return;
            if (insanity.get(player.getName()) < config.getInt("speed.insanity.mitigation.cancel")) return;
        } else if (check.equals("Movement.Speed.Limit")) {
            if (!limit.containsKey(player.getName())) return;
            if (limit.get(player.getName()) < config.getInt("speed.limit.mitigation.cancel")) return;
        }

        event.setCancelled(true);
        verbose("&4CANCEL: &c" + player.getName() + " &7attempted to perform an action that was caught by Acorn and canceled. (&f" + check + "&7)");
        if (config.getBoolean("debug")) Bukkit.getLogger().warning("CANCEL " + player.getName() + " at " + player.getLocation().getX() + " " + player.getLocation().getY() + " " + player.getLocation().getZ() + " for failing " + check + ". EntityDamageByEntityEvent has been canceled and the victim not damaged.");
        if (config.getBoolean("log")) Log.addMessage("CANCEL " + player.getName() + " cancel movement in " + player.getWorld().getName() + "  < X:" + event.getFrom().getX() + " Y:" + event.getFrom().getY() + " Z:" + event.getFrom().getZ() + " >  to  < X:" + event.getTo().getX() + " Y:" + event.getTo().getY() + " Z:" + event.getTo().getZ() + " >  for failing" + check + ".");
    }

    public static void attemptRubberband (Player player, Location to, String check, PlayerMoveEvent event) {
        FileConfiguration config = Config.get();

        event.setCancelled(true);
        player.teleport(to);

        verbose("&4RUBBERBAND: &c" + player.getName() + " &7attempted to perform a disallowed action that was caught by Acorn. They've been teleported to &c" + to.getX() + " " + to.getY() + " " + to.getZ() + "&7. (&f" + check + "&7)");
        if (config.getBoolean("log")) Log.addMessage("RUBBERBAND " + player.getName() + " cancel player movement and rubberband in " + player.getWorld().getName() + "  < X:" + event.getFrom().getX() + " Y:" + event.getFrom().getY() + " Z:" + event.getFrom().getZ() + " >  to  < X:" + event.getTo().getX() + " Y:" + event.getTo().getY() + " Z:" + event.getTo().getZ() + " >  for failing" + check + ".");
    }
}
