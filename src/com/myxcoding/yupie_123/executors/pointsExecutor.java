package com.myxcoding.yupie_123.executors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.myxcoding.yupie_123.YuPball;

public class pointsExecutor implements CommandExecutor {

	@SuppressWarnings("unused")
	private YuPball plugin;

	public pointsExecutor(YuPball plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		return true;
	}

}
