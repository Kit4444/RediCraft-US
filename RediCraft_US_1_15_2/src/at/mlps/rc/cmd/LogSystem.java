package at.mlps.rc.cmd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import at.mlps.rc.api.APIs;
import at.mlps.rc.main.Main;
import at.mlps.rc.mysql.lb.MySQL;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class LogSystem implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage(Main.consolesend);
		}else {
			Player p = (Player)sender;
			String uuid = p.getUniqueId().toString().replace("-", "");
			if(cmd.getName().equalsIgnoreCase("login")) {
				if(p.hasPermission("mlps.canBan")) {
					if(retStatus(uuid)) {
						APIs.sendMSGReady(p, "cmd.login.already");
					}else {
						APIs.sendMSGReady(p, "cmd.login.success");
						updateStatus(uuid, true);
					}
				}else {
					APIs.noPerm(p);
				}
			}else if(cmd.getName().equalsIgnoreCase("logout")) {
				if(p.hasPermission("mlps.canBan")) {
					if(retStatus(uuid)) {
						APIs.sendMSGReady(p, "cmd.logout.success");
						updateStatus(uuid, false);
					}else {
						APIs.sendMSGReady(p, "cmd.logout.already");
					}
				}else {
					APIs.noPerm(p);
				}
			}else if(cmd.getName().equalsIgnoreCase("tg") || cmd.getName().equalsIgnoreCase("togglegroup")) {
				PermissionUser po = PermissionsEx.getUser(p);
				if(po.inGroup("Patron") || po.inGroup("Beta") || po.inGroup("Mod") || po.inGroup("Support") || po.inGroup("Builder") || po.inGroup("NitroBooster") || po.inGroup("Friend")) {
					if(retStatus(uuid)) {
						updateStatus(uuid, false);
						APIs.sendMSGReady(p, "cmd.tg.invisible");
					}else {
						updateStatus(uuid, true);
						APIs.sendMSGReady(p, "cmd.tg.visible");
					}
				}else {
					APIs.noPerm(p);
				}
			}
		}
		return false;
	}
	
	private boolean retStatus(String uuid) {
		boolean boo = true;
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redicore_userstats WHERE uuid = ?");
			ps.setString(1, uuid);
			ResultSet rs = ps.executeQuery();
			rs.next();
			boo = rs.getBoolean("loggedin");
		}catch (SQLException e) { e.printStackTrace(); }
		return boo;
	}
	
	private void updateStatus(String uuid, boolean boo) {
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE redicore_userstats SET loggedin = ? WHERE uuid = ?");
			ps.setBoolean(1, boo);
			ps.setString(2, uuid);
			ps.executeUpdate();
		}catch (SQLException e) { e.printStackTrace(); }
	}

}
