package me.bullyscraft.com.cmds;


import me.bullyscraft.com.BullyPVP;
import me.bullyscraft.com.MySQL.SetupTables;
import me.bullyscraft.com.Stats.ViewStats;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TopCommand extends CommandInterface {

    public TopCommand(BullyPVP plugin) {
        super(plugin);

    }

    @Override
    public void executeCommand(CommandSender sender, Command cmd, String[] args) {
        CommandHelper helper = new CommandHelper(sender, cmd);

        if (sender instanceof Player) {
            if (args.length == 0) {

                sender.sendMessage(ChatColor.GOLD + "/top kills" + ChatColor.RED + " View Top 10 Kills.");
                sender.sendMessage(ChatColor.GOLD + "/top deaths" + ChatColor.RED + " View Top 10 Deaths.");
                sender.sendMessage(ChatColor.GOLD + "/top coins" + ChatColor.RED + " View Top 10 Coins.");
                sender.sendMessage(ChatColor.GOLD + "/top currentstreak" + ChatColor.RED + " View Top 10 Current Kill Streaks.");
                sender.sendMessage(ChatColor.GOLD + "/top higheststreak" + ChatColor.RED + " View Top 10 Highest Kill Streaks.");

            } else if (args.length == 1) {
               if (args[0].equalsIgnoreCase("kills")){
                   SetupTables.top10Kills(plugin, sender);
               }
                else if (args[0].equalsIgnoreCase("deaths")){
                   SetupTables.top10Deaths(plugin, sender);
               }
               else if (args[0].equalsIgnoreCase("coins")){
                   SetupTables.top10Coins(plugin, sender);
               }
               else if (args[0].equalsIgnoreCase("currentstreak")){
                   SetupTables.top10CurrentStreak(plugin, sender);
               }
               else if (args[0].equalsIgnoreCase("higheststreak")){
                   SetupTables.top10HighestStreak(plugin, sender);
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
