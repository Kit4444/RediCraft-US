package at.kitsoft.redicraft.event;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import at.kitsoft.redicraft.api.APIs;
import at.kitsoft.redicraft.mysql.lb.MySQL;

public class MOTD_Join implements Listener{
	
	/*
	 * 
	 */
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		APIs api = new APIs();
		p.sendMessage("§7--------[§aMessage of the Day§7]--------"); //wont be translated
		p.sendMessage("§7You have joined %server".replace("%server", api.getServerName())); //will be translated
		p.sendMessage("§7Enjoy playing on our server."); //will be translated
	}
	
	public void sendTop3MOTDMessages(Player p) {
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redicore_motd");
			ResultSet rs = ps.executeQuery();
			rs.afterLast();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
