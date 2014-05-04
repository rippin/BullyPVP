package me.bullyscraft.com.cmds;

import me.bullyscraft.com.BullyPVP;
import me.bullyscraft.com.Classes.Buffs;
import me.bullyscraft.com.Classes.Kit;
import me.bullyscraft.com.Classes.KitManager;
import me.bullyscraft.com.Stats.PlayerStatsObject;
import me.bullyscraft.com.Stats.PlayerStatsObjectManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RefillCommand extends CommandInterface {

	public RefillCommand(BullyPVP plugin) {
		super(plugin);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void executeCommand(CommandSender sender, Command cmd, String[] args) {
		
		CommandHelper helper = new CommandHelper(sender,cmd);

        if (args.length == 0) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                PlayerStatsObject pso = PlayerStatsObjectManager.getPSO(player, plugin);
                String currentKit = pso.getKitClass();
                Kit k = KitManager.getKit(currentKit);
                String buff = "Refill";
                if (k.getBuffsandPrice().containsKey(buff)) {

                    int s = pso.getCoins();
                    int price = k.getBuffsandPrice().get(buff);

                    if (s >= price) {
                            pso.removeCoins(price);
                            Buffs.giveBuff(player, k, buff);

                    } else {
                        player.sendMessage(ChatColor.DARK_RED
                                + "You do not have enough coins to purchase this.");
                    }
                } else {
                    player.sendMessage(ChatColor.DARK_RED
                            + "You can not use this for your class. Do /powerups for your classes powerups.");
                }

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
