package at.mlps.rc.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class AutoKickerMethods implements Listener{
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		long time = (System.currentTimeMillis() / 1000);
		ScoreboardCLS.afk_timer.put(p, time);
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		long time = (System.currentTimeMillis() / 1000);
		ScoreboardCLS.afk_timer.put(p, time);
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		ScoreboardCLS.afk_timer.remove(p);
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		long time = (System.currentTimeMillis() / 1000);
		ScoreboardCLS.afk_timer.put(p, time);
	}

}
