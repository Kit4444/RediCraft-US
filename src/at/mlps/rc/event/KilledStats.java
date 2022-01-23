package at.mlps.rc.event;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import at.mlps.rc.mysql.lb.MySQL;

public class KilledStats implements Listener{
	
	/*
	 * av col's: killed_players, killed_mobs, gotkilled_players & gotkilled_mobs
	 * killed_players -> value how many players a player has killed
	 * killed_mobs -> value how many mobs (except players) a player has killed
	 * gotkilled_players -> value how often a player got killed by a player
	 * gotkilled_mobs -> value how often a player got killed by mobs (except a player)
	 */

	@SuppressWarnings("unused")
	public void onKill(EntityDeathEvent e) {
		Entity causer = e.getEntity();
		Entity vict = e.getEntity().getKiller();
	}
	
	@EventHandler
	/*public void onKill(EntityDamageByEntityEvent e) {
		EntityType causer = e.getDamager().getType();
		EntityType vict = e.getEntity().getType();
		if(mobs().contains(causer)) {
			if(vict == EntityType.PLAYER) {
				Player p = (Player) e.getEntity();
				int val = getKills(p, "gotkilled_mobs");
				updateKill(p, "gotkilled_mobs", (val + 1));
			}
		}else if(causer == EntityType.PLAYER) {
			if(mobs().contains(vict)) {
				Player p = (Player) e.getDamager();
				int val = getKills(p, "gotkilled_mobs");
				updateKill(p, "killed_mobs", (val + 1));
			}
		}else if(causer == EntityType.PLAYER && vict == EntityType.PLAYER) {
			Player pc = (Player) e.getDamager();
			Player pv = (Player) e.getEntity();
			int val_c = getKills(pc, "killed_players");
			int val_v = getKills(pv, "gotkilled_players");
			updateKill(pc, "killed_players", (val_c + 1));
			updateKill(pv, "gotkilled_players", (val_v + 1));
		}
	}*/
	
	int getKills(Player p, String col) {
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT " + col + " FROM redicore_userstats WHERE uuid = ?");
			ps.setString(1, p.getUniqueId().toString().replace("-", ""));
			ResultSet rs = ps.executeQuery();
			int val = rs.getInt(col);
			if(rs.next()) {
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	List<EntityType> mobs(){
		List<EntityType> entities = new ArrayList<>();
		entities.add(EntityType.BAT);
		entities.add(EntityType.BEE);
		entities.add(EntityType.BLAZE);
		entities.add(EntityType.CAT);
		entities.add(EntityType.CAVE_SPIDER);
		entities.add(EntityType.CHICKEN);
		entities.add(EntityType.COD);
		entities.add(EntityType.COW);
		entities.add(EntityType.CREEPER);
		entities.add(EntityType.DOLPHIN);
		entities.add(EntityType.DONKEY);
		entities.add(EntityType.DROWNED);
		entities.add(EntityType.ELDER_GUARDIAN);
		entities.add(EntityType.ENDER_DRAGON);
		entities.add(EntityType.ENDERMAN);
		entities.add(EntityType.ENDERMITE);
		entities.add(EntityType.EVOKER);
		entities.add(EntityType.FOX);
		entities.add(EntityType.GHAST);
		entities.add(EntityType.GIANT);
		entities.add(EntityType.GUARDIAN);
		entities.add(EntityType.HOGLIN);
		entities.add(EntityType.HORSE);
		entities.add(EntityType.HUSK);
		entities.add(EntityType.ILLUSIONER);
		entities.add(EntityType.IRON_GOLEM);
		entities.add(EntityType.LLAMA);
		entities.add(EntityType.MAGMA_CUBE);
		entities.add(EntityType.MULE);
		entities.add(EntityType.MUSHROOM_COW);
		entities.add(EntityType.OCELOT);
		entities.add(EntityType.PANDA);
		entities.add(EntityType.PARROT);
		entities.add(EntityType.PHANTOM);
		entities.add(EntityType.PIG);
		entities.add(EntityType.PIGLIN);
		entities.add(EntityType.PIGLIN_BRUTE);
		entities.add(EntityType.PILLAGER);
		entities.add(EntityType.POLAR_BEAR);
		entities.add(EntityType.PUFFERFISH);
		entities.add(EntityType.RABBIT);
		entities.add(EntityType.RAVAGER);
		entities.add(EntityType.SALMON);
		entities.add(EntityType.SHEEP);
		entities.add(EntityType.SHULKER);
		entities.add(EntityType.SHULKER_BULLET);
		entities.add(EntityType.SILVERFISH);
		entities.add(EntityType.SKELETON);
		entities.add(EntityType.SKELETON_HORSE);
		entities.add(EntityType.SLIME);
		entities.add(EntityType.SPIDER);
		entities.add(EntityType.SQUID);
		entities.add(EntityType.STRAY);
		entities.add(EntityType.STRIDER);
		entities.add(EntityType.TROPICAL_FISH);
		entities.add(EntityType.TURTLE);
		entities.add(EntityType.VEX);
		entities.add(EntityType.VILLAGER);
		entities.add(EntityType.VINDICATOR);
		entities.add(EntityType.WITCH);
		entities.add(EntityType.WITHER);
		entities.add(EntityType.WITHER_SKELETON);
		entities.add(EntityType.WITHER_SKULL);
		entities.add(EntityType.WOLF);
		entities.add(EntityType.ZOGLIN);
		entities.add(EntityType.ZOMBIE);
		entities.add(EntityType.ZOMBIE_HORSE);
		entities.add(EntityType.ZOMBIE_VILLAGER);
		entities.add(EntityType.ZOMBIFIED_PIGLIN);
		return entities;
	}
}