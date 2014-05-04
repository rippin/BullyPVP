package me.bullyscraft.com.Listeners;



import me.bullyscraft.com.AssistHandler;
import me.bullyscraft.com.BullyPVP;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class RegainHealthListener implements Listener {
	
	public BullyPVP plugin;

	public RegainHealthListener(BullyPVP plugin) {
		this.plugin = plugin;

	}
	

	@SuppressWarnings("static-access")
	@EventHandler
	public void onRegain(EntityRegainHealthEvent event){
		if (event.getEntity() instanceof Player){
			Player player = (Player) event.getEntity();
			if (player.getHealth() >= 19){
				if (plugin.damage.containsKey(player.getName())){
					AssistHandler n = plugin.damage.get(player.getName());
					n.clear();
					}
			}
		}
		
	}
}
