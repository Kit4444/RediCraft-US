package at.mlps.rc.cmd;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import at.mlps.rc.api.APIs;
import at.mlps.rc.main.Main;
import at.mlps.rc.mysql.lb.MySQL;

public class Homesystem implements CommandExecutor, Listener{
	
	int maxhomes = 16;
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) throws IOException {
		Player p = e.getPlayer();
		String uuid = p.getUniqueId().toString();
		HashMap<String, Object> hm = new HashMap<>();
		hm.put("uuid", uuid);
		try {
			if(!Main.mysql.isInDatabase("redicore_homecount", hm)) {
				hm.put("homes", 0);
				Main.mysql.insertInto("redicore_homecount", hm);
			}
		}catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		APIs api = new APIs();
		if(!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage(api.prefix("main") + "§cBitte nur ingame benutzen.");
		}else {
			Player p = (Player)sender;
			String uuid = p.getUniqueId().toString();
			if(cmd.getName().equalsIgnoreCase("sethome")) {
				if(args.length == 1) {
					String home = args[0];
					sethome(p, home.toLowerCase(), home);
				}else{
					p.sendMessage(api.prefix("main") + api.returnStringReady(p, "usage") + "§7 /sethome <Homename>");
				}
			}else if(cmd.getName().equalsIgnoreCase("delhome")) {
				if(args.length == 1) {
					delHome(p, args[0]);
				}else {
					p.sendMessage(api.prefix("main") + api.returnStringReady(p, "usage") + "§7 /delhome <Homename>");
				}
			}else if(cmd.getName().equalsIgnoreCase("home")) {
				if(args.length == 1) {
					String home = args[0].toLowerCase();
					try {
						PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redicore_homesystem WHERE uuid = ? AND home = ? AND server = ?");
						ps.setString(1, uuid);
						ps.setString(2, home);
						ps.setString(3, api.getServerName());
						ResultSet rs = ps.executeQuery();
						rs.next();
						Location loc = new Location(Bukkit.getWorld(rs.getString("world")), rs.getDouble("x"), rs.getDouble("y"), rs.getDouble("z"), (float) rs.getDouble("yaw"), (float) rs.getDouble("pitch"));
						p.teleport(loc);
						p.sendMessage(api.prefix("main") + api.returnStringReady(p, "cmd.home.teleport").replace("%home", rs.getString("cshn")));
					}catch (SQLException e) {
						//e.printStackTrace();
						api.sendMSGReady(p, "cmd.home.nothome");
					}
				}else {
					p.sendMessage(api.prefix("main") + api.returnStringReady(p, "usage") + "§7 /home <Homename>");
				}
			}else if(cmd.getName().equalsIgnoreCase("listhomes")) {
				try {
					PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redicore_homesystem WHERE uuid = ?");
					ps.setString(1, uuid);
					ResultSet rs = ps.executeQuery();
					p.sendMessage("§7========[§aHomelist§7]========");
					int i = 0;
					while(rs.next()) {
						i++;
						p.sendMessage("§7ID: §a" + rs.getInt("ID") + " §7| Homename: §a" + rs.getString("cshn") + " §7| Date: §a" + rs.getString("datetime") + " §7| Server: §a" + rs.getString("server"));
					}
					p.sendMessage(api.prefix("main") + api.returnStringReady(p, "cmd.listhomes.homes").replace("%homecount", String.valueOf(i)));
				}catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}
	
	private void sethome(Player p, String homename, String cshn) {
		APIs api = new APIs();
		String uuid = p.getUniqueId().toString();
		HashMap<String, Object> hm = new HashMap<>();
		hm.put("home", homename);
		hm.put("uuid", uuid);
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy - HH:mm:ss");
		if(p.hasPermission("mlps.homes.infinite")) { 
			if(getHomeCount(p) <= maxhomes) {
				try {
					if(Main.mysql.isInDatabase("redicore_homesystem", hm)) {
						api.sendMSGReady(p, "cmd.sethome.homeexistsalready");
						//p.sendMessage(api.prefix("main") + "§7Dieses Home existiert bereits. L§sche es oder verwende einen anderen Namen.");
					}else {
						Location loc = p.getLocation();
						PreparedStatement ps = MySQL.getConnection().prepareStatement("INSERT INTO redicore_homesystem SET uuid = ?, name = ?, x = ?, y = ?, z = ?, yaw = ?, pitch = ?, world = ?, cshn = ?, home = ?, server = ?, datetime = ?");
						ps.setString(1, uuid);
						ps.setString(2, p.getName());
						ps.setDouble(3, loc.getX());
						ps.setDouble(4, loc.getY());
						ps.setDouble(5, loc.getZ());
						ps.setDouble(6, loc.getYaw());
						ps.setDouble(7, loc.getPitch());
						ps.setString(8, loc.getWorld().getName());
						ps.setString(9, cshn);
						ps.setString(10, homename);
						ps.setString(11, api.getServerName());
						ps.setString(12, sdf.format(new Date()));
						ps.executeUpdate();
						ps.closeOnCompletion();
						countHome(p, true);
						//p.sendMessage(api.prefix("main") + "§7Home gesetzt! Home: §a" + homename + "§7 | Homecount: §a" + getHomeCount(p));
						p.sendMessage(api.prefix("main") + api.returnStringReady(p, "cmd.sethome.successfully").replace("%home", homename).replace("%count", String.valueOf(getHomeCount(p))));
					}
				}catch (SQLException e) {
					e.printStackTrace();
				}
			}else {
				try {
					if(Main.mysql.isInDatabase("redicore_homesystem", hm)) {
						api.sendMSGReady(p, "cmd.sethome.homeexistsalready");
						//p.sendMessage(api.prefix("main") + "§7Dieses Home existiert bereits. L§sche es oder verwende einen anderen Namen.");
					}else {
						Location loc = p.getLocation();
						PreparedStatement ps = MySQL.getConnection().prepareStatement("INSERT INTO redicore_homesystem SET uuid = ?, name = ?, x = ?, y = ?, z = ?, yaw = ?, pitch = ?, world = ?, cshn = ?, home = ?, server = ?, datetime = ?");
						ps.setString(1, uuid);
						ps.setString(2, p.getName());
						ps.setDouble(3, loc.getX());
						ps.setDouble(4, loc.getY());
						ps.setDouble(5, loc.getZ());
						ps.setDouble(6, loc.getYaw());
						ps.setDouble(7, loc.getPitch());
						ps.setString(8, loc.getWorld().getName());
						ps.setString(9, cshn);
						ps.setString(10, homename);
						ps.setString(11, api.getServerName());
						ps.setString(12, sdf.format(new Date()));
						ps.executeUpdate();
						ps.closeOnCompletion();
						countHome(p, true);
						p.sendMessage(api.prefix("main") + api.returnStringReady(p, "cmd.sethome.limitexceeded.bypass").replace("%maxhomes", String.valueOf(maxhomes)));
						p.sendMessage(api.prefix("main") + api.returnStringReady(p, "cmd.sethome.successfully").replace("%home", homename).replace("%count", String.valueOf(getHomeCount(p))));
						//p.sendMessage(api.prefix("main") + "§c10 Home limit §berschritten - §bersprungen durch Berechtigung.");
						//p.sendMessage(api.prefix("main") + "§7Home gesetzt! Home: §a" + homename + "§7 | Homecount: §a" + getHomeCount(p));
						//p.sendMessage(api.prefix("main") + LanguageHandler.returnStringReady(p, "cmd.home.set.successfully").replace("%home", homename).replace("%count", homecount));
					}
				}catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}else {
			if(getHomeCount(p) <= maxhomes) { //wenn weniger als oder gleichviel homes hat, dann zulassen, wenn wenn mehr aber keine berechtigung, blocken.
				try {
					if(Main.mysql.isInDatabase("redicore_homesystem", hm)) {
						api.sendMSGReady(p, "cmd.sethome.homeexistsalready");
						//p.sendMessage(api.prefix("main") + "§7Dieses Home existiert bereits. L§sche es oder verwende einen anderen Namen.");
					}else {
						Location loc = p.getLocation();
						PreparedStatement ps = MySQL.getConnection().prepareStatement("INSERT INTO redicore_homesystem SET uuid = ?, name = ?, x = ?, y = ?, z = ?, yaw = ?, pitch = ?, world = ?, cshn = ?, home = ?, server = ?, datetime = ?");
						ps.setString(1, uuid);
						ps.setString(2, p.getName());
						ps.setDouble(3, loc.getX());
						ps.setDouble(4, loc.getY());
						ps.setDouble(5, loc.getZ());
						ps.setDouble(6, loc.getYaw());
						ps.setDouble(7, loc.getPitch());
						ps.setString(8, loc.getWorld().getName());
						ps.setString(9, cshn);
						ps.setString(10, homename);
						ps.setString(11, api.getServerName());
						ps.setString(12, sdf.format(new Date()));
						ps.executeUpdate();
						ps.closeOnCompletion();
						countHome(p, true);
						p.sendMessage(api.prefix("main") + api.returnStringReady(p, "cmd.sethome.successfully").replace("%home", homename).replace("%count", String.valueOf(getHomeCount(p))));
						//p.sendMessage(api.prefix("main") + "§7Home gesetzt! Home: §a" + homename + "§7 | Homecount: §a" + getHomeCount(p));
						//p.sendMessage(api.prefix("main") + LanguageHandler.returnStringReady(p, "cmd.home.set.successfully").replace("%home", homename).replace("%count", homecount));
					}
				}catch (SQLException e) {
					e.printStackTrace();
				}
			}else {
				p.sendMessage(api.prefix("main") + api.returnStringReady(p, "cmd.sethome.limitexceeded.nobypass").replace("%maxhomes", String.valueOf(maxhomes)));
				//p.sendMessage(api.prefix("main") + "§cDu hast bereits " + maxhomes + " Homes. L§sche ein nicht gebrauchtes Home, um dieses zu setzen.");
			}
		}
	}

	private void delHome(Player p, String homename) {
		APIs api = new APIs();
		String uuid = p.getUniqueId().toString();
		HashMap<String, Object> hm = new HashMap<>();
		hm.put("uuid", uuid);
		hm.put("home", homename);
		try {
			if(Main.mysql.isInDatabase("redicore_homesystem", hm)) {
				PreparedStatement ps = MySQL.getConnection().prepareStatement("DELETE FROM redicore_homesystem WHERE uuid = ? AND home = ? AND server = ?");
				ps.setString(1, uuid);
				ps.setString(2, homename);
				ps.setString(3, api.getServerName());
				ps.executeUpdate();
				countHome(p, false);
				p.sendMessage(api.prefix("main") + api.returnStringReady(p, "cmd.delhome.successfully").replace("%home", homename));
				//p.sendMessage(api.prefix("main") + "§7Home §a" + homename + " §7wurde erfolgreich gel§scht.");
			}else {
				api.sendMSGReady(p, "cmd.delhome.notexisting");
				//p.sendMessage(api.prefix("main") + "§7Dieses Home existiert nicht.");
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void countHome(Player p, boolean state) {
		String uuid = p.getUniqueId().toString();
		HashMap<String, Object> hm = new HashMap<>();
		hm.put("uuid", uuid);
		int homes = getHomeCount(p);
		try {
			if(Main.mysql.isInDatabase("redicore_homecount", hm)) {
				if(state == true) {
					PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE redicore_homecount SET homes = ? WHERE uuid = ?");
					ps.setInt(1, (homes + 1));
					ps.setString(2, uuid);
					ps.executeUpdate();
					ps.close();
				}else {
					PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE redicore_homecount SET homes = ? WHERE uuid = ?");
					ps.setInt(1, (homes - 1));
					ps.setString(2, uuid);
					ps.executeUpdate();
					ps.close();
				}
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private int getHomeCount(Player p) {
		String uuid = p.getUniqueId().toString();
		int i = 0;
		HashMap<String, Object> hm = new HashMap<>();
		hm.put("uuid", uuid);
		try {
			if(Main.mysql.isInDatabase("redicore_homecount", hm)) {
				PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redicore_homecount WHERE uuid = ?");
				ps.setString(1, uuid);
				ResultSet rs = ps.executeQuery();
				rs.next();
				i = rs.getInt("homes");
				rs.close();
				ps.closeOnCompletion();
			}else {
				i = 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}

}