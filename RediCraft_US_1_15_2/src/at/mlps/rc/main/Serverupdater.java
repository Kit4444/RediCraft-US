package at.mlps.rc.main;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

import at.mlps.rc.api.APIs;
import at.mlps.rc.mysql.lb.MySQL;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_16_R1.MinecraftServer;

public class Serverupdater {
	
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
		    try {
		    	PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE redicore_serverstats SET ramusage = ?, serverid = ?, currPlayers = ?, maxPlayers = ?, lastupdateTS = ?, lastupdateST = ?, ramavailable = ?, version = ?, tps = ? WHERE servername = ?");
		    	ps.setInt(1, (int) ramusage);
				ps.setString(2, APIs.getServerId());
				ps.setInt(3, players);
				ps.setInt(4, pmax);
				ps.setInt(5, (int) timestamp);
				ps.setString(6, stime);
				ps.setInt(7, (int) ramtotal);
				ps.setString(8, "1.15.2");
				ps.setString(9, tps);
				ps.setString(10, APIs.getServerName());
				ps.executeUpdate();
				ps.close();
				Bukkit.getConsoleSender().sendMessage("§aDB-Stats updated!");
		    }catch (SQLException e) { e.printStackTrace(); Bukkit.getConsoleSender().sendMessage("§cCan't update DB-Stats."); }
		}else {
			Bukkit.getConsoleSender().sendMessage("§cDB is not connected.");
		}
		Serverrestarter();
	}
	
	private static void Serverrestarter() {
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
		}else if(time == 870) {
			Bukkit.broadcastMessage(prefix + " Removing items in §630 seconds§7.");
		}else if(time == 885) {
			Bukkit.broadcastMessage(prefix + " Removing items in §615 seconds§7.");
		}else if(time == 890) {
			Bukkit.broadcastMessage(prefix + " Removing items in §610 seconds§7.");
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

}