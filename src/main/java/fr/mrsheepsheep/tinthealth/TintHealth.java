/*
 * Copyright (C) 2015 Souleau Alexandre mrsheepsheepandcie@gmail.com
 *
 * Tint Health is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or at your option) any later version.
 *
 * Tint Health is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See http://www.gnu.org/licenses/ for more details.
 */

package fr.mrsheepsheep.tinthealth;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class TintHealth extends JavaPlugin {
    
    private static TintHealth plugin;
    public THFunctions functions;
    public boolean fade = false;
    protected int fadetime = 5;
    protected int intensity = 1;
    protected int minhearts = -1;
    protected boolean damagemode = false;
    protected boolean enabled = true;
    protected boolean debug = false;
    
    public static TintHealth getInstance() {
        return plugin;
    }
    
    @Override
    public void onEnable() {
        this.plugin = this;
        loadMetrics();
        loadConfig();
        loadPlayerToggles();
        functions = new THFunctions(this);
        if (enabled)
            new PlayerListener(this);
        getCommand("tinthealth").setExecutor(new THCommand(this));
    }
    
    @Override
    public void onDisable() {
        savePlayerToggles();
    }
    
    private void loadMetrics() {
        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException e) {
            getLogger().warning("Metrics cannot be started");
            e.printStackTrace();
        }
    }
    
    public void loadConfig() {
        
        FileConfiguration config = getConfig();
        config.options().copyDefaults(true);
        
        fade = config.getBoolean("options.fade-enabled");
        fadetime = config.getInt("options.fade-time");
        intensity = config.getInt("options.intensity-modifier");
        damagemode = config.getBoolean("damage-mode.enabled");
        minhearts = config.getInt("options.minimum-health");
        enabled = config.getBoolean("options.enabled");
        debug = config.getBoolean("options.debug");
        if (intensity < 1) {
            config.set("options.intensity-modifier", 1);
            intensity = 1;
            getLogger().warning("Intensity modifier cannot be less than 1. Changing to 1.");
        }
        
        if (minhearts < 0) {
            minhearts = 100000;
        }
        saveConfig();
        getLogger().info("Configuration successfully loaded");
    }
    
    public void debug(String message) {
        if (debug)
            getLogger().info("[DEBUG] " + message);
    }
    
    private void loadPlayerToggles() {
        File f = new File("plugins/TintHealth", "disabledPlayers.yml");
        YamlConfiguration fc = YamlConfiguration.loadConfiguration(f);
        fc.options().copyDefaults(true);
        fc.addDefault("players", null);
        if (fc.getStringList("players") != null) {
            for (String pname : fc.getStringList("players"))
                functions.togglelist.add(pname);
        }
        try {
            fc.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        getLogger().info(fc.getStringList("players").size() + " player toggles loaded");
    }
    
    private void savePlayerToggles() {
        File f = new File("plugins/TintHealth", "disabledPlayers.yml");
        YamlConfiguration fc = YamlConfiguration.loadConfiguration(f);
        List<String> list = new ArrayList<String>();
        for (String pname : functions.togglelist) {
            list.add(pname);
        }
        fc.set("players", list);
        try {
            fc.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        getLogger().info(list.size() + " player toggles saved");
    }
}
