package com.myxcoding.yupie_123.listeners;

import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class playerDeathEvents implements Listener {

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		player.getLocation().getWorld()
				.playEffect(player.getLocation(), Effect.SMOKE, 1);
	}
}
