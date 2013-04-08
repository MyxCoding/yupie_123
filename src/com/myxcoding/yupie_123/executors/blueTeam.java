package com.myxcoding.yupie_123.executors;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.myxcoding.yupie_123.YuPball;
import com.myxcoding.yupie_123.listeners.playerJoinEvents;

public class blueTeam implements CommandExecutor {

	@SuppressWarnings("unused")
	private YuPball plugin;

	public blueTeam(YuPball plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command command,
			String commandLabel, String[] args) {
		if (commandLabel.equalsIgnoreCase("blue")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (playerJoinEvents.redTeam.contains(player.getName())) {
					playerJoinEvents.redTeam.remove(player.getName() + " , ");
					playerJoinEvents.blueTeam.add(player.getName() + " , ");
				} else if (playerJoinEvents.blueTeam.contains(player.getName())) {
					player.sendMessage("You are already on the"
							+ ChatColor.BLUE + " Blue" + ChatColor.RESET
							+ " team!");
				}
			}
		}
		return false;
	}
}
