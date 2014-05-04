package me.bullyscraft.com.cmds;

import me.bullyscraft.com.BullyPVP;
import me.bullyscraft.com.Stats.PlayerStatsObject;
import me.bullyscraft.com.Stats.PlayerStatsObjectManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddCommand extends CommandInterface {

	public AddCommand(BullyPVP plugin) {
		super(plugin);

	}

	@Override
	public void executeCommand(CommandSender sender, Command cmd, String[] args) {
		CommandHelper helper = new CommandHelper(sender, cmd);
		if (args.length == 2) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (player.isOp()) {
					Player p = Bukkit.getPlayerExact(args[0]);

					if (p != null) {
                        PlayerStatsObject pso = PlayerStatsObjectManager.getPSO(p, plugin);
                        int amount = Integer.parseInt(args[1]);
						pso.addCoins(amount);
					} else {
						player.sendMessage(ChatColor.RED
								+ "User file not found, spelling error? Or is player not online?");
					}
				} else {
					helper.noPermission();
				}
			} else {
				Player p = Bukkit.getPlayerExact(args[0]);
				if (p != null) {
                    PlayerStatsObject pso = PlayerStatsObjectManager.getPSO(p, plugin);
                    int amount = Integer.parseInt(args[1]);
                    pso.addCoins(amount);
				} else {
					sender.sendMessage(ChatColor.RED
							+ "User file not found, spelling error? Or is player not online?");
				}

			}
		} else {
			helper.wrongArguments();
		}

	}
}
