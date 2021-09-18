package com.francescopassante.chestlogger.events;

import com.francescopassante.chestlogger.Main;
import com.francescopassante.chestlogger.utilities.Utilities;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;


public class ChestDestroyedListener implements Listener {
    private Main plugin;

    public ChestDestroyedListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChestDestroyed(BlockBreakEvent e) {
        Material blockType = e.getBlock().getType();
        if (blockType.equals(Material.CHEST) || blockType.equals(Material.TRAPPED_CHEST)) {
            Block chest = e.getBlock();
            String chestUniqueId = Utilities.getUniqueChestId(chest);
            if (isControlled(chestUniqueId)) {
                String grieferUUID = e.getPlayer().getUniqueId().toString();

//              Triggers only if the player who broke the chest isn't the owner
                if (!grieferUUID.equalsIgnoreCase((String)plugin.getChestsConfig().get(chestUniqueId + ".owner"))) {
                    plugin.getChestsConfig().set(chestUniqueId + ".destroyer", grieferUUID);
                } else {
                    plugin.getChestsConfig().set(chestUniqueId, null);
                }
                plugin.saveChestConfig();
            }
        }
    }

    private boolean isControlled(String chestUniqueId) {
        return plugin.getChestsConfig().contains(chestUniqueId);
    }
}
