package me.bullyscraft.com.cmds;

import me.bullyscraft.com.BullyPVP;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.bullyscraft.com.Spawn;

public class SpawnCommand extends CommandInterface {

	public SpawnCommand(BullyPVP plugin) {
		super(plugin);

	}

	@Override
	public void executeCommand(CommandSender sender, Command cmd, String[] args) {

		CommandHelper helper = new CommandHelper(sender, cmd);

		if (args.length == 0) {
			if (sender instanceof Player) {
				if (sender.isOp()) {
					Spawn.setSpawn((Player) sender);
				} else {
					helper.noPermission();
				}
			} else {
				helper.noConsole();
			}
		}
		else{
			helper.wrongArguments();
		}

	}

}
