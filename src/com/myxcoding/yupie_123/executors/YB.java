package com.myxcoding.yupie_123.executors;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

import com.myxcoding.yupie_123.YuPball;

public class YB implements CommandExecutor {

	private YuPball plugin;

	public YB(YuPball plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (args.length == 0) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				PluginDescriptionFile pdfFile = plugin.getDescription();
				player.sendMessage(ChatColor.AQUA + "YuPball was created by: "
						+ ChatColor.GOLD + pdfFile.getAuthors());
				player.sendMessage(ChatColor.AQUA + "YuPball was designed by: "
						+ ChatColor.GOLD + "Yupie_123 (Owner of YuPcraft)");
			}
		} else if (args.length == 1 || args.length == 2 || args.length == 3) {
			if (args[0].equalsIgnoreCase("staff")) {
				sender.sendMessage(ChatColor.RED
						+ "Usage: /yb staff <add/remove> <Player> <Rank>");
			}
		} else if (args.length == 4) {
			if (args[0].equalsIgnoreCase("staff")
					&& args[1].equalsIgnoreCase("add")) {
				String targetPlayer = sender.getServer().getPlayer(args[2])
						.getName();
				if (args[3].equalsIgnoreCase("moderator")
						|| args[3].equalsIgnoreCase("mod")) {
					plugin.players.remove(targetPlayer);
					plugin.mods.add(targetPlayer);
				} else if (args[3].equalsIgnoreCase("administrator")
						|| args[3].equalsIgnoreCase("admin")) {
					plugin.players.remove(targetPlayer);
					plugin.admins.add(targetPlayer);
				}
			} else if (args[0].equalsIgnoreCase("staff")
					&& args[1].equalsIgnoreCase("remove")) {
				String targetPlayer = sender.getServer().getPlayer(args[2])
						.getName();
				if (args[3].equalsIgnoreCase("moderator")
						|| args[3].equalsIgnoreCase("mod")) {
					plugin.mods.remove(targetPlayer);
					plugin.players.add(targetPlayer);
				} else if (args[3].equalsIgnoreCase("administrator")
						|| args[3].equalsIgnoreCase("admin")) {
					plugin.admins.remove(targetPlayer);
					plugin.players.add(targetPlayer);
				}
			}
		}
		return false;
	}

}
