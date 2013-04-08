package com.myxcoding.yupie_123.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.myxcoding.yupie_123.YuPball;

public class snowballDamage implements Listener {

	@SuppressWarnings("unused")
	private YuPball plugin;

	public snowballDamage(YuPball plugin) {
		this.plugin = plugin;
	}

	public void onSnowballHit(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Snowball) {
			Snowball snowball = (Snowball) event.getDamager();
			if (event.getEntity() instanceof Player) {
				Player damaged = (Player) event.getEntity();
				if (snowball.getShooter() instanceof Player) {
					Player shooter = (Player) snowball.getShooter();
					damaged.setHealth(0);
					shooter.sendMessage("You killed " + ChatColor.RED + damaged
							+ ChatColor.RESET + "!");
				}
			}
		}
	}

}
