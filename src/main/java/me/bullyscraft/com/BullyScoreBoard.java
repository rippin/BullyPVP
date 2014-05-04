package me.bullyscraft.com;

import me.bullyscraft.com.Stats.PlayerStatsObject;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

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
    this.pso = pso;
	
	
}

 	public void setUp(){
	
 	coins.setScore(pso.getCoins());
	deaths.setScore(pso.getDeaths());
	kills.setScore(pso.getKills());
	streak.setScore(pso.getCurrentstreak());
	highStreak.setScore(pso.getHigheststreak());
	player.setScoreboard(board);
	
	
 	}

	public void update(){

        coins.setScore(pso.getCoins());
        deaths.setScore(pso.getDeaths());
        kills.setScore(pso.getKills());
        streak.setScore(pso.getCurrentstreak());
        highStreak.setScore(pso.getHigheststreak());
        player.setScoreboard(board);
	
	}







}
