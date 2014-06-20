package me.bullyscraft.com;

import me.bullyscraft.com.Stats.PlayerStatsObject;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class BullyScoreBoard {

 private Player player;
 private ScoreboardManager manager;
 private Scoreboard board;
 private Objective objective;
 private Score kills;
 private Score deaths;
 private Score coins;	
 private Score highStreak;
 private Score streak;
 private Score wins1v1;
 private Score losses1v1;
 private Score currentStreak1v1;
 private Score highStreak1v1;
 private BullyPVP plugin;
 private PlayerStatsObject pso;

 public BullyScoreBoard(Player player, PlayerStatsObject pso){
	this.player = player;
	manager = Bukkit.getScoreboardManager();
	board = manager.getNewScoreboard();
 	objective = board.registerNewObjective("Stats", "dummy");
	objective.setDisplaySlot(DisplaySlot.SIDEBAR);
	objective.setDisplayName(ChatColor.AQUA + "BullyPVP Stats");
	kills = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Kills:"));
	deaths = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Deaths:"));
	coins = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Coins:"));
	streak = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Streak:"));
	highStreak = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "High Streak:"));
    plugin = BullyPVP.instance;
    if (plugin.isBully1v1Enabled()){
        Team bypass = board.registerNewTeam("bypass");
        bypass.setPrefix(ChatColor.GREEN + "1v1 ");
        wins1v1 = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "1v1 Wins:"));
        losses1v1 = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "1v1 Losses:"));
        currentStreak1v1 = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "1v1 Streak:"));
        highStreak1v1 = objective.getScore(Bukkit.getOfflinePlayer("HighStreak:"));
        bypass.addPlayer(Bukkit.getOfflinePlayer("HighStreak:"));
    }
    this.pso = pso;
	
	
}

 	public void setUp(){
	
 	coins.setScore(pso.getCoins());
	deaths.setScore(pso.getDeaths());
	kills.setScore(pso.getKills());
	streak.setScore(pso.getCurrentstreak());
	highStreak.setScore(pso.getHigheststreak());
	if (plugin.isBully1v1Enabled()){
        wins1v1.setScore(pso.getWins1v1());
        losses1v1.setScore(pso.getLosses1v1());
        highStreak1v1.setScore(pso.getHighStreak1v1());
        currentStreak1v1.setScore(pso.getCurrentStreak1v1());
    }
    player.setScoreboard(board);
	
	
 	}

	public void update(){

        coins.setScore(pso.getCoins());
        deaths.setScore(pso.getDeaths());
        kills.setScore(pso.getKills());
        streak.setScore(pso.getCurrentstreak());
        highStreak.setScore(pso.getHigheststreak());
        if (plugin.isBully1v1Enabled()){
            wins1v1.setScore(pso.getWins1v1());
            losses1v1.setScore(pso.getLosses1v1());
            highStreak1v1.setScore(pso.getHighStreak1v1());
            currentStreak1v1.setScore(pso.getCurrentStreak1v1());
        }
        player.setScoreboard(board);
	
	}







}
