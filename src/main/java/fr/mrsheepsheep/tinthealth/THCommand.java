/*
 * Copyright (C) 2015 Souleau Alexandre mrsheepsheepandcie@gmail.com
 *
 * Tint Health is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or at your option) any later version.
 *
 * Tint Health is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See http://www.gnu.org/licenses/ for more details.
 */

package fr.mrsheepsheep.tinthealth;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class THCommand implements CommandExecutor {
    
    TintHealth plugin;
    final String heart = "\u2764 ";
    final String bloodurl = "https://www.dropbox.com/s/dxg210a9uajqvn3/BloodScreen%20by%20Macaron.zip?dl=1";
    
    public THCommand(TintHealth plugin){
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                if (sender.hasPermission("tinthealth.reload")) {
                    plugin.reloadConfig();
                    plugin.loadConfig();
                    sender.sendMessage(ChatColor.GREEN + "TintHealth configuration reloaded");
                }
                else {
                    String prefix = ChatColor.DARK_RED + heart + ChatColor.RED;
                    sender.sendMessage(prefix + "You don't have permission.");
                }
            }
            else if (args[0].equalsIgnoreCase("toggle")) {
                if (sender instanceof Player){
                    String prefix = ChatColor.DARK_RED + heart + ChatColor.RED;
                    Player p = (Player) sender;
                    if (p.hasPermission("tinthealth.toggle")){
                        plugin.functions.togglePlayerTint(p);
                        if (plugin.functions.isTintEnabled(p)) p.sendMessage(prefix + "Tint is now " + ChatColor.GREEN + "enabled" + ChatColor.RED + ".");
                        else p.sendMessage(prefix + "Tint is now " + ChatColor.GRAY + "disabled" + ChatColor.RED + ".");
                    }
                    else p.sendMessage(prefix + "You don't have permission.");
                }
                else sender.sendMessage("This is an in-game only command !");
            }
            else if (args[0].equalsIgnoreCase("blood")) {
                if (sender instanceof Player) {
                    String prefix = ChatColor.DARK_RED + heart + ChatColor.RED;
                    Player p = (Player) sender;
                    if (p.hasPermission("tinthealth.blood")){
                        p.setResourcePack(bloodurl);
                        p.sendMessage(prefix + "Blood texture has been sent. If you did not enable server resource pack, nothing will happen.");
                    }
                    else p.sendMessage("You don't have permission");
                }
                else sender.sendMessage("This is an in-game only command !");
            }
            else sendHelpMessage(sender);
        }
        else sendHelpMessage(sender);
        return true;
    }
    
    public void sendHelpMessage(CommandSender sender){
        String prefix = "[TintHealth] ";
        if (sender instanceof Player) prefix = ChatColor.DARK_RED + heart + ChatColor.GRAY;
        
        sender.sendMessage(prefix + ChatColor.RED + "TintHealth v" + ChatColor.GRAY + plugin.getDescription().getVersion() + ChatColor.RED + " by " + ChatColor.GRAY + "MrSheepSheep " + prefix);
        sender.sendMessage(prefix + ChatColor.YELLOW + "" + ChatColor.BOLD + "Available commands:");
        
        if (sender.hasPermission("tinthealth.reload")) {
            sender.sendMessage(prefix + "/tint reload" + ChatColor.RED + " - Reloads the config");
        }
        if (sender.hasPermission("tinthealth.toggle")) {
            sender.sendMessage(prefix + "/tint toggle" + ChatColor.RED + " - Enable / Disable the tint for you");
        }
        if (!sender.hasPermission("tinthealth.toggle") && !sender.hasPermission("tinthealth.reload")) {
            sender.sendMessage(prefix + "None !");
        }
        sender.sendMessage(prefix + "/tint blood" + ChatColor.RED + " - Install a blood texture. Credits goes to Macaron on the Spigot forums !");
    }
    
}
