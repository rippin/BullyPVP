package me.bullyscraft.com.cmds;

import me.bullyscraft.com.BullyPVP;
import me.bullyscraft.com.Config;
import me.bullyscraft.com.MySQL.MySQL;
import me.bullyscraft.com.MySQL.SetupTables;
import me.bullyscraft.com.Stats.PlayerStatsObject;
import me.bullyscraft.com.Stats.PlayerStatsObjectManager;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Calendar;

public class PurgeCommand extends CommandInterface {

	public PurgeCommand(BullyPVP plugin) {
		super(plugin);
		
	}

	@Override
	public void executeCommand(final CommandSender sender, Command cmd, String[] args) {
		
		CommandHelper helper = new CommandHelper(sender, cmd);
		
		if (args.length == 0){
			sender.sendMessage(ChatColor.GOLD + "/purge [days]");
		}
        else if (args.length == 1){
            if (sender.isOp()){
                if (args[0].matches("[0-9]+")){
                    Calendar cal = Calendar.getInstance();
                    int days = Integer.parseInt(args[0]);
                    cal.add(Calendar.DAY_OF_MONTH, -days);
                    final long purgedTime = cal.getTimeInMillis();
                         int rowsDeleted = 0;
                     for (PlayerStatsObject pso : PlayerStatsObjectManager.getAllPlayerStatsObject()){

                        if (pso.getUUID() != null) {
                    OfflinePlayer offlinePlayer = plugin.getServer().getOfflinePlayer(pso.getUUID());
                   if (offlinePlayer != null){
                       if (offlinePlayer.getLastPlayed() != 0){
                            if (offlinePlayer.getLastPlayed() < purgedTime){
                                MySQL.updateSQL("DELETE FROM KitPVP WHERE UUID = '" + pso.getUUID().toString() + "' LIMIT 1;");
                                rowsDeleted++;
                            }
                       }
                       else {
                           MySQL.updateSQL("DELETE FROM KitPVP WHERE UUID = '" + pso.getUUID().toString() + "' LIMIT 1;");
                           rowsDeleted++;
                       }
                   }
               }
                 else {
                       MySQL.updateSQL("DELETE FROM KitPVP WHERE Username = '" + pso.getUsername().toString() + "' LIMIT 1;");
                       rowsDeleted++;
                        }
                     }
                    sender.sendMessage(ChatColor.GREEN + Integer.toString(rowsDeleted) + " rows have been deleted.");
                   }
                }
             else{
                sender.sendMessage("Argument must be numeric.");
            }
        }
		else{
			helper.wrongArguments();
		}
	}
  }