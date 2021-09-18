package com.francescopassante.chestlogger.utilities;

import org.bukkit.block.Block;

public class Utilities {


    public static String getUniqueChestId(Block block) {
        return block.getX() + "/" + block.getY() + "/" + block.getZ() + "/" +  block.getWorld().getName();
    }

    public static String createUniqueChestId(double blockX, double blockY, double blockZ, String worldName) {
        return blockX + "/" + blockY + "/" + blockZ + "/" +  worldName;
    }
}
