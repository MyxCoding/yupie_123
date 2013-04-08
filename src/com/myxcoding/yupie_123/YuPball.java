package com.myxcoding.yupie_123;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.myxcoding.yupie_123.executors.Shop;
import com.myxcoding.yupie_123.executors.YB;
import com.myxcoding.yupie_123.executors.blueTeam;
import com.myxcoding.yupie_123.executors.pointsExecutor;
import com.myxcoding.yupie_123.executors.redTeam;
import com.myxcoding.yupie_123.executors.staffCommand;
import com.myxcoding.yupie_123.executors.warnExecutor;
import com.myxcoding.yupie_123.listeners.bannedListener;
import com.myxcoding.yupie_123.listeners.playerDeathEvents;
import com.myxcoding.yupie_123.listeners.playerJoinEvents;
import com.myxcoding.yupie_123.listeners.snowballDamage;
import com.myxcoding.yupie_123.util.ListStore;

public class YuPball extends JavaPlugin implements Listener {
	public static final Logger log = Logger.getLogger("Minecraft");
	public final playerJoinEvents pje = new playerJoinEvents(null);
	public final bannedListener bL = new bannedListener(null);
	public final playerDeathEvents pDE = new playerDeathEvents();
	public final snowballDamage sD = new snowballDamage(null);
	public HashMap<String, Integer> points = new HashMap<String, Integer>();
	public int redTeamAmount;
	public int blueTeamAmount;
	public ListStore warnedOnce;
	public ListStore warnedTwice;
	public ListStore kickedOnce;
	public ListStore kickedTwice;
	public ListStore bannedPlayers;
	public ListStore mods;
	public ListStore admins;
	public ListStore owner;
	public ListStore co_owner;
	public ListStore players;
	public Inventory Shop;
	public ListStore speedy;
	public ListStore soldier;
	public ListStore heavy;
	public ListStore doctor;
	public ListStore spy;
	public ListStore sniper;
	public ListStore bomberman;
	public ListStore supporter;
	public ListStore premium;
	public ListStore precious;
	public ListStore ninja;
	public ListStore precious_plus;

	@Override
	public void onEnable() {
		redTeamAmount = 0;
		blueTeamAmount = 0;
		saveConfig();
		getServer().getPluginManager().registerEvents(this, this);
		PluginDescriptionFile pdfFile = this.getDescription();
		log.info("[" + pdfFile.getName() + "] V" + pdfFile.getVersion()
				+ " Has Been Enabled!");
		String pluginFolder = this.getDataFolder().getAbsolutePath();
		(new File(pluginFolder)).mkdirs();
		this.mods = new ListStore(new File(pluginFolder + File.separator
				+ "mods.txt"));
		this.admins = new ListStore(new File(pluginFolder + File.separator
				+ "admins.txt"));
		this.players = new ListStore(new File(pluginFolder + File.separator
				+ "players.txt"));
		this.owner = new ListStore(new File(pluginFolder + File.separator
				+ "owner.txt"));
		this.co_owner = new ListStore(new File(pluginFolder + File.separator
				+ "co-owner.txt"));
		this.warnedOnce = new ListStore(new File(pluginFolder + File.separator
				+ "warned_1.txt"));
		this.warnedTwice = new ListStore(new File(pluginFolder + File.separator
				+ "warned_2.txt"));
		this.kickedOnce = new ListStore(new File(pluginFolder + File.separator
				+ "kicked_1.txt"));
		this.kickedTwice = new ListStore(new File(pluginFolder + File.separator
				+ "kicked_2.txt"));
		this.bannedPlayers = new ListStore(new File(pluginFolder
				+ File.separator + "banned_players.txt"));
		this.speedy = new ListStore(new File(pluginFolder + File.separator
				+ "speedy_donator.txt"));
		this.soldier = new ListStore(new File(pluginFolder + File.separator
				+ "soldier_donator.txt"));
		this.heavy = new ListStore(new File(pluginFolder + File.separator
				+ "heavy_donator.txt"));
		this.doctor = new ListStore(new File(pluginFolder + File.separator
				+ "doctor_donator.txt"));
		this.spy = new ListStore(new File(pluginFolder + File.separator
				+ "spy_donator.txt"));
		this.sniper = new ListStore(new File(pluginFolder + File.separator
				+ "sniper_donator.txt"));
		this.bomberman = new ListStore(new File(pluginFolder + File.separator
				+ "bomberman_donator.txt"));
		this.supporter = new ListStore(new File(pluginFolder + File.separator
				+ "supporter_donator.txt"));
		this.premium = new ListStore(new File(pluginFolder + File.separator
				+ "premium_donator.txt"));
		this.precious = new ListStore(new File(pluginFolder + File.separator
				+ "precious_donator.txt"));
		this.ninja = new ListStore(new File(pluginFolder + File.separator
				+ "ninja_donator.txt"));
		this.precious_plus = new ListStore(new File(pluginFolder
				+ File.separator + "precious_plus_donator.txt"));

		getConfig().options().copyDefaults(true);
		saveConfig();
		this.warnedOnce.load();
		this.warnedTwice.load();
		this.kickedOnce.load();
		this.kickedTwice.load();
		this.bannedPlayers.load();
		this.owner.load();
		this.admins.load();
		this.co_owner.load();
		this.mods.load();
		this.players.load();
		this.speedy.load();
		this.soldier.load();
		this.doctor.load();
		this.heavy.load();
		this.spy.load();
		this.sniper.load();
		this.bomberman.load();
		this.supporter.load();
		this.premium.load();
		this.precious.load();
		this.ninja.load();
		this.precious_plus.load();
		this.getCommand("warn").setExecutor(new warnExecutor(this));
		this.getCommand("points").setExecutor(new pointsExecutor(this));
		this.getCommand("red").setExecutor(new redTeam(this));
		this.getCommand("blue").setExecutor(new blueTeam(this));
		this.getCommand("shop").setExecutor(new Shop(this));
		this.getCommand("yb").setExecutor(new YB(this));
		this.getCommand("pl").setExecutor(new YB(this));
		//this.getCommand("plugins").setExecutor(new YB(this));
		this.getCommand("staff").setExecutor(new staffCommand(this));
		super.onEnable();
	}

	/*
	 * private void setupConfig(FileConfiguration config) throws IOException {
	 * if (!new File(getDataFolder(), "RESET.FILE").exists()) { new
	 * File(getDataFolder(), "RESET.FILE").createNewFile(); } }
	 */
	@Override
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		log.info("[" + pdfFile.getName() + "]" + " Has Been Disabled!");
		super.onDisable();
		this.warnedOnce.save();
		this.warnedTwice.save();
		this.kickedOnce.save();
		this.kickedTwice.save();
		this.bannedPlayers.save();
		this.admins.save();
		this.mods.save();
		this.players.save();
		this.owner.save();
		this.co_owner.save();
		this.speedy.save();
		this.soldier.save();
		this.doctor.save();
		this.heavy.save();
		this.spy.save();
		this.sniper.save();
		this.bomberman.save();
		this.supporter.save();
		this.premium.save();
		this.precious.save();
		this.ninja.save();
		this.precious_plus.save();
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (player.hasPlayedBefore()) {
			if (player.hasPermission("yupball.staff.admin")) {
				player.sendMessage(ChatColor.GOLD + "Welcome back "
						+ ChatColor.GREEN + player.getName());
				Bukkit.broadcastMessage("An Admin has joined the game!");
			} else if (player.hasPermission("yupball.staff.mod")) {
				player.sendMessage(ChatColor.GOLD + "Welcome back "
						+ ChatColor.GREEN + player.getName());
				Bukkit.broadcastMessage("A Moderator has joined the game!");
			} else {
				player.sendMessage(ChatColor.GOLD + "Welcome back "
						+ ChatColor.GREEN + player.getName());
			}
		} else {
			player.sendMessage(ChatColor.GOLD + "Welcome " + ChatColor.GREEN
					+ player.getName());
			Bukkit.broadcastMessage("Welcome " + ChatColor.GREEN
					+ player.getName() + ChatColor.RESET + " to the server!");
		}
	}

	/*
	 * public void onPlayerNameTag(PlayerReceiveNameTagEvent event) { Player
	 * player = event.getPlayer(); if (blueTeam.contains(player.getName())) {
	 * event.setTag(ChatColor.BLUE + player.getName()); } if
	 * (redTeam.contains(player.getName())) { event.setTag(ChatColor.RED +
	 * player.getName()); } }
	 */
}
