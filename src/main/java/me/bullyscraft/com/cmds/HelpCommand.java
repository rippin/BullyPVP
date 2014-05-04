package me.bullyscraft.com.cmds;

import me.bullyscraft.com.BullyPVP;
import me.bullyscraft.com.Config;
import me.bullyscraft.com.MySQL.SetupTables;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class HelpCommand extends CommandInterface {

	public HelpCommand(BullyPVP plugin) {
		super(plugin);
		
	}

	@Override
	public void executeCommand(CommandSender sender, Command cmd, String[] args) {
		
		CommandHelper helper = new CommandHelper(sender, cmd);
		
		if (args.length == 0){
			sender.sendMessage(ChatColor.GOLD + "/powerups" + ChatColor.RED + " to view the powerups available.");
			sender.sendMessage(ChatColor.GOLD + "/kits" + ChatColor.RED + " to view the kits available.");
			sender.sendMessage(ChatColor.GOLD + "/kit <kit name>" + ChatColor.RED + " to obtain a kit");
            sender.sendMessage(ChatColor.GOLD + "/stats <name>" + ChatColor.RED + " show stats of the player.");
            sender.sendMessage(ChatColor.GOLD + "/top" + ChatColor.RED + " show top stats of the server.");
			//top commands here
		}
        else if (args.length == 1){
            if (sender.isOp()){
                if (args[0].equalsIgnoreCase("reload")){
                    Config.reload();
                    sender.sendMessage(ChatColor.GREEN + "Config has been reloaded");
                }
                else if (args[0].equalsIgnoreCase("transferUUID")) {
                    SetupTables.transferUUID(plugin);
                }
            }
        }
		else{
			helper.wrongArguments();
		}
	}

}
