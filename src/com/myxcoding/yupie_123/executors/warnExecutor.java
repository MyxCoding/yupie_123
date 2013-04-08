package com.myxcoding.yupie_123.executors;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.myxcoding.yupie_123.YuPball;

public class warnExecutor implements CommandExecutor {

	private YuPball plugin;

	public warnExecutor(YuPball plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String commandLabel, String[] args) {

		if (plugin.warnedOnce.contains(args[0]) == false) {
			if (args.length != 1) {
				sender.sendMessage(ChatColor.RED + "Usage: /warn <player_name>");
				return true;
			}
			Player warn = plugin.getServer().getPlayer(args[0]);
			plugin.warnedOnce.add(args[0]);
			if (warn != null) {
				warn.sendMessage(ChatColor.RED
						+ "You received your first warning!");
			}
			sender.sendMessage(ChatColor.GREEN + args[0] + ChatColor.RESET
					+ " has been warned (First Warning)");
			return true;
		} else if (plugin.warnedOnce.contains(args[0]) == true) {
			if (args.length != 1) {
				sender.sendMessage(ChatColor.RED + "Usage: /warn <player_name>");
				return true;
			}

			Player warn = plugin.getServer().getPlayer(args[0]);
			plugin.warnedOnce.remove(args[0]);
			plugin.warnedTwice.add(args[0]);
			if (warn != null) {
				warn.sendMessage("You received your second warning!");
			}
			sender.sendMessage(ChatColor.GREEN + args[0] + ChatColor.RESET
					+ " has been warned (Second Warning!)");
		} else if (plugin.warnedTwice.contains(args[0]) == true) {
			if (args.length != 1) {
				sender.sendMessage(ChatColor.RED + "Usage: /warn <player_name>");
				return true;
			}

			Player warn = plugin.getServer().getPlayer(args[0]);
			plugin.warnedTwice.remove(args[0]);
			plugin.kickedOnce.add(args[0]);
			if (warn != null) {
				warn.kickPlayer("You have received 3 Warnings. So you were kicked from the game!");
			}
			sender.sendMessage(ChatColor.GREEN
					+ args[0]
					+ ChatColor.RESET
					+ " has been warned (Third Warning!) and has been Kicked from the game!");
		} else if (plugin.kickedOnce.contains(args[0]) == true) {
			if (args.length != 1) {
				sender.sendMessage(ChatColor.RED + "Usage: /warn <player_name>");
				return true;
			}

			Player warn = plugin.getServer().getPlayer(args[0]);
			plugin.kickedOnce.remove(args[0]);
			plugin.kickedTwice.add(args[0]);
			if (warn != null) {
				warn.kickPlayer("You have received 4 Warnings. So you were kicked from the game!");
			}
			sender.sendMessage(ChatColor.GREEN
					+ args[0]
					+ ChatColor.RESET
					+ " has been warned (Fourth Warning!) and has been Kicked from the game!");
		} else if (plugin.kickedTwice.contains(args[0]) == true) {
			if (args.length != 1) {
				sender.sendMessage(ChatColor.RED + "Usage: /warn <player_name>");
				return true;
			}

			Player warn = plugin.getServer().getPlayer(args[0]);
			plugin.kickedTwice.remove(args[0]);
			plugin.bannedPlayers.add(args[0]);
			if (warn != null) {
				warn.kickPlayer("You have received 5 Warnings and therefore have been Banned!");
			}
			sender.sendMessage(ChatColor.GREEN
					+ args[0]
					+ ChatColor.RESET
					+ " has been warned (Fifth Warning!) and has been Banned from the server!");
		}
		return true;
	}
}
