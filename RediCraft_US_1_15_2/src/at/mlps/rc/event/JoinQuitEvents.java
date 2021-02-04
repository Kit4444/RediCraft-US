package at.mlps.rc.event;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import at.mlps.rc.api.APIs;
import at.mlps.rc.main.Main;
import at.mlps.rc.mysql.lb.MySQL;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class JoinQuitEvents implements Listener{
	
	static File spawnfile = new File("plugins/RCUSS/spawn.yml");
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		APIs api = new APIs();
		e.setJoinMessage(null);
		Player p = e.getPlayer();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        String uuid = p.getUniqueId().toString().replace("-", "");
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(spawnfile);
        try {
			PermissionUser pu = PermissionsEx.getUser(p);
    		PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE redicore_userstats SET userrank = ?, rankcolor = ?, online = ?, server = ?, lastjoints = ?, lastjoinstring = ?, lastloginip = ?, isstaff = ?, username = ? WHERE uuid = ?");
    		if(pu.inGroup("PMan")) {
        		ps.setString(1, "Projectmanager");
        		ps.setString(2, "#ffaa00");
    		}else if(pu.inGroup("HumanR")) {
    			ps.setString(1, "Projectmanager");
        		ps.setString(2, "#aa00aa");
        	}else if(pu.inGroup("CMan")) {
        		ps.setString(1, "Community Manager");
        		ps.setString(2, "#00aa00");
        	}else if(pu.inGroup("AMan")) {
        		ps.setString(1, "Game Moderation Manager");
        		ps.setString(2, "#aa0000");
        	}else if(pu.inGroup("Developer")) {
        		ps.setString(1, "Developer");
        		ps.setString(2, "#ff55ff");
        	}else if(pu.inGroup("Admin")) {
        		ps.setString(1, "Game Moderator");
        		ps.setString(2, "#ff5555");
        	}else if(pu.inGroup("Mod")) {
        		ps.setString(1, "Moderator");
        		ps.setString(2, "#55ff55");
        	}else if(pu.inGroup("Support")) {
        		ps.setString(1, "Support");
        		ps.setString(2, "#5555ff");
        	}else if(pu.inGroup("Translator")) {
        		ps.setString(1, "Content");
        		ps.setString(2, "#ffff55");
        	}else if(pu.inGroup("Builder")) {
        		ps.setString(1, "Builder");
        		ps.setString(2, "#55ffff");
        	}else if(pu.inGroup("RLTM")) {
        		ps.setString(1, "Retired Legend Team Member");
        		ps.setString(2, "#00aaaa");
        	}else if(pu.inGroup("RTM")) {
        		ps.setString(1, "Retired Team Member");
        		ps.setString(2, "#00aaaa");
        	}else if(pu.inGroup("Partner")) {
        		ps.setString(1, "Partner");
        		ps.setString(2, "#00aa00");
        	}else if(pu.inGroup("Beta")) {
        		ps.setString(1, "Beta-Tester");
        		ps.setString(2, "#00aaaa");
        	}else if(pu.inGroup("NitroBooster")) {
        		ps.setString(1, "Nitrobooster");
        		ps.setString(2, "#00aaaa");
        	}else if(pu.inGroup("Friend")) {
        		ps.setString(1, "Friend");
        		ps.setString(2, "#00aaaa");
        	}else {
        		ps.setString(1, "Player");
        		ps.setString(2, "#aaaaaa");
        	}
    		ps.setBoolean(3, true);
    		ps.setString(4, api.getServerName());
    		ps.setLong(5, ts.getTime());
    		ps.setString(6, stime);
    		ps.setString(7, p.getAddress().getHostString());
    		if(p.hasPermission("mlps.isStaff")) {
    			ps.setBoolean(8, true);
    		}else {
    			ps.setBoolean(8, false);
    		}
    		ps.setString(9, p.getName());
    		ps.setString(10, uuid);
    		ps.executeUpdate();
    		ps.close();
		}catch (SQLException ex) {
			ex.printStackTrace();
		}
        if(!p.hasPlayedBefore()) {
        	Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
				@Override
				public void run() {
					APIs api = new APIs();
					String sname = api.getServerName();
					if(sname.equalsIgnoreCase("Survival")) {
						p.teleport(retLoc(cfg, "freebuild"));
					}else if(sname.equalsIgnoreCase("Creative")) {
						p.teleport(retLoc(cfg, "plotworld"));
					}else if(sname.equalsIgnoreCase("SkyBlock")) {
						p.teleport(retLoc(cfg, "plotworld"));
					}else if(sname.equalsIgnoreCase("Farmserver")) {
						p.teleport(retLoc(cfg, "freebuild"));
					}
				}
			}, 20);
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		e.setQuitMessage(null);
		Player p = e.getPlayer();
		String uuid = p.getUniqueId().toString().replace("-", "");
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        PermissionUser pu = PermissionsEx.getUser(p);
        try {
        	PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE redicore_userstats SET userrank = ?, lastjoints = ?, lastjoinstring = ?, lastloginip = ?, online = ? WHERE uuid = ?");
        	if(pu.inGroup("PMan")) {
        		ps.setString(1, "Projectmanager");
        	}else if(pu.inGroup("CMan")) {
        		ps.setString(1, "Community Manager");
        	}else if(pu.inGroup("AMan")) {
        		ps.setString(1, "Game Moderation Manager");
        	}else if(pu.inGroup("Developer")) {
        		ps.setString(1, "Developer");
        	}else if(pu.inGroup("Admin")) {
        		ps.setString(1, "Game Moderator");
        	}else if(pu.inGroup("Mod")) {
        		ps.setString(1, "Moderator");
        	}else if(pu.inGroup("Support")) {
        		ps.setString(1, "Support");
        	}else if(pu.inGroup("Translator")) {
        		ps.setString(1, "Content");
        	}else if(pu.inGroup("Builder")) {
        		ps.setString(1, "Builder");
        	}else if(pu.inGroup("RLTM")) {
        		ps.setString(1, "Retired Legend Team Member");
        	}else if(pu.inGroup("RTM")) {
        		ps.setString(1, "Retired Team Member");
        	}else if(pu.inGroup("Partner")) {
        		ps.setString(1, "Partner");
        	}else if(pu.inGroup("Beta")) {
        		ps.setString(1, "Beta-Tester");
        	}else if(pu.inGroup("Patron")) {
        		ps.setString(1, "Patron");
        	}else if(pu.inGroup("NitroBooster")) {
        		ps.setString(1, "Nitrobooster");
        	}else if(pu.inGroup("Friend")) {
        		ps.setString(1, "Friend");
        	}else {
        		ps.setString(1, "Player");
        	}
        	ps.setLong(2, ts.getTime());
        	ps.setString(3, stime);
        	ps.setString(4, p.getAddress().getHostString());
        	ps.setBoolean(5, false);
        	ps.setString(6, uuid);
        	ps.executeUpdate();
        	ps.close();
        }catch (SQLException ex) { ex.printStackTrace(); }
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		e.setDeathMessage(null);
	}
	
	private Location retLoc(YamlConfiguration cfg, String type) {
		Location loc = null;
		if(cfg.contains("Spawn." + type + ".WORLD")) {
			loc = new Location(Bukkit.getWorld(cfg.getString("Spawn." + type + ".WORLD")), cfg.getDouble("Spawn." + type + ".X"), cfg.getDouble("Spawn." + type + ".Y"), cfg.getDouble("Spawn." + type + ".Z"), (float)cfg.getDouble("Spawn." + type + ".YAW"), (float)cfg.getDouble("Spawn." + type + ".PITCH"));
		}else {
			loc = null;
		}
		return loc;
	}
}