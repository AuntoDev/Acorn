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

package com.auntodev.Acorn;

import com.auntodev.Acorn.Checks.Combat.*;
import com.auntodev.Acorn.Checks.Movement.*;
import com.auntodev.Acorn.Checks.Movement.Fly.*;
import com.auntodev.Acorn.Checks.Movement.Speed.*;
import com.auntodev.Acorn.Checks.Packets.*;
import com.auntodev.Acorn.Events.*;

import com.auntodev.Acorn.Functions.Config;
import com.auntodev.Acorn.Functions.Log;
import com.auntodev.Acorn.Functions.Verbose;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Acorn extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("Checking config...");

        Config.ensure();
        final FileConfiguration config = Config.get();

        getLogger().info("Loaded config!");
        getLogger().info("Creating log file...");

        Log.setup();

        getLogger().info("Log file created!");
        getLogger().info("Registering events...");

        Plugin plugin = this;
        PluginManager mng = Bukkit.getPluginManager();

        mng.registerEvents(new OutOfVision(), plugin);
        mng.registerEvents(new GroundSpoof(), plugin);
        mng.registerEvents(new Generic(), plugin);
        mng.registerEvents(new Mineplex(), plugin);
        mng.registerEvents(new Jesus(), plugin);
        mng.registerEvents(new Insanity(), plugin);
        mng.registerEvents(new Limit(), plugin);
        mng.registerEvents(new MovedWrongly(), plugin);

        mng.registerEvents(new Teleport(), plugin);
        mng.registerEvents(new Knockback(), plugin);

        getLogger().info("Registered events!");
        getLogger().info("Registering ProtocolLib events...");

        ProtocolManager manager = ProtocolLibrary.getProtocolManager();
        manager.addPacketListener(new PacketAdapter(plugin, ListenerPriority.HIGHEST, PacketType.Play.Client.POSITION) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                if (config.getBoolean("fly.enabled")) return;

                Player player = event.getPlayer();
                Timer.timer(player, event);
            }
        });

        getLogger().info("ProtocolLib events registered!");
        getLogger().info("Acorn is ready!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Acorn is being disabled...");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        FileConfiguration config = Config.get();
        if (!cmd.getName().equals("acorn") && !cmd.getName().equals("ac")) return false;

        if (config.getBoolean("hide-commands")) {
            sender.sendMessage(config.getString("hide-message"));
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage("Commands must be run by a player!");
            return true;
        }

        if (args.length != 0) {
            if (args[0].equals("reload")) {
                if (!sender.hasPermission("acorn.reload") && !sender.isOp()) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lACORN  &cAccess to this command was denied."));
                    return true;
                }

                Config.reload();
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lACORN  &aConfiguration file has been reloaded."));
                return true;
            } else if (args[0].equals("status")) {
                if (!sender.hasPermission("acorn.notify") && !sender.isOp()) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lACORN  &cAccess to this command was denied."));
                    return true;
                }

                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "\n" +
                    "&6&lACORN  &e&lMovement:\n" +
                    "&6&lACORN   &7Fly: &f" + config.getBoolean("fly.enabled") + "\n" +
                    "&6&lACORN   &7Spider: &f" + config.getBoolean("spider.enabled") + "\n" +
                    "&6&lACORN   &7MovedWrongly: &f" + config.getBoolean("move-wrongly.enabled") + "\n" +
                    "&6&lACORN  &e&lCombat:\n" +
                    "&6&lACORN   &7OutOfVision: &f" + config.getBoolean("out-of-vision.enabled") + "\n" +
                    "&6&lACORN  &e&lPackets:" + "\n" +
                    "&6&lACORN   &7Timer: &f" + config.getBoolean("timer.enabled") + "\n" +
                    "\n"
                ));
                return true;
            } else if (args[0].equals("verbose")) {
                if (!sender.hasPermission("acorn.verbose") && !sender.isOp()) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lACORN  &cAccess to this command was denied."));
                    return true;
                }

                boolean toggled = Verbose.toggle((Player)sender);

                if (toggled) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lACORN  &aVerbose messages have been enabled for you."));
                } else sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lACORN  &cVerbose messages have been disabled for you."));

                return true;
            }
        }

        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lACORN  &7Running Acorn version BETA.\n&6&lACORN  &bhttps://github.com/Aunto-Development-Group/Acorn\n\n&e/acorn&7, &e/acorn reload&7, &e/acorn status&7, &e/acorn verbose"));
        return true;
    }
}