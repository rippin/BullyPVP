package me.bullyscraft.com.cmds;

import me.bullyscraft.com.BullyPVP;
import me.bullyscraft.com.Classes.Kit;
import me.bullyscraft.com.Classes.KitManager;
import me.bullyscraft.com.Classes.Wipe;
import me.bullyscraft.com.Stats.PlayerStatsObject;
import me.bullyscraft.com.Stats.PlayerStatsObjectManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KitCommand extends CommandInterface {

    public KitCommand(BullyPVP plugin) {
        super(plugin);

    }

    @Override
    public void executeCommand(CommandSender sender, Command cmd, String[] args) {

        CommandHelper helper = new CommandHelper(sender, cmd);

        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Kits Available: ");
            for (Kit k : KitManager.getAllKits()){
                sender.sendMessage(ChatColor.RED + "/kit " + k.getName() + ChatColor.GOLD + "  " + k.getKitDescription());
            }
        } else if (args.length == 1) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                PlayerStatsObject pso = PlayerStatsObjectManager.getPSO(player, plugin);

                if (args[0].equalsIgnoreCase(pso.getKitClass())) {
                    player.sendMessage(ChatColor.RED + "You already have this kit...");
                    return;
                } else {
                    for (Kit k : KitManager.getAllKits()) {
                        if (k.getName().equalsIgnoreCase(args[0])) {
                            if (k.getKitType().equalsIgnoreCase("Premium")) {
                                if (!player.hasPermission("Kit." + k.getName())){
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', k.getKitNoPerms()));
                                    return;
                                }
                            }
                            Wipe.removeKitItems(player);
                            k.giveKit(player);
                            return;
                        }

                    }
                    player.sendMessage(ChatColor.RED + "That kit does not exist.");

                }
            } else {
                helper.noConsole();
            }
        } else {
            helper.wrongArguments();
        }
    }

}
