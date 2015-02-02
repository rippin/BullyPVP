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
 private Team team;
 private BullyPVP plugin;
 private PlayerStatsObject pso;
 private Objective prefixes;
 private List<Team> teams = new ArrayList<Team>();

    //public static List<String> prefixUUIDList = new ArrayList<String>();
@SuppressWarnings("deprecated")
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
     this.pso = pso;
     BullyScoreboardManager.getAllBullyScoreboards().add(this); // add le scoreboard
    // for prefixes
     setPlayerTeam(BullyScoreboardManager.prefixPerms, this.player);
     BullyScoreboardManager.addPlayerToAllScoreboards(player); //Update all scoreboards
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

    public void setPlayerTeam(HashMap<String, String> prefixPerms, Player player){
        String ranking;
        String prefixPlaceHolder;
        BullyScoreBoard b = BullyScoreboardManager.getBullyScoreboard(player.getUniqueId().toString());
        PlayerStatsObject pso = b.getPSO();
        Team team;
        if (!containsTeam(player)) {
        team = board.registerNewTeam(player.getName());
        }
        else {
            team = board.getTeam(player.getName());
        }
        if (pso.getRanking() == null || pso.getRanking().equalsIgnoreCase("0")){
            ranking = "NR";
        }
        else {
            ranking = pso.getRanking();
        }
        for (Map.Entry<String, String> entry : prefixPerms.entrySet()){
            if (player.hasPermission(entry.getKey())){
               prefixPlaceHolder = entry.getValue();
                if (prefixPlaceHolder.contains("@")){
                    String splitSuffix[] = entry.getValue().split("@");
                    prefixPlaceHolder = ChatColor.translateAlternateColorCodes('&', splitSuffix[0]);
                    String suffix = ChatColor.translateAlternateColorCodes('&', splitSuffix[1]);
                    suffix = suffix.replace("%rank%", ranking);
                    team.setSuffix(suffix);
                }
                else {
                    prefixPlaceHolder = ChatColor.translateAlternateColorCodes('&', entry.getValue());
                }
                team.setPrefix(prefixPlaceHolder);
                team.addPlayer(player);
                teams.add(team);
                break;
            }
        }
    }
    public void loadTeam(){
      teams.clear();
      this.team = board.registerNewTeam(this.player.getName());

    }


    public void setPSO(PlayerStatsObject pso){
        this.pso = pso;
    }
    public PlayerStatsObject getPSO(){ return pso; }
    public Team getTeam() { return  team; }


    public void setAllPlayerPrefix(){
       // Now set the rest of players
       for (Player p : Bukkit.getOnlinePlayers()){
           if (p == null || !p.isOnline() || p.getName().equalsIgnoreCase(player.getName())){
              continue;
           }
           setPlayerTeam(BullyScoreboardManager.prefixPerms, p);
       }

    }

    public void addPlayerToScoreboard(Player player){
        setPlayerTeam(BullyScoreboardManager.prefixPerms, player);
        player.setScoreboard(board);
    }

    public void removeTeam(Player player){
            if (teams.contains(player.getName())){
                board.getTeam(player.getName()).unregister();
                teams.remove(player.getName());
            }
        }

    public boolean containsTeam(Player player){
    for (Team team : board.getTeams()){
        if (team.getName().equalsIgnoreCase(player.getName())){
            return  true;
        }
    }
     return  false;
   }
}
