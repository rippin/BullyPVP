package me.bullyscraft.com.cmds;

import me.bullyscraft.com.BullyPVP;
import me.bullyscraft.com.Stats.ViewStats;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ResetStatsCommand extends CommandInterface {

    public ResetStatsCommand(BullyPVP plugin){
        super(plugin);

    }


    @Override
    public void executeCommand(CommandSender sender, Command cmd, String[] args) {
        CommandHelper helper = new CommandHelper(sender, cmd);
        if (args.length == 0){
            sender.sendMessage(ChatColor.GRAY + "To reset your stats do /reset confirm."
                    + ChatColor.DARK_RED + " WILL NOT RESET COINS.");
        }
        else if (args.length == 1){
            if (args[0].equalsIgnoreCase("confirm")){
                ViewStats.resetStats((Player)sender, plugin);
            }
            else
            helper.wrongArguments();
        }
        else
        helper.unknownCommand();
    }
}
