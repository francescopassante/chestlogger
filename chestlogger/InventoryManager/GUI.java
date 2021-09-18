package com.francescopassante.chestlogger.InventoryManager;

import com.francescopassante.chestlogger.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GUI {
    private Main plugin;

    public GUI(Main plugin, Player p, int page) {
        this.plugin = plugin;

        Inventory gui = Bukkit.createInventory(null, 54, plugin.getConfig().getString("chests.inventory-title").replace("%owner", p.getName()).replace("%page", String.valueOf(page)));
        List<ItemStack> allItems = new ArrayList<ItemStack>();
//      Fill allItems with the chests that have to show up in the /chests GUI.
        setUpItems(p, allItems);
        GUIUtils guiUtils = new GUIUtils();

        ItemStack previousPage;
        ItemStack nextPage;
        ItemMeta previousMeta;
        ItemMeta nextMeta;
        if (guiUtils.isPageValid(allItems, page - 1, 45)) {
            previousPage = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1);
            previousMeta = previousPage.getItemMeta();
            previousMeta.setDisplayName(ChatColor.GREEN + "Previous page");
        } else {
            previousPage = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
            previousMeta = previousPage.getItemMeta();
            previousMeta.setDisplayName(ChatColor.RED + "No page available");
        }
        if (guiUtils.isPageValid(allItems, page + 1, 45)) {
            nextPage = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1);
            nextMeta = nextPage.getItemMeta();
            nextMeta.setDisplayName(ChatColor.GREEN + "Next page");
        } else {
            nextPage = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
            nextMeta = nextPage.getItemMeta();
            nextMeta.setDisplayName(ChatColor.RED + "No page available");
        }
    //  Needed to pass the current page to the InventoryClick Listener
        previousMeta.setLocalizedName("" + page);
        nextMeta.setLocalizedName("" + page);
        previousPage.setItemMeta(previousMeta);
        nextPage.setItemMeta(nextMeta);
        gui.setItem(48, previousPage);
        gui.setItem(50, nextPage);

        List<ItemStack> pageItems = guiUtils.getPageItems(allItems, page, 45);
        for (ItemStack item: pageItems) {
            gui.setItem(gui.firstEmpty(), item);
        }

        p.openInventory(gui);
    }


    private void setUpItems(Player p, List<ItemStack> allItems) {
        for (String chestUUID: plugin.getChestsConfig().getKeys(false)) {
            if (plugin.getChestsConfig().getString(chestUUID + ".owner").equalsIgnoreCase(p.getUniqueId().toString())) {
                String[] data = chestUUID.split("/");
                String x = data[0];
                String y = data[1];
                String z = data[2];
                String world = data[3];

                ItemStack chest = new ItemStack(Material.CHEST, 1);

                ItemMeta meta = chest.getItemMeta();
                meta.setDisplayName(plugin.getConfig().getString("chests.display-name"));

                List<String> lore = new ArrayList<String>();
                lore.add(plugin.getConfig().getString("chests.first-line").replace("%x", x).replace("%y", y).replace("%z", z).replace("%world", world));
                lore.add(plugin.getConfig().getString("chests.second-line").replace("%x", x).replace("%y", y).replace("%z", z).replace("%world", world));
                lore.add(plugin.getConfig().getString("chests.third-line").replace("%x", x).replace("%y", y).replace("%z", z).replace("%world", world));
                lore.add(plugin.getConfig().getString("chests.fourth-line").replace("%x", x).replace("%y", y).replace("%z", z).replace("%world", world));

                if (plugin.getChestsConfig().get(chestUUID + ".destroyer") != null) {
                    String destroyer = Bukkit.getPlayer(UUID.fromString(plugin.getChestsConfig().getString(chestUUID + ".destroyer"))).getName();
                    lore.add(plugin.getConfig().getString("chests.destroyed-by-line").replace("%destroyer", destroyer));
                }

                meta.setLore(lore);
                chest.setItemMeta(meta);
                allItems.add(chest);
            }
        }
    }


}
