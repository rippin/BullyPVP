package me.bullyscraft.com.cmds;



import me.bullyscraft.com.BullyPVP;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class CommandInterface {

	protected BullyPVP plugin;

	public CommandInterface(BullyPVP plugin) {
		this.plugin = plugin;

	}

	public abstract void executeCommand(CommandSender sender, Command cmd,
			String[] args);



}
