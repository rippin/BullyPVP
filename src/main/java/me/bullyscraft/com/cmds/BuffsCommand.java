package me.bullyscraft.com.cmds;

import me.bullyscraft.com.BullyPVP;
import me.bullyscraft.com.Classes.Kit;
import me.bullyscraft.com.Classes.KitManager;
import me.bullyscraft.com.Stats.PlayerStatsObject;
import me.bullyscraft.com.Stats.PlayerStatsObjectManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BuffsCommand extends CommandInterface {

	
	public BuffsCommand(BullyPVP plugin) {
		super(plugin);
		
	}

	@Override
	public void executeCommand(CommandSender sender, Command cmd, String[] args) {
		CommandHelper helper = new CommandHelper(sender,cmd);
		
		if (args.length == 0){
			if (sender instanceof Player){
				Player player = (Player) sender;
                PlayerStatsObject pso = PlayerStatsObjectManager.getPSO(player, plugin);
				String s = pso.getKitClass();
                Kit k = KitManager.getKit(s);
                KitManager.getBuffMessages(player, k);

			}
			else{
				helper.noConsole();
			}
		}
		else{
			helper.wrongArguments();
		}
		
	}

}
