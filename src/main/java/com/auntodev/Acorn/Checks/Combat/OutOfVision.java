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

package com.auntodev.Acorn.Checks.Combat;

import com.auntodev.Acorn.Checks.Check;
import com.auntodev.Acorn.Functions.Config;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.ArrayList;
import java.util.Set;

/*

    [COMBAT] OutOfVision

     -> Prevents players from hitting entities that
        aren't in their view.
     -> Really good at detecting KillAura.
     -> May still detect KillAura even when the
        "Lock view" mode is enabled.

 */

public class OutOfVision extends Check implements Listener {
    @EventHandler (priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDamageByEntity (EntityDamageByEntityEvent event) {
        FileConfiguration config = Config.get();
        if (!(event.getDamager() instanceof Player)) return;

        Player attacker = (Player)event.getDamager();
        ArrayList<Entity> list = getNearestEntityInSight(attacker);
        if (list.contains(event.getEntity())) return;

        flag(attacker, "Combat.OutOfVision", "&7Attempted to attack &c" + event.getEntity().getName() + " &7whilst not looking at them.");
        if (config.getInt("out-of-vision.mitigation.cancel") > 0) attackCancel(attacker, "Combat.OutOfVision", event);
    }

    /*
        Credit for the getNearestEntityInSight function goes to:
        https://stackoverflow.com/users/6727559/phantomunicorns

        who posted it here:
        https://stackoverflow.com/a/39003086
     */

    private static ArrayList<Entity> getNearestEntityInSight(Player player) {
        ArrayList<Entity> entities = (ArrayList<Entity>) player.getNearbyEntities(5, 5, 5);
        ArrayList<Block> sightBlock = (ArrayList<Block>) player.getLineOfSight( (Set<Material>) null, 5);
        ArrayList<Location> sight = new ArrayList<Location>();
        ArrayList<Entity> list = new ArrayList<Entity>();

        for (int i = 0;i<sightBlock.size();i++)
            sight.add(sightBlock.get(i).getLocation());
        for (int i = 0;i<sight.size();i++) {
            for (int k = 0;k<entities.size();k++) {
                if (Math.abs(entities.get(k).getLocation().getX()-sight.get(i).getX())<1.3) {
                    if (Math.abs(entities.get(k).getLocation().getY()-sight.get(i).getY())<1.5) {
                        if (Math.abs(entities.get(k).getLocation().getZ()-sight.get(i).getZ())<1.3) {
                            list.add(entities.get(k));
                        }
                    }
                }
            }
        }

        return list;
    }
}
