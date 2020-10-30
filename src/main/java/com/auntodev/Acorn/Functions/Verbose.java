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

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class Verbose {
    private final static HashMap<UUID, Boolean> map = new HashMap<UUID, Boolean>();

    public static HashMap<UUID, Boolean> get () {
        return map;
    }

    public static boolean toggle (Player player) {
        if (map.containsKey(player.getUniqueId())) {
            if (map.get(player.getUniqueId())) {
                map.remove(player.getUniqueId());
                return false;
            } else {
                map.put(player.getUniqueId(), true);
                return true;
            }
        } else {
            map.put(player.getUniqueId(), true);
            return true;
        }
    }
}
