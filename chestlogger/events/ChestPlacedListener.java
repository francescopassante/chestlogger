package com.francescopassante.chestlogger.events;

import com.francescopassante.chestlogger.Main;
import com.francescopassante.chestlogger.utilities.Utilities;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;



public class ChestPlacedListener implements Listener {
    private final Main plugin;
    private final String prefix;

    public ChestPlacedListener(Main plugin) {
        this.plugin = plugin;
        this.prefix = plugin.getConfig().getString("messages.prefix");
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Material blockType = e.getBlock().getType();
        if (blockType == Material.CHEST || blockType == Material.TRAPPED_CHEST) {
            Player owner = e.getPlayer();
            Block block = e.getBlock();
            plugin.getChestsConfig().set(Utilities.getUniqueChestId(block) + ".owner", owner.getUniqueId().toString());
            plugin.getChestsConfig().options().copyDefaults(true);
            plugin.saveChestConfig();
            if (plugin.getConfig().getBoolean("settings.send-message-on-chest-placed")) {
                owner.sendMessage(prefix + plugin.getConfig().getString("messages.chest-placed-message"));
            }
        }
    }

}
