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

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.*;

public class Config {
    private static final Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("Acorn");
    private static FileConfiguration customConfig = null;
    private static FileConfiguration messageConfig = null;
    private static File customConfigFile = null;
    private static File messageConfigFile = null;

    public static void reload () {
        try {
            if (customConfigFile == null) customConfigFile = new File(plugin.getDataFolder(), "config.yml");
            customConfig = YamlConfiguration.loadConfiguration(customConfigFile);

            Reader defConfigStream = new InputStreamReader(plugin.getResource("config.yml"), "UTF8");
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            customConfig.setDefaults(defConfig);
        } catch (UnsupportedEncodingException err) {
            Bukkit.getLogger().warning("Reload of config file failed!");
        }

        try {
            if (messageConfigFile == null) messageConfigFile = new File(plugin.getDataFolder(), "messages.yml");
            messageConfig = YamlConfiguration.loadConfiguration(messageConfigFile);

            Reader defConfigStream = new InputStreamReader(plugin.getResource("messages.yml"), "UTF8");
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            messageConfig.setDefaults(defConfig);
        } catch (UnsupportedEncodingException err) {
            Bukkit.getLogger().warning("Reload of messages file failed!");
        }
    }

    public static FileConfiguration get () {
        if (customConfig == null) reload();
        return customConfig;
    }

    public static FileConfiguration messages () {
        if (messageConfig == null) reload();
        return messageConfig;
    }

    public static void ensure() {
        if (customConfigFile == null) customConfigFile = new File(plugin.getDataFolder(), "config.yml");
        if (!customConfigFile.exists()) plugin.saveResource("config.yml", false);

        if (messageConfigFile == null) messageConfigFile = new File(plugin.getDataFolder(), "messages.yml");
        if (!messageConfigFile.exists()) plugin.saveResource("messages.yml", false);
    }

    public static String version () {
        return "BETA";
    }
}
