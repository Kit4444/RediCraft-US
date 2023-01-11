package at.kitsoft.redicraft.event;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import at.kitsoft.redicraft.mysql.lb.MySQL;

public class KilledStats implements Listener{
	
	/*
	 * av col's: killed_players, killed_mobs, gotkilled_players & gotkilled_mobs
	 * killed_players -> value how many players a player has killed
	 * killed_mobs -> value how many mobs (except players) a player has killed
	 * gotkilled_players -> value how often a player got killed by a player
	 * gotkilled_mobs -> value how often a player got killed by mobs (except a player)
	 * 
	 * Revision 2 @ 15th August 2022, 5:50pm
	 */

	@EventHandler
	public void onKill(EntityDeathEvent e) {
		if(e.getEntity().getKiller() == null) {
			return;
		}
		if(e.getEntity() == null) {
			return;
		}
		EntityType causer = e.getEntity().getKiller().getType();
		EntityType vict = e.getEntity().getType();
		if(causer == EntityType.PLAYER && vict == EntityType.PLAYER) {
			//when player kills player
			Player pc = e.getEntity().getKiller();
			Player pv = (Player) e.getEntity();
			int causer_kills = getKills(pc, "killed_players");
			int victim_kills = getKills(pv, "gotkilled_players");
			updateKill(pc, "killed_players", (causer_kills + 1));
			updateKill(pv, "gotkilled_players", (victim_kills + 1));
		}else if(causer == EntityType.PLAYER && vict != EntityType.PLAYER) {
			//when player kills ai entity
			Player pc = e.getEntity().getKiller();
			int causer_kills = getKills(pc, "killed_mobs");
			updateKill(pc, "killed_mobs", (causer_kills + 1));
		}else if(causer != EntityType.PLAYER && vict == EntityType.PLAYER) {
			//when ai entity kills player
			Player pv = (Player) e.getEntity();
			int victim_kills = getKills(pv, "gotkilled_mobs");
			updateKill(pv, "gotkilled_mobs", (victim_kills + 1));
		}
	}
	
	public void onPlayerDeath(PlayerDeathEvent e) {
		e.getEntity().getKiller();
	}
	
	int getKills(Player p, String col) {
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT " + col + " FROM redicore_userstats WHERE uuid = ?");
			ps.setString(1, p.getUniqueId().toString().replace("-", ""));
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				int val = rs.getInt(col);
				return val;
			}else {
				return -1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return -2;
		}
	}
	
	void updateKill(Player p, String col, int value) {
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE redicore_userstats SET " + col + " = ? WHERE uuid = ?");
			ps.setInt(1, value);
			ps.setString(2, p.getUniqueId().toString().replace("-", ""));
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}