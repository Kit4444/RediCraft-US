package at.mlps.rc.cmd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import at.mlps.rc.api.APIs;
import at.mlps.rc.api.ActionLogger;
import at.mlps.rc.main.Main;
import at.mlps.rc.mysql.lb.MySQL;

public class CMD_SetID_SetPf implements CommandExecutor{
	
	int minid = 0;
	int maxid = 99999;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage(Main.consolesend);
		}else {
			Player p = (Player)sender;
			APIs api = new APIs();
			if(cmd.getName().equalsIgnoreCase("setid")) {
				if(args.length == 0) {
					p.sendMessage(api.prefix("main") + api.returnStringReady(p, "usage") + "§7/setid <Player> <ID>");
				}else if(args.length >= 1 && args.length <= 2) {
					if(p.hasPermission("mlps.setID")) {
						Player p2 = Bukkit.getPlayerExact(args[0]);
						if(p2 == null) {
							p.sendMessage(api.prefix("main") + api.returnStringReady(p, "cmd.setid.playernotonline").replace("%displayer", args[0]));
						}else {
							int id = Integer.parseInt(args[1]);
							if(p.hasPermission("mlps.setID.exceedLimit")) {
								try {
									PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT userid FROM redicore_userstats WHERE userid = ?");
									ps.setInt(1, id);
									ResultSet rs = ps.executeQuery();
									rs.next();
									int mid = rs.getInt("userid");
									if(mid == id) {
										api.sendMSGReady(p, "cmd.setid.idalreadyexists");
									}
									rs.close();
									ps.close();
								}catch (SQLException e) {
									try {
										PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE redicore_userstats SET userid = ? WHERE uuid = ?");
										ps.setInt(1, id);
										ps.setString(2, p2.getUniqueId().toString().replace("-", ""));
										ps.executeUpdate();
										ps.close();
										p.sendMessage(api.prefix("main") + api.returnStringReady(p, "cmd.setid.updatesuccessfull").replace("%displayer", p2.getDisplayName()).replace("%id", String.valueOf(id)));
										ActionLogger.log(api.getServerName(), p, "Player executed setid command for " + p2.getName() + "!");
									}catch (SQLException e1) {
										e1.printStackTrace();
									}
								}
							}else {
								if(id >= minid && id <= maxid) {
									try {
										PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT userid FROM redicore_userstats WHERE userid = ?");
										ps.setInt(1, id);
										ResultSet rs = ps.executeQuery();
										rs.next();
										int mid = rs.getInt("userid");
										if(mid == id) {
											api.sendMSGReady(p, "cmd.setid.idalreadyexists");
										}
										rs.close();
										ps.close();
									}catch (SQLException e) {
										try {
											PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE redicore_userstats SET userid = ? WHERE uuid = ?");
											ps.setInt(1, id);
											ps.setString(2, p2.getUniqueId().toString().replace("-", ""));
											ps.executeUpdate();
											ps.close();
											p.sendMessage(api.prefix("main") + api.returnStringReady(p, "cmd.setid.updatesuccessfull").replace("%displayer", p2.getDisplayName()).replace("%id", String.valueOf(id)));
											ActionLogger.log(api.getServerName(), p, "Player executed setid command for " + p2.getName() + "!");
										}catch (SQLException e1) {
											e1.printStackTrace();
										}
									}
								}else {
									p.sendMessage(api.prefix("main") + api.returnStringReady(p, "cmd.setid.limitexceeded").replace("%minid", String.valueOf(minid)).replace("%maxid", String.valueOf(maxid)));
									ActionLogger.log(api.getServerName(), p, "Player attempted to exceed the id-limitation.");
								}
							}
						}
					}else {
						api.noPerm(p);
						ActionLogger.log(api.getServerName(), p, "Player attempted to change the id.");
					}
				}else {
					p.sendMessage(api.prefix("main") + api.returnStringReady(p, "usage") + "§7/setid <Player> <ID>");
				}
			}else if(cmd.getName().equalsIgnoreCase("setpf")) {
				if(args.length == 0) {
					p.sendMessage(api.prefix("main") + api.returnStringReady(p, "usage") + "§7 /setpf <Player> <Prefix>");
				}else if(args.length >= 1) {
					if(p.hasPermission("mlps.setPF")) {
						StringBuilder sb = new StringBuilder();
						for(int i = 1; i < args.length; i++) {
							sb.append(args[i]).append(" ");
						}
						String reason = sb.toString().trim();
						Player p2 = Bukkit.getPlayerExact(args[0]);
						if(p2 == null) {
							p.sendMessage(api.prefix("main") + api.returnStringReady(p, "cmd.setpf.playernotonline").replace("%displayer", args[0]));
						}else {
							String uuid2 = p2.getUniqueId().toString().replace("-", "");
							String prefix = ChatColor.translateAlternateColorCodes('&', reason);
							if(prefix.length() <= 16) {
								if(prefix.equalsIgnoreCase("reset")) {
									try {
										PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE redicore_userstats SET userprefix = ?, userprefix_ncc = ? WHERE uuid = ?");
										ps.setString(1, "RESET");
										ps.setString(2, "RESET");
										ps.setString(3, uuid2);
										ps.executeUpdate();
										ps.close();
										p.sendMessage(api.prefix("main") + api.returnStringReady(p, "cmd.setpf.playerreset").replace("%displayer", p2.getDisplayName()));
										ActionLogger.log(api.getServerName(), p, "Player resetted prefix for " + p2.getName());
									}catch (SQLException e) { }
								}else {
									try {
										PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE redicore_userstats SET userprefix = ?, userprefix_ncc = ? WHERE uuid = ?");
										ps.setString(1, prefix);
										ps.setString(2, prefix.replace("§a", "").replace("§b", "").replace("§c", "").replace("§d", "").replace("§e", "").replace("§f", "").replace("§1", "").replace("§2", "").replace("§3", "").replace("§4", "").replace("§5", "").replace("§6", "").replace("§7", "").replace("§8", "").replace("§9", "").replace("§0", ""));
										ps.setString(3, uuid2);
										ps.executeUpdate();
										ps.close();
										p.sendMessage(api.prefix("main") + api.returnStringReady(p, "cmd.setpf.newprefix").replace("%displayer", p2.getDisplayName()).replace("%prefix", prefix).replace("|", "\n"));
										ActionLogger.log(api.getServerName(), p, "Player changed prefix for " + p2.getName());
									}catch (SQLException e) { }
								}
							}else {
								p.sendMessage(api.prefix("main") + api.returnStringReady(p, "cmd.setpf.prefixtoolong").replace("%length", String.valueOf(prefix.length())));
								ActionLogger.log(api.getServerName(), p, "Player attempted to exceed max charlimit for prefix.");
							}
						}
					}else {
						api.noPerm(p);
						ActionLogger.log(api.getServerName(), p, "Player attempted to change prefix.");
					}
				}
			}
		}
		return false;
	}
}