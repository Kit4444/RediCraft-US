package at.mlps.rc.main;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import at.mlps.rc.api.APIs;
import at.mlps.rc.mysql.lb.MySQL;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_16_R2.MinecraftServer;

public class Serverupdater implements Listener{
	
	@SuppressWarnings({ "deprecation", "resource" })
	public static void updateServer() {
		if(MySQL.isConnected()) {
			Runtime runtime = Runtime.getRuntime();
			long ramusage = (runtime.totalMemory() - runtime.freeMemory()) / 1048576L;
			long ramtotal = runtime.totalMemory() / 1048576L;
			int players = Bukkit.getOnlinePlayers().size();
			int pmax = Bukkit.getMaxPlayers();
			Timestamp ts = new Timestamp(System.currentTimeMillis());
			long timestamp = ts.getTime();
			SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
		    String stime = time.format(new Date());
		    StringBuilder sb = new StringBuilder("");
		    for(double tps : MinecraftServer.getServer().recentTps) {
		    	sb.append(format(tps));
		    }
		    String tps = sb.substring(0, sb.length() - 1);
		    int code1 = random(0, 5000);
			int code2 = random(5001, 10000);
			String gcode1 = code1 + "-" + code2;
		    try {
		    	Main.mysql.update("UPDATE useless_testtable SET toupdate = '" + gcode1 + "' WHERE type = '" + APIs.getServerName().toLowerCase() + "';");
		    	PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE redicore_serverstats SET ramusage = ?, serverid = ?, currPlayers = ?, maxPlayers = ?, lastupdateTS = ?, lastupdateST = ?, ramavailable = ?, version = ?, tps = ? WHERE servername = ?");
		    	ps.setInt(1, (int) ramusage);
				ps.setString(2, APIs.getServerId());
				ps.setInt(3, players);
				ps.setInt(4, pmax);
				ps.setInt(5, (int) timestamp);
				ps.setString(6, stime);
				ps.setInt(7, (int) ramtotal);
				ps.setString(8, "1.16.3");
				ps.setString(9, tps);
				ps.setString(10, APIs.getServerName());
				ps.executeUpdate();
				ps.close();
		    }catch (SQLException e) { e.printStackTrace(); Bukkit.getConsoleSender().sendMessage("§cCan't update DB-Stats."); }
		}else {
			Bukkit.getConsoleSender().sendMessage("§cDB is not connected.");
		}
	}
	
	static int secs = 0;
	
	public static void Serverrestarter() {
		secs++;
		if(secs == 59) {
			secs = 0;
		}
		SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
	    String stime = time.format(new Date());
	    if(stime.equals("20:00:00")) {
	    	for(Player all : Bukkit.getOnlinePlayers()) {
	    		APIs.sendMSGReady(all, "restarter.time.200000");
	    	}
	    }else if(stime.equals("21:00:00")) {
	    	for(Player all : Bukkit.getOnlinePlayers()) {
	    		APIs.sendMSGReady(all, "restarter.time.210000");
	    	}
	    }else if(stime.equals("22:00:00")) {
	    	for(Player all : Bukkit.getOnlinePlayers()) {
	    		APIs.sendMSGReady(all, "restarter.time.220000");
	    	}
	    }else if(stime.equals("22:30:00")) {
	    	for(Player all : Bukkit.getOnlinePlayers()) {
	    		APIs.sendMSGReady(all, "restarter.time.223000");
	    	}
	    }else if(stime.equals("23:00:00")) {
	    	for(Player all : Bukkit.getOnlinePlayers()) {
	    		APIs.sendMSGReady(all, "restarter.time.230000");
	    	}
	    }else if(stime.equals("23:30:00")) {
	    	for(Player all : Bukkit.getOnlinePlayers()) {
	    		APIs.sendMSGReady(all, "restarter.time.233000");
	    	}
	    }else if(stime.equals("23:45:00")) {
	    	for(Player all : Bukkit.getOnlinePlayers()) {
	    		APIs.sendMSGReady(all, "restarter.time.234500");
	    	}
	    }else if(stime.equals("23:55:00")) {
	    	for(Player all : Bukkit.getOnlinePlayers()) {
	    		APIs.sendMSGReady(all, "restarter.time.235500");
	    	}
	    }else if(stime.equals("23:56:00")) {
	    	for(Player all : Bukkit.getOnlinePlayers()) {
	    		APIs.sendMSGReady(all, "restarter.time.235600");
	    	}
	    }else if(stime.equals("23:57:00")) {
	    	for(Player all : Bukkit.getOnlinePlayers()) {
	    		APIs.sendMSGReady(all, "restarter.time.235700");
	    	}
	    }else if(stime.equals("23:58:00")) {
	    	for(Player all : Bukkit.getOnlinePlayers()) {
	    		APIs.sendMSGReady(all, "restarter.time.235800");
	    	}
	    }else if(stime.equals("23:59:00")) {
	    	for(Player all : Bukkit.getOnlinePlayers()) {
	    		APIs.sendMSGReady(all, "restarter.time.235900");
	    	}
	    }else if(stime.equals("23:59:50")) {
	    	for(Player all : Bukkit.getOnlinePlayers()) {
	    		APIs.sendMSGReady(all, "restarter.time.235950");
	    	}
	    }else if(stime.equals("23:59:51")) {
	    	for(Player all : Bukkit.getOnlinePlayers()) {
	    		APIs.sendMSGReady(all, "restarter.time.235951");
	    	}
	    }else if(stime.equals("23:59:52")) {
	    	for(Player all : Bukkit.getOnlinePlayers()) {
	    		APIs.sendMSGReady(all, "restarter.time.235952");
	    	}
	    }else if(stime.equals("23:59:53")) {
	    	for(Player all : Bukkit.getOnlinePlayers()) {
	    		APIs.sendMSGReady(all, "restarter.time.235953");
	    	}
	    }else if(stime.equals("23:59:54")) {
	    	for(Player all : Bukkit.getOnlinePlayers()) {
	    		APIs.sendMSGReady(all, "restarter.time.235954");
	    	}
	    }else if(stime.equals("23:59:55")) {
	    	for(Player all : Bukkit.getOnlinePlayers()) {
	    		APIs.sendMSGReady(all, "restarter.time.235955");
	    	}
	    }else if(stime.equals("23:59:56")) {
	    	for(Player all : Bukkit.getOnlinePlayers()) {
	    		APIs.sendMSGReady(all, "restarter.time.235956");
	    	}
	    }else if(stime.equals("23:59:57")) {
	    	for(Player all : Bukkit.getOnlinePlayers()) {
	    		APIs.sendMSGReady(all, "restarter.time.235957");
	    	}
	    }else if(stime.equals("23:59:58")) {
	    	for(Player all : Bukkit.getOnlinePlayers()) {
	    		APIs.sendMSGReady(all, "restarter.time.235958");
	    	}
	    }else if(stime.equals("23:59:59")) {
	    	for(Player all : Bukkit.getOnlinePlayers()) {
	    		APIs.sendMSGReady(all, "restarter.time.235959");
	    	}
	    }else if(stime.equals("00:00:01")) {
	    	Bukkit.shutdown();
	    }
	}
	
	private static String format(double tps) {
		return String.valueOf((tps > 18.0 ? ChatColor.GREEN : (tps > 16.0 ? ChatColor.YELLOW : ChatColor.RED)).toString()) + (tps > 20.0 ? "*" : "") + Math.min((double)Math.round(tps * 100.0) / 100.0, 20.0);
	}
	
	static String prefix = "§7[§aClear§cLag§7]";
	static int time = 0;
	
	public static void clearlag() {
		time++;
		if(time == 600) {
			Bukkit.broadcastMessage(prefix + " Removing items in §65 minutes§7.");
		}else if(time == 780) {
			Bukkit.broadcastMessage(prefix + " Removing items in §62 minutes§7.");
		}else if(time == 840) {
			Bukkit.broadcastMessage(prefix + " Removing items in §61 minute§7.");
		}else if(time == 885) {
			Bukkit.broadcastMessage(prefix + " Removing items in §615 seconds§7.");
		}else if(time == 895) {
			Bukkit.broadcastMessage(prefix + " Removing items in §65 seconds§7.");
		}else if(time == 896) {
			Bukkit.broadcastMessage(prefix + " Removing items in §64 seconds§7.");
		}else if(time == 897) {
			Bukkit.broadcastMessage(prefix + " Removing items in §63 seconds§7.");
		}else if(time == 898) {
			Bukkit.broadcastMessage(prefix + " Removing items in §62 seconds§7.");
		}else if(time == 899) {
			Bukkit.broadcastMessage(prefix + " Removing items in §61 second§7.");
		}else if(time == 900) {
			time = 0;
			int worldentities = 0;
			for(World world : Bukkit.getWorlds()) {
				for(Entity e : world.getEntities()) {
					if(e instanceof Item) {
						worldentities++;
						e.remove();
					}
				}
			}
			Bukkit.broadcastMessage(prefix + " Removed §6" + worldentities + " §7Items on §6" + Bukkit.getWorlds().size() + " Worlds§7!");
		}
	}
	
	public static int getPlayTime(Player p) {
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT playtime FROM redicore_userstats WHERE uuid = ?");
			ps.setString(1, p.getUniqueId().toString().replace("-", ""));
			ResultSet rs = ps.executeQuery();
			rs.next();
			return rs.getInt("playtime");
		}catch (SQLException e) {
			return 0;
		}
	}
	
	public void setPlayTime(Player p, long playtime) {
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE redicore_userstats SET playtime = ? WHERE uuid = ?");
			ps.setLong(1, playtime);
			ps.setString(2, p.getUniqueId().toString().replace("-", ""));
			ps.executeUpdate();
		}catch (SQLException e) {
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		File file = new File("plugins/RCUSS/ptimecache.yml");
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		Player p = e.getPlayer();
		String uuid = p.getUniqueId().toString().replace("-", "");
		long systime = (System.currentTimeMillis() / 1000);
		cfg.set(uuid, systime);
		try {
			cfg.save(file);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		String uuid = p.getUniqueId().toString().replace("-", "");
		File file = new File("plugins/RCUSS/ptimecache.yml");
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		long oldts = cfg.getLong(uuid);
		long newts = (System.currentTimeMillis() / 1000);
		long diffts = (newts - oldts);
		long newptime = (diffts + getPlayTime(p));
		setPlayTime(p, newptime);
	}
	
	static int random(int low, int max) {
		Random r = new Random();
		int number = r.nextInt(max);
		while(number < low) {
			number = r.nextInt(max);
		}
		return number;
	}

}