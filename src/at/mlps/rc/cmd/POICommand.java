package at.mlps.rc.cmd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.dynmap.DynmapCommonAPI;
import org.dynmap.DynmapCore;
import org.dynmap.markers.MarkerAPI;

import at.mlps.rc.api.APIs;
import at.mlps.rc.mysql.lb.MySQL;

public class POICommand implements CommandExecutor {

    // /poi <add|remove|list> <mArg Desc(add only)>

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {

        } else {
            Player p = (Player) sender;
            APIs api = new APIs();
            if (args.length == 0) {
                p.sendMessage(api.prefix("main") + api.returnStringReady(p, "usage")
                        + "§7/poi <add|remove|list> <Description|ID> | §aDescription is only for Add; Remove thru the ID");
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("list")) {
                    if (p.hasPermission("mlps.poi.list")) {
                        String serverName = api.getServerName();
                        try {
                            PreparedStatement ps = MySQL.getConnection()
                                    .prepareStatement("SELECT * FROM redicore_poi WHERE server = ?");
                            ps.setString(1, serverName);
                            ResultSet rs = ps.executeQuery();
                            int pois = 0;
                            while (rs.next()) {
                                pois++;
                                p.sendMessage("§a" + rs.getInt("id") + " §7| §f" + rs.getString("description"));
                            }
                            p.sendMessage("§7Listed " + pois + " POIs");
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else {
                        api.noPerm(p);
                    }
                } else if (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("remove")) {
                    p.sendMessage(api.prefix("main") + "§cNot enough Parameters provided.");
                }
            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("remove")) {
                    if (p.hasPermission("mlps.poi.remove")) {
                        String oldId = args[1];
                        int id = 0;
                        if (oldId.matches("^[0-9]+$")) {
                            id = Integer.parseInt(oldId);
                            try {
                                PreparedStatement ps = MySQL.getConnection()
                                        .prepareStatement("DELETE FROM redicore_poi WHERE id = ? AND server = ?");
                                ps.setInt(1, id);
                                ps.setString(2, api.getServerName());
                                ps.executeUpdate();
                                p.sendMessage(
                                        api.prefix("main") + "§7The POI with the ID §a" + id + " §7has been removed.");
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        } else {
                            p.sendMessage(api.prefix("main") + "§cThe ID must be alphanumeric only.");
                        }
                    } else {
                        api.noPerm(p);
                    }
                }
            } else {
                if (args[0].equalsIgnoreCase("add")) {
                    if (p.hasPermission("mlps.poi.add")) {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 1; i < args.length; i++) {
                            sb.append(args[i]).append(" ");
                        }
                        String msg = sb.toString().trim();
                        Location loc = p.getLocation();
                        try {
                            PreparedStatement ps = MySQL.getConnection().prepareStatement(
                                    "INSERT INTO redicore_poi(server, world, x, y, z, description) VALUES (?, ?, ?, ?, ?, ?)");
                            ps.setString(1, api.getServerName());
                            ps.setString(2, loc.getWorld().getName());
                            ps.setInt(3, loc.getBlockX());
                            ps.setInt(4, loc.getBlockY());
                            ps.setInt(5, loc.getBlockZ());
                            ps.setString(6, msg);
                            ps.executeUpdate();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else {
                        api.noPerm(p);
                    }
                }
            }
        }
        return false;
    }

    public void updateMarkers(){
        DynmapCommonAPI dapi = new DynmapCore();
        MarkerAPI mapi = dapi.getMarkerAPI();
        mapi.createMarkerSet(arg0, arg1, arg2, arg3)
    }
}