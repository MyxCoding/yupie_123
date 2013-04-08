package com.myxcoding.yupie_123.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.myxcoding.yupie_123.YuPball;

public class bannedListener implements Listener {

	private YuPball plugin;

	public bannedListener(YuPball plugin) {
		this.plugin = plugin;
	}

	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (plugin.bannedPlayers.contains(player.getName()) == true) {
			player.kickPlayer("You are banned from this server for receiving too many infractions!");
		}
	}
}
