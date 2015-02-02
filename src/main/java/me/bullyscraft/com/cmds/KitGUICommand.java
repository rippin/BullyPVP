package me.bullyscraft.com.cmds;

import me.bullyscraft.com.BullyPVP;
import me.bullyscraft.com.Classes.Kit;
import me.bullyscraft.com.Classes.KitGUI;
import me.bullyscraft.com.Classes.KitManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KitGUICommand extends CommandInterface {

	public KitGUICommand(BullyPVP plugin) {
		super(plugin);
		
	}

	@Override
	public void executeCommand(CommandSender sender, Command cmd, String[] args) {
		CommandHelper helper = new CommandHelper(sender, cmd);
		
		if (args.length == 0){
           if (sender instanceof Player){
               Player p = (Player) sender;
               p.openInventory(KitGUI.getGuiList().get(0));
           }
		}
		else{
			helper.wrongArguments();
		}
		
	}

}
