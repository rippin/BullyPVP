package me.bullyscraft.com.cmds;

import me.bullyscraft.com.BullyPVP;
import me.bullyscraft.com.Classes.Kit;
import me.bullyscraft.com.Classes.KitManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class KitsCommand extends CommandInterface {

	public KitsCommand(BullyPVP plugin) {
		super(plugin);
		
	}

	@Override
	public void executeCommand(CommandSender sender, Command cmd, String[] args) {
		CommandHelper helper = new CommandHelper(sender, cmd);
		
		if (args.length == 0){
			for (Kit k : KitManager.getAllKits()){
               sender.sendMessage(ChatColor.RED + "/kit " + k.getName() + ChatColor.GOLD + "  " + k.getKitDescription());
            }

		}
		else{
			helper.wrongArguments();
		}
		
	}

}
