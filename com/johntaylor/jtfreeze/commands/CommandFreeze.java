package com.johntaylor.jtfreeze.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.johntaylor.jtfreeze.Main;
import com.johntaylor.jtfreeze.utils.FreezeUtils;

import net.md_5.bungee.api.ChatColor;

public class CommandFreeze {
	
	public static void onCommand(CommandSender sender, Command cmd, String args[]) {
		if(!sender.hasPermission("jtfreeze.freeze")) {
			sender.sendMessage(ChatColor.RED + "No permission."); return;
		}
		if(args.length != 1) {
			sender.sendMessage(ChatColor.RED + "/freeze [player]"); return;
		}
		Player player = Bukkit.getPlayer(args[0]);
		if((player != null)) {
			if(FreezeUtils.hasFreeze(player)) {
				FreezeUtils.removeFreeze(player);
				sender.sendMessage(ChatColor.DARK_RED + player.getName() + ChatColor.RED + " has been unfrozen!");
				player.sendMessage(ChatColor.RED + "You have been unfrozen!");
			} else {
				new FreezeUtils(player).run();
				sender.sendMessage(ChatColor.DARK_RED + player.getName() + ChatColor.RED +  " has been frozen.");
				player.sendMessage(ChatColor.RED + "You have been frozen, you have " + ChatColor.DARK_RED + Main.minutes + " minutes " + ChatColor.RED + "to join " + ChatColor.DARK_RED + Main.teamspeak + ChatColor.RED + ".");
			}
		} else {
			sender.sendMessage(ChatColor.RED + "Player not found.");
		}		
	}
}