package com.myxcoding.yupie_123.executors;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.myxcoding.yupie_123.YuPball;

public class Shop implements CommandExecutor {

	private YuPball plugin;

	public Shop(YuPball plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			Inventory Shop = plugin.Shop;
			Shop.addItem(new ItemStack(Material.APPLE, 1));
			Shop.addItem(new ItemStack(Material.ANVIL, 1));
			player.openInventory(Shop);
		}
		return false;
	}
}
