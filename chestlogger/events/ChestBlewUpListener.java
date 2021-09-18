package com.francescopassante.chestlogger.events;

import com.francescopassante.chestlogger.Main;
import com.francescopassante.chestlogger.utilities.Utilities;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.List;

public class ChestBlewUpListener implements Listener {
    private Main plugin;

    public ChestBlewUpListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onTNTExplosion(EntityExplodeEvent e) {
        if (e.getEntityType().equals(EntityType.PRIMED_TNT)) {

            TNTPrimed tnt = (TNTPrimed) e.getEntity();
            Entity destroyer = tnt.getSource();

            List<Block> destroyedBlocks = e.blockList();
//            Checks if a controlled chest has been destroyed by the explosion and adds a destroyer if the TNT was placed by a player
            for (Block i : destroyedBlocks) {
                if (i.getType().equals(Material.CHEST) || i.getType().equals(Material.TRAPPED_CHEST)) {
                    String chestUUID = Utilities.getUniqueChestId(i);
                    if (plugin.getChestsConfig().contains(chestUUID)) {
                        if (destroyer instanceof Player) {
//                            If destroyer == owner, simply deletes the chest from the protected chest list
                            if (destroyer.getUniqueId().toString().equalsIgnoreCase(plugin.getChestsConfig().getString(chestUUID + ".owner"))) {
                                plugin.getChestsConfig().set(chestUUID, null);
                            } else {
                                plugin.getChestsConfig().set(chestUUID + ".destroyer", ((Player) destroyer).getUniqueId().toString());
                                plugin.saveChestConfig();
                            }

                        }
                    }
                }
            }
        }
    }
}
