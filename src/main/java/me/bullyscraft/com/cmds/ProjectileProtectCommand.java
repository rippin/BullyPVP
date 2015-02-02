package me.bullyscraft.com.cmds;

import me.bullyscraft.com.BullyPVP;
import me.bullyscraft.com.Classes.Buffs;
import me.bullyscraft.com.Classes.Kit;
import me.bullyscraft.com.Classes.KitManager;
import me.bullyscraft.com.Config;
import me.bullyscraft.com.MethodLibs;
import me.bullyscraft.com.Stats.GetBuffs;
import me.bullyscraft.com.Stats.PlayerStatsObject;
import me.bullyscraft.com.Stats.PlayerStatsObjectManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ProjectileProtectCommand extends CommandInterface {

    public ProjectileProtectCommand(BullyPVP plugin) {
        super(plugin);

    }

    @Override
    public void executeCommand(CommandSender sender, Command cmd, String[] args) {

        CommandHelper helper = new CommandHelper(sender, cmd);

        if (args.length == 0) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                PlayerStatsObject pso = PlayerStatsObjectManager.getPSO(player, plugin);
                String currentKit = pso.getKitClass();
                Kit k = KitManager.getKit(currentKit);
                String buff = "ProjectileProtection";
                if (k == null){
                    player.sendMessage(ChatColor.RED + "It seems this kit was removed so no more buffs :(.");
                    return;
                }
                if (k.getBuffsandPrice().containsKey(buff)) {

                    int s = pso.getCoins();
                    int i = GetBuffs.getBuffs(player, k, buff);
                    int price = MethodLibs.calculatePriceBuffEnchant(i, k, buff);
                    if (s >= price) {

                        if (i >= Config.getConfig().getInt("Kits." + currentKit + ".Buffs." + buff + ".Max")) {
                            player.sendMessage(ChatColor.RED
                                    + "You may not increase level on this weapon.");
                        } else {
                            pso.removeCoins(price);
                            Buffs.giveBuff(player, k, buff);
                        }
                    } else {
                        player.sendMessage(ChatColor.DARK_RED
                                + "You do not have enough coins to purchase this.");
                    }
                } else {
                    player.sendMessage(ChatColor.DARK_RED
                            + "You can not use this for your class. Do /powerups for your classes powerups.");
                }

            } else {
                helper.noConsole();
            }
        } else {
            helper.wrongArguments();
        }
    }
}
