package com.francescopassante.chestlogger.events;

import com.francescopassante.chestlogger.InventoryManager.GUI;
import com.francescopassante.chestlogger.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryClickedListener implements Listener {
    private Main plugin;

    public InventoryClickedListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getView().getTitle().equalsIgnoreCase(plugin.getConfig().getString("chests.inventory-title").replace("%owner", p.getName()))) {
            e.setCancelled(true);
            ItemStack clickedItem = e.getCurrentItem();
            if (clickedItem != null && clickedItem.hasItemMeta()) {
                if (clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "Next page")) {
                    int page = Integer.parseInt(clickedItem.getItemMeta().getLocalizedName());
                    p.closeInventory();
                    new GUI(plugin, p, page + 1);
                } else if (clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "Previous page")) {
                    int page = Integer.parseInt(clickedItem.getItemMeta().getLocalizedName());
                    p.closeInventory();
                    new GUI(plugin, p, page - 1);
                }
            }
        }
    }

}
