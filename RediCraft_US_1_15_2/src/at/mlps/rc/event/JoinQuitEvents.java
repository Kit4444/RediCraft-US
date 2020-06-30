package at.mlps.rc.event;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import at.mlps.rc.api.APIs;
import at.mlps.rc.mysql.lb.MySQL;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class JoinQuitEvents implements Listener{
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		e.setJoinMessage(null);
		Player p = e.getPlayer();
		if(!p.hasPlayedBefore()) {
			Serverteleporter.worldteleporter(p);
		}
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        String uuid = p.getUniqueId().toString().replace("-", "");
        try {
			PermissionUser pu = PermissionsEx.getUser(p);
    		PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE redicore_userstats SET userrank = ?, online = ?, server = ?, lastjoints = ?, lastjoinstring = ?, lastloginip = ?, isstaff = ? WHERE uuid = ?");
    		if(pu.inGroup("PMan")) {
        		ps.setString(1, "Projectmanager");
        	}else if(pu.inGroup("CMan")) {
        		ps.setString(1, "Community Manager");
        	}else if(pu.inGroup("AMan")) {
        		ps.setString(1, "Administrations Manager");
        	}else if(pu.inGroup("Developer")) {
        		ps.setString(1, "Developer");
        	}else if(pu.inGroup("Admin")) {
        		ps.setString(1, "Admin");
        	}else if(pu.inGroup("Mod")) {
        		ps.setString(1, "Moderator");
        	}else if(pu.inGroup("Support")) {
        		ps.setString(1, "Support");
        	}else if(pu.inGroup("Translator")) {
        		ps.setString(1, "Translator");
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
    		ps.setBoolean(2, true);
    		ps.setString(3, APIs.getServerName());
    		ps.setLong(4, ts.getTime());
    		ps.setString(5, stime);
    		ps.setString(6, p.getAddress().getHostString());
    		if(p.hasPermission("mlps.isStaff")) {
    			ps.setBoolean(7, true);
    		}else {
    			ps.setBoolean(7, false);
    		}
    		ps.setString(8, uuid);
    		ps.executeUpdate();
    		ps.close();
		}catch (SQLException ex) {
			ex.printStackTrace();
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
        		ps.setString(1, "Administrations Manager");
        	}else if(pu.inGroup("Developer")) {
        		ps.setString(1, "Developer");
        	}else if(pu.inGroup("Admin")) {
        		ps.setString(1, "Admin");
        	}else if(pu.inGroup("Mod")) {
        		ps.setString(1, "Moderator");
        	}else if(pu.inGroup("Support")) {
        		ps.setString(1, "Support");
        	}else if(pu.inGroup("Translator")) {
        		ps.setString(1, "Translator");
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
}