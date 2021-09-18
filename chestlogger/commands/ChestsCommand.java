package com.francescopassante.chestlogger.commands;

import com.francescopassante.chestlogger.InventoryManager.GUI;
import com.francescopassante.chestlogger.Main;

import org.bukkit.ChatColor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class ChestsCommand implements CommandExecutor {
    private Main plugin;
    private String prefix;

    public ChestsCommand(Main plugin) {
        this.plugin = plugin;
        this.prefix = plugin.getConfig().getString("messages.prefix");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player in order to perform this command!");
            return true;
        }

        Player p = (Player) sender;

        if (!(p.hasPermission("chestlogger.chests"))) {
            p.sendMessage(prefix + plugin.getConfig().getString("messages.no-permission"));
            return true;
        }

        GUI gui = new GUI(plugin, p, 1);

        return true;
    }

}
