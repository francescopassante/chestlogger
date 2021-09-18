package com.francescopassante.chestlogger.InventoryManager;

import java.util.List;

import org.bukkit.inventory.ItemStack;


import java.util.ArrayList;

public class GUIUtils {
    // allItems = 2 page = 1, availableSlots = 52
    public List<ItemStack> getPageItems(List<ItemStack> allItems, int page, int availableSlots) {
        List<ItemStack> pageItems = new ArrayList<ItemStack>();
        int upperBound = page * availableSlots; // 52
        int lowerBound = upperBound - availableSlots; // 0

        for(int i = lowerBound; i < upperBound; i++) {
            try {
                pageItems.add(allItems.get(i));
            } catch (Exception ex) {
                continue;
            }
        }

        return pageItems;
    }

    public boolean isPageValid(List<ItemStack> allItems, int page, int availableSlots) {
        if (page <= 0) {
            return false;
        }
        int upperBound = page * availableSlots;
        int lowerBound = upperBound - availableSlots;

        return allItems.size() > lowerBound;
    }
}
