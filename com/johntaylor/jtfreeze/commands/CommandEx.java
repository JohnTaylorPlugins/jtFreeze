package com.johntaylor.jtfreeze.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandEx implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("freeze")) {
			CommandFreeze.onCommand(sender, cmd, args); return true;
		}
		return true;
	}
}
