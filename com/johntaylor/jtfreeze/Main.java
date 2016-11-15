package com.johntaylor.jtfreeze;

import java.io.File;

import org.bukkit.plugin.java.JavaPlugin;

import com.johntaylor.jtfreeze.commands.CommandEx;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin {
	
	public static String teamspeak;
	public static int minutes;
	private static Main main;
	public static boolean MessageOnMove;
	
	public void onEnable() {
		main = this;
		createConfig();
		getCommand("freeze").setExecutor(new CommandEx());
		teamspeak = getConfig().getString("Teamspeak");
		minutes = getConfig().getInt("Minutes");
		MessageOnMove = getConfig().getBoolean("MessageOnMove");
	}
	
	public static Main getInstance() {
		return main;
	}
	
	private void createConfig() {
	    try {
	        if (!getDataFolder().exists()) {
	            getDataFolder().mkdirs();
	        }
	        File file = new File(getDataFolder(), "config.yml");
	        if (!file.exists()) {
	            getLogger().info("Config.yml not found, creating!");
	            saveDefaultConfig();
	        } else {
	            getLogger().info("Config.yml found, loading!");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}