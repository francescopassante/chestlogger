package com.francescopassante.chestlogger.commands;


import com.francescopassante.chestlogger.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class ChestLoggerCommands implements CommandExecutor {
    private Main plugin;
    private String prefix;

    public ChestLoggerCommands(Main plugin) {
        this.plugin = plugin;
        this.prefix = plugin.getConfig().getString("messages.prefix");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {


        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {

                if (!(sender.hasPermission("chestlogger.reload"))) {
                    sender.sendMessage(prefix + plugin.getConfig().getString("messages.no-permission"));
                    return true;
                }

                plugin.reloadConfig();
                sender.sendMessage(prefix + plugin.getConfig().getString("messages.config-reloaded"));
                return true;
            }

            if (args[0].equalsIgnoreCase("clear")) {
                if (sender instanceof Player) {
                    Player p = (Player) sender;
                    if (!(p.hasPermission("chestlogger.clear.self"))) {
                        p.sendMessage(prefix + plugin.getConfig().getString("messages.no-permission"));
                        return true;
                    }

                    if (plugin.getChestsConfig().getKeys(false) == null) {
                        p.sendMessage(prefix + plugin.getConfig().getString("messages.no-chests-to-clear"));
                        return true;
                    }

                    for (String key : plugin.getChestsConfig().getKeys(false)) {
//                    If there are some chests who are owned by the player who issued the command, they get deleted from the chests.yml
                        if (plugin.getChestsConfig().getString(key + ".owner").equalsIgnoreCase(p.getUniqueId().toString())) {
                            plugin.getChestsConfig().set(key, null);
                            plugin.saveChestConfig();
                        }
                    }

                    p.sendMessage(prefix + plugin.getConfig().getString("messages.cleared-own-chests"));
                    return true;
                } else {
                    sender.sendMessage("You must be a player in order to perform this command!");
                    return true;
                }
            }

        }

        if (args.length == 2 && args[0].equalsIgnoreCase("clear")) {
            if (!sender.hasPermission("chestlogger.clear.other")) {
                sender.sendMessage(prefix + plugin.getConfig().getString("messages.no-permission"));
                return true;
            }

            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage(prefix + plugin.getConfig().getString("messages.no-such-player").replace("%target", target.getName()));
                return true;
            }

            for (String key : plugin.getChestsConfig().getKeys(false)) {
//                    If there are some chests who are owned by the target, they get deleted from the chests.yml
                if (plugin.getChestsConfig().getString(key + ".owner").equalsIgnoreCase(target.getUniqueId().toString())) {
                    plugin.getChestsConfig().set(key, null);
                    plugin.saveChestConfig();
                }
            }

            sender.sendMessage(plugin.getConfig().getString("messages.cleared-other-chests").replace("%target", target.getName()));


        }


        return true;
    }
}
