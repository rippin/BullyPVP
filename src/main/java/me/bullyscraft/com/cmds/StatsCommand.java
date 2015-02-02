package me.bullyscraft.com.cmds;

import me.bullyscraft.com.BullyPVP;
import me.bullyscraft.com.Stats.PlayerStatsObject;
import me.bullyscraft.com.Stats.PlayerStatsObjectManager;
import me.bullyscraft.com.Stats.ViewStats;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatsCommand extends CommandInterface {

	public StatsCommand(BullyPVP plugin) {
		super(plugin);

	}

	@Override
	public void executeCommand(CommandSender sender, Command cmd, String[] args) {
		CommandHelper helper = new CommandHelper(sender, cmd);

		if (sender instanceof Player) {
			if (args.length == 0) {
                PlayerStatsObject pso = PlayerStatsObjectManager.getPSO((Player) sender, plugin);
				ViewStats.viewStats( sender, pso);

			} else if (args.length == 1) {
                PlayerStatsObject pso = PlayerStatsObjectManager.getPSO(args[0], plugin);
                if (pso != null) {

					ViewStats.viewStats( sender, pso);
				} else {
					sender.sendMessage(ChatColor.RED
							+ "Could not find player, spelling mistake?");
				}
            }
              else {
				helper.wrongArguments();
			}
		} else {
			helper.noConsole();
		}
	}
}
