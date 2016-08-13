/*
 * Copyright (C) 2015 Souleau Alexandre mrsheepsheepandcie@gmail.com
 *
 * Tint Health is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or at your option) any later version.
 *
 * Tint Health is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See http://www.gnu.org/licenses/ for more details.
 */

package fr.mrsheepsheep.tinthealth;

import org.bukkit.entity.Player;

public class THAPI {

//	API access: 
//
//	TintHealth th = (TintHealth) plugin.getServer().getPluginManager().getPlugin("TintHealth");
//	THAPI api = th.getAPI();
//  Then use api to call methods listed here.
//  If you want a new API method, ask on the plugin discussion:
//  http://www.spigotmc.org/threads/tint-health.62428/
	
	private TintHealth plugin;
	private THFunctions functions;
	
	protected THAPI(TintHealth plugin){
		this.plugin = plugin;
		this.functions = plugin.functions;
	}

	public void setTint(Player p, int percentage){
		// Sets the tint to percentage (eg. 95% of red).
		functions.setBorder(p, 100 - percentage);
	}
	
	public void fadeTint(Player p, int startpercentage, int timeInSeconds){
		// Fades the tint from startpercentage (eg. 95% of red) to 0 (no red)
		// Does not check permissions.
		functions.fadeBorder(p, 100 - startpercentage, timeInSeconds);
	}
	
	public void removeTint(Player p){
		// Sets the tint to 0.
		functions.removeBorder(p);
	}
	
	public int getIntensityModifier(){
		return plugin.intensity;
	}
	
	public boolean isFadeEnabled(){
		return plugin.fade;
	}
	
	public int getFadeTime(){
		return plugin.fadetime;
	}
	
	public void sendTint(Player p, int percentage){
		// This sends a tint percentage to the player then fades it using the plugin configuration.
		// It also detects if fade mode is enabled.
		functions.sendBorder(p, 100 - percentage);
	}
	
	public boolean isTHEnabled(){
		return plugin.enabled;
	}
	
	public void enable(){
		// Enables default plugin behavior if disabled
		plugin.enabled = true;
	}
	
	public void disable(){
		// Disables default plugin behavior
		plugin.enabled = false;
	}
}
