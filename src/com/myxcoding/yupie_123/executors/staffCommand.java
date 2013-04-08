package com.myxcoding.yupie_123.executors;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.myxcoding.yupie_123.YuPball;
import com.myxcoding.yupie_123.listeners.playerJoinEvents;

public class staffCommand implements CommandExecutor {

	@SuppressWarnings("unused")
	private YuPball plugin;

	public staffCommand(YuPball plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			player.sendMessage(ChatColor.AQUA + "Staff Online:");
			player.sendMessage(ChatColor.YELLOW + "Mods: " + ChatColor.GRAY
					+ playerJoinEvents.mods);
			player.sendMessage(ChatColor.GREEN + "Admins: " + ChatColor.GRAY
					+ playerJoinEvents.admins);
			player.sendMessage(ChatColor.AQUA + "Server Staff:");
			player.sendMessage(ChatColor.YELLOW + "Mods: " + ChatColor.GRAY);
			player.sendMessage(ChatColor.GREEN + "Admins: " + ChatColor.GRAY);
		}
		return false;
	}

}
