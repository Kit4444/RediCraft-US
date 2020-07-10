package at.mlps.rc.cmd;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import at.mlps.rc.api.APIs;
import at.mlps.rc.event.ScoreboardCLS;
import at.mlps.rc.main.Main;
import at.mlps.rc.mysql.lb.MySQL;

public class ScoreboardChange implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage(Main.consolesend);
		}else {
			Player p = (Player)sender;
			String uuid = p.getUniqueId().toString().replace("-", "");
			if(args.length == 1) {
				if(Main.serverlist.contains(APIs.getServerName())) {
					if(args[0].equalsIgnoreCase("off")) {
						updateSB(uuid, 0);
						APIs.sendMSGReady(p, "cmd.scoreboard.off");
					}else if(args[0].equalsIgnoreCase("default")) {
						updateSB(uuid, 1);
						APIs.sendMSGReady(p, "cmd.scoreboard.default");
					}else if(args[0].equalsIgnoreCase("job")) {
						updateSB(uuid, 2);
						APIs.sendMSGReady(p, "cmd.scoreboard.job");
					}else if(args[0].equalsIgnoreCase("admin")) {
						if(p.hasPermission("mlps.canBan")) {
							updateSB(uuid, 3);
							APIs.sendMSGReady(p, "cmd.scoreboard.admin");
						}else {
							APIs.noPerm(p);
						}
					}else if(args[0].equalsIgnoreCase("data")) {
						if(p.hasPermission("mlps.isSA")) {
							updateSB(uuid, 4);
							APIs.sendMSGReady(p, "cmd.scoreboard.data");
						}else {
							APIs.noPerm(p);
						}
					}
					ScoreboardCLS sb = new ScoreboardCLS();
					try {
						sb.setScoreboard(p);
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else {
					if(args[0].equalsIgnoreCase("off")) {
						updateSB(uuid, 0);
						APIs.sendMSGReady(p, "cmd.scoreboard.off");
					}else if(args[0].equalsIgnoreCase("default")) {
						updateSB(uuid, 1);
						APIs.sendMSGReady(p, "cmd.scoreboard.default");
					}else if(args[0].equalsIgnoreCase("job")) {
						APIs.sendMSGReady(p, "cmd.scoreboard.creajob");
					}else if(args[0].equalsIgnoreCase("admin")) {
						if(p.hasPermission("mlps.canBan")) {
							updateSB(uuid, 3);
							APIs.sendMSGReady(p, "cmd.scoreboard.admin");
						}else {
							APIs.noPerm(p);
						}
					}else if(args[0].equalsIgnoreCase("data")) {
						if(p.hasPermission("mlps.isSA")) {
							updateSB(uuid, 4);
							APIs.sendMSGReady(p, "cmd.scoreboard.data");
						}else {
							APIs.noPerm(p);
						}
					}
					ScoreboardCLS sb = new ScoreboardCLS();
					try {
						sb.setScoreboard(p);
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}else {
				p.sendMessage(Main.prefix() + APIs.returnStringReady(p, "usage") + "§7/sb <off|default|job|admin|data");
			}
		}
		return false;
	}
	
	private void updateSB(String uuid, int id) {
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE redicore_userstats SET scoreboard = ? WHERE uuid = ?");
			ps.setInt(1, id);
			ps.setString(2, uuid);
			ps.executeUpdate();
			ps.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
}