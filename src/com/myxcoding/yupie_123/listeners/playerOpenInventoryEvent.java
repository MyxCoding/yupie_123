package com.myxcoding.yupie_123.listeners;

import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInventoryEvent;
import org.bukkit.inventory.Inventory;

import com.myxcoding.yupie_123.YuPball;

@SuppressWarnings("deprecation")
public class playerOpenInventoryEvent implements Listener {

	private YuPball plugin;

	public playerOpenInventoryEvent(YuPball plugin) {
		this.plugin = plugin;
	}

	public void openInventory(PlayerInventoryEvent event) {
		Inventory Shop = plugin.Shop;
		if (event.getInventory() == Shop) {

		}
	}

}
