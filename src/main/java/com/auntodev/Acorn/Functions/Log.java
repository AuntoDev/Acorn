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
import org.bukkit.plugin.Plugin;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Log {
    private static File file;
    static Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("Acorn");

    public static void addMessage (String message) {
        Date d = new Date();

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
            bw.append("[").append(d.toString()).append("] ").append(message).append("\n");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setup() {
        File dir = new File(plugin.getDataFolder() + "/logs");
        if (!dir.exists()) dir.mkdirs();

        Date d = new Date();
        file = new File(plugin.getDataFolder() + "/logs/" + d.toString() + ".log");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        addMessage("----- Log file setup finished. -----");
    }
}
