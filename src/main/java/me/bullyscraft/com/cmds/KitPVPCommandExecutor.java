package me.bullyscraft.com.cmds;

import me.bullyscraft.com.BullyPVP;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class KitPVPCommandExecutor implements CommandExecutor {
	
	private BullyPVP plugin;
	
	public KitPVPCommandExecutor(BullyPVP plugin){
		this.plugin = plugin;
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		
		CommandHelper helper = new CommandHelper(sender, cmd);
		
		if (cmd.getName().equalsIgnoreCase("help") || cmd.getName().equalsIgnoreCase("?") ){
			
			new HelpCommand(plugin).executeCommand(sender, cmd, args);
				return true;
		  }
		
		else if (cmd.getName().equalsIgnoreCase("setspawn")){
			new SpawnCommand(plugin).executeCommand(sender, cmd, args);
			return true;
			
		}
		
		else if(cmd.getName().equalsIgnoreCase("kits")){
			new KitsCommand(plugin).executeCommand(sender, cmd, args);
			return true;
			
		}
	 
		else if (cmd.getName().equalsIgnoreCase("kit")){
			new KitCommand(plugin).executeCommand(sender, cmd, args);
			return true;
		}
		
		else if (cmd.getName().equalsIgnoreCase("refill") || cmd.getName().equalsIgnoreCase("soup")){
			new RefillCommand(plugin).executeCommand(sender, cmd, args);
			return true;
		}
		
		else if (cmd.getName().equalsIgnoreCase("powerups") || cmd.getName().equalsIgnoreCase("buffs")){
			new BuffsCommand(plugin).executeCommand(sender, cmd, args);
			return true;
		}
		
		else if (cmd.getName().equalsIgnoreCase("protect") || cmd.getName().equalsIgnoreCase("protection")){
			new ProtectCommand(plugin).executeCommand(sender, cmd, args);
			return true;
		}
		
		else if (cmd.getName().equalsIgnoreCase("sharpness") || cmd.getName().equalsIgnoreCase("sharp")){
			new SharpCommand(plugin).executeCommand(sender, cmd, args);
			return true;
		}
		
		else if (cmd.getName().equalsIgnoreCase("power") || cmd.getName().equalsIgnoreCase("pow")){
			new PowerCommand(plugin).executeCommand(sender, cmd, args);
			return true;
		}
		
		else if (cmd.getName().equalsIgnoreCase("knockback")){
			new KnockbackCommand(plugin).executeCommand(sender, cmd, args);
			return true;
		}
		
		else if (cmd.getName().equalsIgnoreCase("punch")){
			new PunchCommand(plugin).executeCommand(sender, cmd, args);
			return true;
		}
		
		else if (cmd.getName().equalsIgnoreCase("repair") || cmd.getName().equalsIgnoreCase("fix") ){
			new RepairCommand(plugin).executeCommand(sender, cmd, args);
			return true;
		}
		else if (cmd.getName().equalsIgnoreCase("add")){
			new AddCommand(plugin).executeCommand(sender, cmd, args);
			return true;
		}
		
		else if (cmd.getName().equalsIgnoreCase("speed")){
			new SpeedCommand(plugin).executeCommand(sender, cmd, args);
			return true;
		}
		else if (cmd.getName().equalsIgnoreCase("strength")){
			new StrengthCommand(plugin).executeCommand(sender, cmd, args);
			return true;
		}
		
		else if (cmd.getName().equalsIgnoreCase("stats")){
			new StatsCommand(plugin).executeCommand(sender, cmd, args);
			return true;
		}

        else if (cmd.getName().equalsIgnoreCase("top")){
            new TopCommand(plugin).executeCommand(sender, cmd, args);
            return true;
        }
        else if (cmd.getName().equalsIgnoreCase("reset")){
            if (sender.hasPermission("Kit.Reset")) {
            new ResetStatsCommand(plugin).executeCommand(sender, cmd, args);
            }
            else {
                helper.noPermission();
            }
            return true;
        }

		else{
			helper.unknownCommand();
		}
	
		return false;
	}

}
