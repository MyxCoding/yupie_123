package com.myxcoding.yupie_123.listeners;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.myxcoding.yupie_123.YuPball;

public class playerJoinEvents implements Listener {
	public static ArrayList<String> redTeam = new ArrayList<String>();
	public static ArrayList<String> blueTeam = new ArrayList<String>();
	public static ArrayList<String> admins = new ArrayList<String>();
	public static ArrayList<String> mods = new ArrayList<String>();

	private YuPball plugin;

	private String modDisplay = ChatColor.AQUA + "[" + ChatColor.YELLOW + "Mod"
			+ ChatColor.AQUA + "]";
	private String adminDisplay = ChatColor.AQUA + "[" + ChatColor.GREEN
			+ "Admin" + ChatColor.AQUA + "]";
	private String ownerDisplay = ChatColor.AQUA + "[" + ChatColor.DARK_BLUE
			+ "O" + ChatColor.DARK_GREEN + "w" + ChatColor.DARK_AQUA + "n"
			+ ChatColor.DARK_RED + "e" + ChatColor.DARK_PURPLE + "r"
			+ ChatColor.AQUA + "]";
	private String co_ownerDisplay = ChatColor.AQUA + "[" + ChatColor.BLACK
			+ "Co-" + ChatColor.YELLOW + "Own" + ChatColor.DARK_RED + "er"
			+ ChatColor.AQUA + "]";

	public playerJoinEvents(YuPball plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		int redTeamAmount = plugin.redTeamAmount;
		int blueTeamAmount = plugin.blueTeamAmount;
		if (player.hasPlayedBefore()) {
			if (plugin.admins.contains(player.getName())) {
				admins.add(player.getName());
				player.sendMessage(ChatColor.GOLD + "Welcome back "
						+ ChatColor.GREEN + player.getName());
				Bukkit.broadcastMessage("An Admin has joined the game!");
			} else if (plugin.mods.contains(player.getName())) {
				mods.add(player.getName());
				player.sendMessage(ChatColor.GOLD + "Welcome back "
						+ ChatColor.GREEN + player.getName());
				Bukkit.broadcastMessage("A Moderator has joined the game!");
			} else {
				player.sendMessage(ChatColor.GOLD + "Welcome back "
						+ ChatColor.GREEN + player.getName());
			}
		} else {
			player.setExp(1);
			player.sendMessage(ChatColor.GOLD + "Welcome " + ChatColor.GREEN
					+ player.getName());
			Bukkit.broadcastMessage("Welcome " + ChatColor.GREEN
					+ player.getName() + ChatColor.RESET + " to the server!");
		}

		if (redTeamAmount < blueTeamAmount) {
			redTeamAmount++;
			redTeam.add(player.getName());
			player.setDisplayName(ChatColor.RED + player.getName());
			player.sendMessage("You have been Placed in the " + ChatColor.RED
					+ "Red " + ChatColor.RESET + "Team");
		} else if (blueTeamAmount < redTeamAmount) {
			blueTeamAmount--;
			blueTeam.add(player.getName());
			player.setDisplayName(ChatColor.BLUE + player.getName());
			player.sendMessage("You have been Placed in the " + ChatColor.BLUE
					+ "Blue " + ChatColor.RESET + "Team");
		} else if (redTeamAmount == blueTeamAmount) {
			redTeamAmount++;
			redTeam.add(player.getName());
			player.setDisplayName(ChatColor.RED + player.getName());
			player.sendMessage("You have been Placed in the " + ChatColor.RED
					+ "Red " + ChatColor.RESET + "Team");
		}
		String playerName = event.getPlayer().getName();
		if (plugin.mods.contains(playerName)) {
			player.setDisplayName(this.modDisplay + ChatColor.RED + playerName);
		} else if (plugin.admins.contains(playerName)) {
			player.setDisplayName(this.adminDisplay + ChatColor.DARK_RED
					+ playerName);
		} else if (plugin.owner.contains(playerName)) {
			player.setDisplayName(this.ownerDisplay + ChatColor.BLUE
					+ playerName);
		} else if (plugin.co_owner.contains(playerName)) {
			player.setDisplayName(this.co_ownerDisplay + ChatColor.BLUE
					+ playerName);
		} else if (plugin.speedy.contains(playerName)) {
			player.setDisplayName(this.co_ownerDisplay + ChatColor.BLUE
					+ playerName);
		} else if (plugin.soldier.contains(playerName)) {
			player.setDisplayName(this.co_ownerDisplay + ChatColor.BLUE
					+ playerName);
		} else if (plugin.heavy.contains(playerName)) {
			player.setDisplayName(this.co_ownerDisplay + ChatColor.BLUE
					+ playerName);
		} else if (plugin.doctor.contains(playerName)) {
			player.setDisplayName(this.co_ownerDisplay + ChatColor.BLUE
					+ playerName);
		} else if (plugin.spy.contains(playerName)) {
			player.setDisplayName(this.co_ownerDisplay + ChatColor.BLUE
					+ playerName);
		} else if (plugin.sniper.contains(playerName)) {
			player.setDisplayName(this.co_ownerDisplay + ChatColor.BLUE
					+ playerName);
		} else if (plugin.bomberman.contains(playerName)) {
			player.setDisplayName(this.co_ownerDisplay + ChatColor.BLUE
					+ playerName);
		} else if (plugin.supporter.contains(playerName)) {
			player.setDisplayName(this.co_ownerDisplay + ChatColor.BLUE
					+ playerName);
		} else if (plugin.premium.contains(playerName)) {
			player.setDisplayName(this.co_ownerDisplay + ChatColor.BLUE
					+ playerName);
		} else if (plugin.precious.contains(playerName)) {
			player.setDisplayName(this.co_ownerDisplay + ChatColor.BLUE
					+ playerName);
		} else if (plugin.ninja.contains(playerName)) {
			player.setDisplayName(this.co_ownerDisplay + ChatColor.BLUE
					+ playerName);
		} else if (plugin.precious_plus.contains(playerName)) {
			player.setDisplayName(this.co_ownerDisplay + ChatColor.BLUE
					+ playerName);
		}
	}
}