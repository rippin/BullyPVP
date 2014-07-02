package me.bullyscraft.com.Scoreboards;

import me.bullyscraft.com.BullyPVP;
import me.bullyscraft.com.Stats.PlayerStatsObject;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.*;

public class BullyScoreBoard {

 private Player player;
 private String playerUUID;
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
 private Objective prefixes;
 private List<Team> teams = new ArrayList<Team>();

    public static List<String> prefixUUIDList = new ArrayList<String>();

 public BullyScoreBoard(Player player, PlayerStatsObject pso){
	this.player = player;
    this.playerUUID = player.getUniqueId().toString();
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
    // for prefixes
     loadTeams(BullyScoreboardManager.prefixPerms);
    this.pso = pso;
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
        setAllPlayerPrefix();
        player.setScoreboard(board);
	
	}
    public void updateWithoutPrefixes(){
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

    public String getUUID(){
    return playerUUID;
    }

    public void loadTeams(HashMap<String, String> prefixPerms){
        for (Map.Entry<String, String> entry : prefixPerms.entrySet()){
        Team team = board.registerNewTeam(entry.getKey());
         team.setPrefix(ChatColor.translateAlternateColorCodes('&', entry.getValue()));
         teams.add(team);
        }
    }

    public void reloadTeams(HashMap<String, String> prefixPerms){
        teams.clear();
        for (Map.Entry<String, String> entry : prefixPerms.entrySet()){
            Team team = board.registerNewTeam(entry.getKey());
            team.setPrefix(ChatColor.translateAlternateColorCodes('&', entry.getValue()));
            teams.add(team);
        }
    }


    public void setPSO(PlayerStatsObject pso){
        this.pso = pso;
    }


    public void setAllPlayerPrefix(){
        for (Team team : teams){
            if (player.hasPermission(team.getName())){
                team.addPlayer(player);
                break;
            }
        }
       // Now set the rest of players
       for (String uuid : prefixUUIDList){
        Player p = Bukkit.getPlayer(UUID.fromString(uuid));
           for (Team team : teams){
               if (p.hasPermission(team.getName())){
                   team.addPlayer(p);
                   break;
               }
           }
       }
        prefixUUIDList.add(player.getUniqueId().toString());

    }

    public void addPlayerToScoreboard(Player player){
        for (Team team : teams){
            if (player.hasPermission(team.getName())){
                team.addPlayer(player);
                break;
            }
        }
        this.player.setScoreboard(board);
    }

}
