package com.myxcoding.yupie_123.executors;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.myxcoding.yupie_123.YuPball;
import com.myxcoding.yupie_123.listeners.playerJoinEvents;

public class redTeam implements CommandExecutor {

	@SuppressWarnings("unused")
	private YuPball plugin;

	public redTeam(YuPball plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String commandLabel, String[] args) {
		if (commandLabel.equalsIgnoreCase("red")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (playerJoinEvents.blueTeam.contains(player.getName())) {
					playerJoinEvents.blueTeam.remove(player.getName() + " , ");
					playerJoinEvents.redTeam.add(player.getName() + " , ");
				} else if (playerJoinEvents.redTeam.contains(player.getName())) {
					player.sendMessage("You are already on the" + ChatColor.RED
							+ " Red" + ChatColor.RESET + " team!");
				}
			}
		}
		return false;
	}
}
