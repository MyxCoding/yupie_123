package com.myxcoding.yupie_123;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.kitteh.tag.PlayerReceiveNameTagEvent;
import org.kitteh.tag.TagAPI;

import com.myxcoding.yupie_123.util.ListStore;

public class YuPball extends JavaPlugin implements Listener {
	public static final Logger log = Logger.getLogger("Minecraft");
	public int redTeamAmount;
	public int blueTeamAmount;
	public ListStore warnedOnce;
	public ListStore warnedTwice;
	public ListStore kickedOnce;
	public ListStore kickedTwice;
	public ListStore bannedPlayers;
	public static HashMap<String, Integer> points = new HashMap<String, Integer>();
	public static HashMap<String, Integer> kills = new HashMap<String, Integer>();
	public static HashMap<String, Integer> deaths = new HashMap<String, Integer>();
	public static HashMap<String, Integer> goals = new HashMap<String, Integer>();
	public static ArrayList<String> staff = new ArrayList<String>();
	public static ArrayList<String> admins = new ArrayList<String>();
	public static ArrayList<String> mods = new ArrayList<String>();
	public static ArrayList<String> redTeam = new ArrayList<String>();
	public static ArrayList<String> blueTeam = new ArrayList<String>();
	public boolean inGame = false;
	public boolean inLobby = false;
	public static Inventory Shop;

	private String message = ChatColor.GRAY + "[" + ChatColor.AQUA + "YuPball"
			+ ChatColor.GRAY + "]";

	@Override
	public void onEnable() {
		redTeamAmount = 0;
		blueTeamAmount = 0;
		saveConfig();
		getServer().getPluginManager().registerEvents(this, this);
		PluginDescriptionFile pdfFile = this.getDescription();
		log.info("[" + pdfFile.getName() + "] V" + pdfFile.getVersion()
				+ " Has Been Enabled!");
		try {
			saveConfig();
			setupConfig(getConfig());
			getConfig().options().copyDefaults(true);
			saveConfig();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Shop = Bukkit.createInventory(null, 9, "Shop");
		this.getCommand("warn").setExecutor(new warnExecutor(this));
	}

	private void setupConfig(FileConfiguration config) throws IOException {
		if (!new File(getDataFolder(), "RESET.FILE").exists()) {
			config.set("Data.Stats.ExamplePlayer.Points", "5000");
			new File(getDataFolder(), "RESET.FILE").createNewFile();
		}
	}

	@Override
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		log.info("[" + pdfFile.getName() + "]" + " Has Been Disabled!");
		saveConfig();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		// Information Command
		if (label.equalsIgnoreCase("yb")) {
			if (args.length == 0) {
				if (sender instanceof Player) {
					Player player = (Player) sender;
					PluginDescriptionFile pdfFile = this.getDescription();
					player.sendMessage(ChatColor.AQUA
							+ "YuPball was created by: " + ChatColor.GOLD
							+ pdfFile.getAuthors());
					player.sendMessage(ChatColor.AQUA
							+ "YuPball was designed by: " + ChatColor.GOLD
							+ "Yupie_123 (Owner of YuPcraft)");
				}
			}
			// Staff Command
		} else if (label.equalsIgnoreCase("staff")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				player.sendMessage(ChatColor.AQUA + "Staff Online:");
				player.sendMessage(ChatColor.YELLOW + "Mods: " + ChatColor.GRAY
						+ mods);
				player.sendMessage(ChatColor.GREEN + "Admins: "
						+ ChatColor.GRAY + admins);
			}
			// Points Command
		} else if (label.equalsIgnoreCase("points")) {
			if (args.length == 0) {
				if (sender instanceof Player) {
					Player player = (Player) sender;
					if (this.getConfig().contains(player.getName())) {
						String playerName = player.getName();
						int amountPoints = this.getConfig().getInt(
								"Data.Stats." + playerName + ".Points");
						player.sendMessage(message + ChatColor.GOLD
								+ "You have " + amountPoints + " points");
					} else {
						String playerName = player.getName();
						this.getConfig().set(
								"Data.Stats." + playerName + ".Points", 0);
						int amountPoints = this.getConfig().getInt(
								"Data.Stats." + playerName + ".Points");
						player.sendMessage(message + ChatColor.GOLD
								+ "You have " + amountPoints + " points");
						saveConfig();
					}
				}
			} else if (args.length == 1) {
				String playerName = args[1];
				int amountPoints = this.getConfig().getInt(
						"Data.Stats." + playerName + ".Points");
				sender.sendMessage(message + ChatColor.GOLD + args[0]
						+ " currently has " + amountPoints + " points");
			} else if (args.length > 1) {
				sender.sendMessage(ChatColor.RED + "Usage: /points [Player]");
			} else if (args.length == 2
					&& sender.hasPermission("yupball.points.edit")) {
				if (args[0].equalsIgnoreCase("give")) {
					if (sender instanceof Player) {
						try {
							Player player = (Player) sender;
							String playerName = player.getName();
							int amountPoints = this.getConfig().getInt(
									"Data.Stats." + playerName + ".Points");
							NumberFormat format = NumberFormat
									.getInstance(Locale.US);
							String num = args[1];

							int number = (Integer) format.parse(num);
							int Final = amountPoints + number;
							this.getConfig().set(
									"Data.Stats." + playerName + ".Points",
									Final);
							sender.sendMessage(message + ChatColor.GOLD
									+ "You added " + ChatColor.GREEN + args[1]
									+ ChatColor.GOLD + " to your account");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} else if (args[0].equalsIgnoreCase("remove")) {
					if (sender instanceof Player) {
						try {
							Player player = (Player) sender;
							String playerName = player.getName();
							int amountPoints = this.getConfig().getInt(
									"Data.Stats." + playerName + ".Points");
							NumberFormat format = NumberFormat
									.getInstance(Locale.US);
							String num = args[1];

							int number = (Integer) format.parse(num);
							int Final = amountPoints - number;
							this.getConfig().set(
									"Data.Stats." + playerName + ".Points",
									Final);
							sender.sendMessage(message + ChatColor.GOLD
									+ "You removed " + ChatColor.GREEN
									+ args[1] + ChatColor.GOLD
									+ " from your account");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			} else if (args.length == 3
					&& sender.hasPermission("yupball.points.edit")) {
				if (args[0].equalsIgnoreCase("give")) {
					if (sender instanceof Player) {
						Player targetPlayer = sender.getServer().getPlayer(
								args[0]);
						String targetPlayerName = targetPlayer.getName();
						int amountPoints = this.getConfig().getInt(
								"Data.Stats." + targetPlayerName + ".Points");
						NumberFormat format = NumberFormat
								.getInstance(Locale.US);
						String num = args[1];
						try {
							int number = (Integer) format.parse(num);
							int Final = amountPoints + number;
							this.getConfig().set(
									"Data.Stats." + targetPlayerName
											+ ".Points", Final);
						} catch (ParseException e) {
							e.printStackTrace();
						}

						sender.sendMessage(message + ChatColor.GOLD
								+ "You added " + ChatColor.GREEN + args[1]
								+ ChatColor.GOLD + " to " + targetPlayerName
								+ "'s account");
					}
				} else if (args[0].equalsIgnoreCase("remove")) {
					if (sender instanceof Player) {
						Player targetPlayerName = sender.getServer().getPlayer(
								args[0]);
						int amountPoints = this.getConfig().getInt(
								"Data.Stats." + targetPlayerName + ".Points");
						NumberFormat format = NumberFormat
								.getInstance(Locale.US);
						String num = args[1];
						try {
							int number = (Integer) format.parse(num);
							int Final = amountPoints - number;
							this.getConfig().set(
									"Data.Stats." + targetPlayerName
											+ ".Points", Final);
						} catch (ParseException e) {
							e.printStackTrace();
						}

						sender.sendMessage(message + ChatColor.GOLD
								+ "You removed " + ChatColor.GREEN + args[1]
								+ ChatColor.GOLD + " from " + targetPlayerName
								+ "'s account");
					}
				}
			}
			// Shop Open Command
		} else if (label.equalsIgnoreCase("shop")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				ItemStack Iron = new ItemStack(Material.IRON_INGOT, 1);
				ItemStack Gold = new ItemStack(Material.GOLD_INGOT, 1);
				ItemStack Diamond = new ItemStack(Material.DIAMOND, 1);
				player.openInventory(Shop);
				Shop.clear();
				Shop.setItem(0, Iron);
				Shop.setItem(1, Gold);
				Shop.setItem(2, Diamond);
			}
			// Join
		} else if (label.equalsIgnoreCase("join")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (redTeamAmount < blueTeamAmount) {
					redTeamAmount++;
					redTeam.add(player.getName());
					player.setDisplayName(ChatColor.DARK_RED + player.getName()
							+ ChatColor.RESET);
					player.sendMessage(message + ChatColor.GOLD
							+ "You have been Placed in the "
							+ ChatColor.DARK_RED + "Red " + ChatColor.GOLD
							+ "Team");
					player.setCustomName(ChatColor.RED + player.getName());
					TagAPI.refreshPlayer(player);
				} else if (blueTeamAmount < redTeamAmount) {
					blueTeamAmount++;
					blueTeam.add(player.getName());
					player.setDisplayName(ChatColor.BLUE + player.getName()
							+ ChatColor.RESET);
					player.sendMessage(message + ChatColor.GOLD
							+ "You have been Placed in the " + ChatColor.BLUE
							+ "Blue " + ChatColor.GOLD + "Team");
					player.setCustomName(ChatColor.BLUE + player.getName());
					TagAPI.refreshPlayer(player);
				} else if (redTeamAmount == blueTeamAmount) {
					redTeamAmount++;
					redTeam.add(player.getName());
					player.setDisplayName(ChatColor.DARK_RED + player.getName()
							+ ChatColor.RESET);
					player.sendMessage(message + ChatColor.GOLD
							+ "You have been Placed in the "
							+ ChatColor.DARK_RED + "Red " + ChatColor.GOLD
							+ "Team");
					player.setCustomName(ChatColor.DARK_RED + player.getName());
					TagAPI.refreshPlayer(player);
				}
			} else {
				sender.sendMessage(ChatColor.RED
						+ "You need to be ingame to perform this command!");
			}
			// Leave
		} else if (label.equalsIgnoreCase("leave")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (redTeam.contains(player.getName())) {
					redTeam.remove(player.getName());
					redTeamAmount--;
					player.sendMessage(message + ChatColor.GOLD
							+ "You have been removed from the "
							+ ChatColor.DARK_RED + "Red" + ChatColor.GOLD
							+ " team!");
					player.setCustomName(player.getName());
					TagAPI.refreshPlayer(player);
				} else if (blueTeam.contains(player.getName())) {
					blueTeam.remove(player.getName());
					blueTeamAmount--;
					player.sendMessage(message + ChatColor.GOLD
							+ "You have been removed from the "
							+ ChatColor.BLUE + "Blue" + ChatColor.GOLD
							+ " team!");
					player.setCustomName(player.getName());
				} else {
					player.sendMessage(message + ChatColor.RED
							+ "You are not in a team!");
				}
			} else {
				sender.sendMessage(message + ChatColor.RED
						+ "You need to be ingame to perform this command!");
			}
			// Stats
		} else if (label.equalsIgnoreCase("stats")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (args.length == 0) {
					int playerKills = this.getConfig().getInt(
							"Data.Stats." + player.getName() + ".Kills");
					int playerDeaths = this.getConfig().getInt(
							"Data.Stats." + player.getName() + ".Deaths");
					int playerGoals = this.getConfig().getInt(
							"Data.Stats." + player.getName() + ".Goals");
					player.sendMessage(ChatColor.GOLD + "----------"
							+ ChatColor.AQUA + "Stats:" + ChatColor.GOLD
							+ "----------");
					player.sendMessage(ChatColor.GREEN + "Kills: "
							+ ChatColor.GOLD + playerKills);
					player.sendMessage(ChatColor.RED + "Deaths: "
							+ ChatColor.GOLD + playerDeaths);
					player.sendMessage(ChatColor.BLUE + "Goals: "
							+ ChatColor.GOLD + playerGoals);
					player.sendMessage(ChatColor.AQUA + "Date Last Seen: "
							+ ChatColor.GOLD + player.getLastPlayed());
				} else if (args.length == 1) {
					Player targetPlayer = sender.getServer().getPlayer(args[0]);
					int playerKills = this.getConfig().getInt(
							"Data.Stats." + targetPlayer.getName() + ".Kills");
					int playerDeaths = this.getConfig().getInt(
							"Data.Stats." + targetPlayer.getName() + ".Deaths");
					int playerGoals = this.getConfig().getInt(
							"Data.Stats." + targetPlayer.getName() + ".Goals");
					player.sendMessage(ChatColor.GOLD + "----------"
							+ ChatColor.AQUA + "Stats:" + ChatColor.GOLD
							+ "----------");
					player.sendMessage(ChatColor.GREEN + "Kills: "
							+ ChatColor.GOLD + playerKills);
					player.sendMessage(ChatColor.RED + "Deaths: "
							+ ChatColor.GOLD + playerDeaths);
					player.sendMessage(ChatColor.BLUE + "Goals: "
							+ ChatColor.GOLD + playerGoals);
					player.sendMessage(ChatColor.AQUA + "Date Last Seen:"
							+ ChatColor.GOLD + player.getLastPlayed());
				}
			}
		} else if (label.equalsIgnoreCase("set")) {
			if (args.length == 0) {
				sender.sendMessage(message + ChatColor.DARK_RED
						+ "Usage: /set <spectate/lobby>");
			} else if (args.length == 1) {
				if (args[0].equalsIgnoreCase("spectate")) {
					if (sender instanceof Player) {
						Player player = (Player) sender;
						int SpecX = player.getLocation().getBlockX();
						int SpecY = player.getLocation().getBlockY();
						int SpecZ = player.getLocation().getBlockZ();
						this.getConfig()
								.set("Arena.Spectate.Location.X", SpecX);
						this.getConfig()
								.set("Arena.Spectate.Location.Y", SpecY);
						this.getConfig()
								.set("Arena.Spectate.Location.Z", SpecZ);
						player.sendMessage(message + ChatColor.GOLD
								+ "You set the Spectator location!");
						saveConfig();
					} else {
						sender.sendMessage(message
								+ ChatColor.DARK_RED
								+ "You need to be an ingame player to perform this command!");
					}
				} else if (args[0].equalsIgnoreCase("lobby")) {
					if (sender instanceof Player) {
						Player player = (Player) sender;
						int LobbyX = player.getLocation().getBlockX();
						int LobbyY = player.getLocation().getBlockY();
						int LobbyZ = player.getLocation().getBlockZ();
						this.getConfig().set("Arena.Lobby.Location.X", LobbyX);
						this.getConfig().set("Arena.Lobby.Location.Y", LobbyY);
						this.getConfig().set("Arena.Lobby.Location.Z", LobbyZ);
						player.sendMessage(message + ChatColor.GOLD
								+ "You set the Lobby location!");
						saveConfig();
					} else {
						sender.sendMessage(message
								+ ChatColor.DARK_RED
								+ "You need to be an ingame player to perform this command!");
					}
				}
			}

		}
		return false;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {

		String name = event.getPlayer().getName();
		Player player = event.getPlayer();
		TagAPI.refreshPlayer(player);
		if (player.hasPlayedBefore() == true) {
			if (player.hasPermission("yupball.staff.admin")
					|| player.isOp() == true) {
				player.sendMessage(ChatColor.GOLD + "Welcome back "
						+ ChatColor.GREEN + player.getName());
				staff.add(name);
				admins.add(name);
				Bukkit.broadcastMessage(ChatColor.GOLD
						+ "An Admin has joined the game!");
				TagAPI.refreshPlayer(player);
			} else if (player.hasPermission("yupball.staff.mod")) {
				player.sendMessage(ChatColor.GOLD + "Welcome back "
						+ ChatColor.GREEN + player.getName());
				staff.add(name);
				mods.add(name);
				Bukkit.broadcastMessage(ChatColor.GOLD
						+ "A Mod has joined the game!");
				TagAPI.refreshPlayer(player);
			} else if (player.getName() == "yupie_123") {
				player.sendMessage(ChatColor.GOLD + "Welcome back "
						+ ChatColor.GREEN + player.getName());
				staff.add(name);
				Bukkit.broadcastMessage(ChatColor.GOLD
						+ "The Server owner has joined the game!");
				TagAPI.refreshPlayer(player);
			} else {
				player.sendMessage(ChatColor.GOLD + "Welcome back "
						+ ChatColor.GREEN + player.getName());
				TagAPI.refreshPlayer(player);
			}
		} else {
			player.sendMessage(ChatColor.GOLD + "Welcome to the server "
					+ ChatColor.GREEN + player.getName());
			Bukkit.broadcastMessage(ChatColor.GOLD + "Welcome "
					+ ChatColor.GREEN + player.getName() + ChatColor.GOLD
					+ " to the server!");
			this.getConfig().set("Data.Stats." + player.getName() + ".Points",
					0);
			this.getConfig()
					.set("Data.Stats." + player.getName() + ".Kills", 0);
			this.getConfig().set("Data.Stats." + player.getName() + ".Deaths",
					0);
			this.getConfig()
					.set("Data.Stats." + player.getName() + ".Goals", 0);
			this.getConfig().set(
					"Data.Stats." + player.getName() + ".DeathsThisRound", 0);
			int amountPoints = this.getConfig().getInt(
					"Data.Stats." + player.getName() + ".Points");
			player.sendMessage(message + ChatColor.GOLD + "You currently have "
					+ amountPoints + " points");
			TagAPI.refreshPlayer(player);
			saveConfig();
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getInventory() == Shop) {
			Player player = (Player) event.getWhoClicked();
			if (event.getCurrentItem().getType() == Material.IRON_INGOT) {
				event.setCancelled(true);
				if (points.get(player.getName()) == 50) {
					player.getInventory().addItem(
							new ItemStack(Material.SNOW_BALL, 10));
				}
			} else if (event.getCurrentItem().getType() == Material.GOLD_INGOT) {
				event.setCancelled(true);
				if (points.get(player.getName()) == 200) {
					player.getInventory().addItem(
							new ItemStack(Material.SNOW_BALL, 50));
				}
			} else if (event.getCurrentItem().getType() == Material.DIAMOND) {
				event.setCancelled(true);
				if (points.get(player.getName()) == 300) {
					player.getInventory().addItem(
							new ItemStack(Material.SNOW_BALL, 100));
				}
			}
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if (admins.contains(player.getName())) {
			Bukkit.broadcastMessage(ChatColor.GOLD
					+ "An admin has left the game!");
			admins.remove(player.getName());
		} else if (mods.contains(player.getName())) {
			mods.remove(player.getName());
		}
	}

	@EventHandler
	public void onPlayerKill(PlayerDeathEvent event) {
		Player player = event.getEntity().getPlayer();
		Player killer = event.getEntity().getKiller();
		player.getLocation().getWorld()
				.playEffect(player.getLocation(), Effect.SMOKE, 1);
		int deaths = (this.getConfig().getInt("Data.Stats." + player.getName()
				+ ".DeathsThisRound")) + 1;
		this.getConfig().set(
				"Data.Stats." + player.getName() + ".DeathsThisRound", deaths);
		int kills = (this.getConfig().getInt("Data.Stats." + killer.getName()
				+ ".Kills")) + 1;
		this.getConfig()
				.set("Data.Stats." + killer.getName() + ".Kills", kills);
		if (this.getConfig().getInt(
				"Data.Stats." + player.getName() + ".DeathsThisRound") == 1) {
			player.sendMessage(message + ChatColor.DARK_RED + "You have "
					+ ChatColor.GREEN + "2" + ChatColor.DARK_RED
					+ " lives left!");
			event.setDeathMessage(ChatColor.BLUE + player.getName()
					+ ChatColor.RED + " was hit by " + ChatColor.GREEN
					+ killer.getName());
		} else if (this.getConfig().getInt(
				"Data.Stats." + player.getName() + ".DeathsThisRound") == 2) {
			player.sendMessage(message + ChatColor.DARK_RED + "You have "
					+ ChatColor.GREEN + "1" + ChatColor.DARK_RED
					+ " life left!");
			event.setDeathMessage(ChatColor.BLUE + player.getName()
					+ ChatColor.RED + " was hit by " + ChatColor.GREEN
					+ killer.getName());
		} else if (this.getConfig().getInt(
				"Data.Stats." + player.getName() + ".DeathsThisRound") == 3) {
			player.sendMessage(message + ChatColor.DARK_RED + "You have "
					+ ChatColor.GREEN + "0" + ChatColor.DARK_RED
					+ " lives left!");
			event.setDeathMessage(ChatColor.BLUE + player.getName()
					+ ChatColor.RED + " was killed by " + ChatColor.GREEN
					+ killer.getName());
		}
	}

	@EventHandler
	public void onPlayerTag(PlayerReceiveNameTagEvent event) {
		Player player = event.getNamedPlayer();
		if (redTeam.contains(player.getName())) {
			event.setTag(ChatColor.DARK_RED + player.getName());
		} else if (blueTeam.contains(player.getName())) {
			event.setTag(ChatColor.BLUE + player.getName());
		} else if (player.hasPermission("yupball.staff")) {
			event.setTag(ChatColor.GOLD + player.getName());
		} else if (player.getName().equalsIgnoreCase("yupie_123")) {
			event.setTag(ChatColor.DARK_PURPLE + player.getName());
		} else {
			event.setTag(ChatColor.GREEN + player.getName());
		}
	}

	@SuppressWarnings("null")
	@EventHandler
	public void playerRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		if (this.getConfig().getInt(
				"Data.Stats." + player.getName() + ".DeathsThisRound") == 1
				|| this.getConfig().getInt(
						"Data.Stats." + player.getName() + ".DeathsThisRound") == 2) {
			int SpecX = this.getConfig().getInt("Arena.Spectate.Location.X");
			int SpecY = this.getConfig().getInt("Arena.Spectate.Location.Y");
			int SpecZ = this.getConfig().getInt("Arena.Spectate.Location.Z");
			Location Spectate = null;
			Spectate.setX(SpecX);
			Spectate.setY(SpecY);
			Spectate.setZ(SpecZ);
			player.teleport(Spectate);
			player.sendMessage(message + ChatColor.GOLD
					+ "You were teleported to the spectator area!");
		} else if (this.getConfig().getInt(
				"Data.Stats." + player.getName() + ".DeathsThisRound") == 3) {
			int LobbyX = this.getConfig().getInt("Arena.Lobby.Location.X");
			int LobbyY = this.getConfig().getInt("Arena.Lobby.Location.Y");
			int LobbyZ = this.getConfig().getInt("Arena.Lobby.Location.Z");
			Location Lobby = null;
			Lobby.setX(LobbyX);
			Lobby.setY(LobbyY);
			Lobby.setZ(LobbyZ);
			player.teleport(Lobby);
			player.sendMessage(message + ChatColor.GOLD
					+ "You were teleported to the Lobby area!");
			this.getConfig().set(
					"Data.Stats." + player.getName() + ".DeathsThisRound", 0);
		}
	}

	@EventHandler
	public void onSnowballHit(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Snowball) {
			Snowball snowball = (Snowball) event.getDamager();
			if (event.getEntity() instanceof Player) {
				Player damaged = (Player) event.getEntity();
				if (snowball.getShooter() instanceof Player) {
					Player shooter = (Player) snowball.getShooter();
					event.setDamage(20);
					shooter.sendMessage(message + "You killed " + ChatColor.RED
							+ damaged + ChatColor.RESET + "!");
				}
			}
		} else if (event.getDamager() instanceof Player) {
			if (event.getEntity() instanceof Player) {
				event.setCancelled(true);
			}
		}
	}
}
