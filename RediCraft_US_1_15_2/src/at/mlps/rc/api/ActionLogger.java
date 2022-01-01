package at.mlps.rc.api;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.plotsquared.core.annotations.annotations.NotNull;

import at.mlps.rc.mysql.lb.MySQL;

public class ActionLogger {
	
	public static void log(@NotNull String server, @NotNull Player p, @NotNull String action) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy - HH:mm:ss");
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("INSERT INTO redicore_actionlog(uuid, server, dateTime, actionType) VALUES (?, ?, ?, ?)");
			ps.setString(1, p.getUniqueId().toString());
			ps.setString(2, server);
			ps.setString(3, sdf.format(new Date()));
			ps.setString(4, action);
			ps.executeUpdate();
		} catch (SQLException e) {
		}
	}
	
	public static void log(@NotNull String server, @NotNull String issuer, @NotNull String action) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy - HH:mm:ss");
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("INSERT INTO redicore_actionlog(uuid, server, dateTime, actionType) VALUES (?, ?, ?, ?)");
			ps.setString(1, issuer);
			ps.setString(2, server);
			ps.setString(3, sdf.format(new Date()));
			ps.setString(4, action);
			ps.executeUpdate();
		} catch (SQLException e) {
		}
	}
	
	public static void logwithCoords(@NotNull String server, @NotNull Player p, @NotNull String action) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy - HH:mm:ss");
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("INSERT INTO redicore_actionlog(uuid, server, dateTime, actionType, x, y, z, world) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
			ps.setString(1, p.getUniqueId().toString());
			ps.setString(2, server);
			ps.setString(3, sdf.format(new Date()));
			ps.setString(4, action);
			ps.setInt(5, p.getLocation().getBlockX());
			ps.setInt(6, p.getLocation().getBlockY());
			ps.setInt(7, p.getLocation().getBlockZ());
			ps.setString(8, p.getLocation().getWorld().getName());
			ps.executeUpdate();
		} catch (SQLException e) {
		}
	}
	
	public static void logwithBCoords(@NotNull String server, @NotNull Player p, @NotNull Location loc, @NotNull String action) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy - HH:mm:ss");
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("INSERT INTO redicore_actionlog(uuid, server, dateTime, actionType, x, y, z, world) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
			ps.setString(1, p.getUniqueId().toString());
			ps.setString(2, server);
			ps.setString(3, sdf.format(new Date()));
			ps.setString(4, action);
			ps.setInt(5, loc.getBlockX());
			ps.setInt(6, loc.getBlockY());
			ps.setInt(7, loc.getBlockZ());
			ps.setString(8, p.getLocation().getWorld().getName());
			ps.executeUpdate();
		} catch (SQLException e) {
		}
	}
}