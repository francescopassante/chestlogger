package com.francescopassante.chestlogger;

import com.francescopassante.chestlogger.commands.ChestLoggerCommands;
import com.francescopassante.chestlogger.commands.ChestsCommand;
import com.francescopassante.chestlogger.events.ChestBlewUpListener;
import com.francescopassante.chestlogger.events.ChestDestroyedListener;
import com.francescopassante.chestlogger.events.ChestPlacedListener;
import com.francescopassante.chestlogger.events.InventoryClickedListener;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Main extends JavaPlugin {

    private File chestsConfigFile;
    private FileConfiguration chestsConfig;


    public void onEnable() {
        saveDefaultConfig();
        createCustomConfig();
        getServer().getConsoleSender().sendMessage("ChestLogger activated");

        getServer().getPluginManager().registerEvents(new ChestBlewUpListener(this), this);
        getServer().getPluginManager().registerEvents(new InventoryClickedListener(this), this);
        getServer().getPluginManager().registerEvents(new ChestPlacedListener(this), this);
        getServer().getPluginManager().registerEvents(new ChestDestroyedListener(this), this);

        getCommand("chestlogger").setExecutor(new ChestLoggerCommands(this));
        getCommand("chests").setExecutor(new ChestsCommand(this));
    }

    public void onDisable() {
        getServer().getConsoleSender().sendMessage("ChestLogger deactivated");
    }

    private void createCustomConfig() {
        chestsConfigFile = new File(getDataFolder(), "chests.yml");
        if (!chestsConfigFile.exists()) {
            chestsConfigFile.getParentFile().mkdirs();
            saveResource("chests.yml", false);
        }

        chestsConfig = new YamlConfiguration();
        try {
            chestsConfig.load(chestsConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getChestsConfig() {
        return this.chestsConfig;
    }

    public void saveChestConfig() {
        try {
            this.chestsConfig.save(this.chestsConfigFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
