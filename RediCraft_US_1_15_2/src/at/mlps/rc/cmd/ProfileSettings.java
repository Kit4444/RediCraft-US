package at.mlps.rc.cmd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import at.mlps.rc.api.APIs;
import at.mlps.rc.mysql.lb.MySQL;

public class ProfileSettings implements CommandExecutor, Listener{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			
		}else {
			Player p = (Player)sender;
			openSettingsInv(p);
			p.sendMessage("settings opened");
		}
		return true;
	}
	
	private static void openSettingsInv(Player p) {
		Inventory inv = Bukkit.createInventory(null, 9*3, "§6" + p.getName() + "'s Settings");
		APIs api = new APIs();
		if(getSetting(p, "disablePMs")) {
			inv.setItem(0, api.defItem(Material.RED_CONCRETE_POWDER, 1, "§cPrivate Messages"));
		}else {
			inv.setItem(0, api.defItem(Material.GREEN_CONCRETE_POWDER, 1, "§aPrivate Messages"));
		}
		if(getSetting(p, "disableTPAR")) {
			inv.setItem(1, api.defItem(Material.RED_CONCRETE_POWDER, 1, "§cTeleport Requests"));
		}else {
			inv.setItem(1, api.defItem(Material.GREEN_CONCRETE_POWDER, 1, "§aTeleport Requests"));
		}
		inv.setItem(4, api.defItem(Material.BOOK, 1, "§aLanguage"));
		if(getSetting(p, "showIDdsc")) {
			inv.setItem(9, api.defItem(Material.GREEN_CONCRETE_POWDER, 1, "§aShow ID Discord"));
		}else {
			inv.setItem(9, api.defItem(Material.RED_CONCRETE_POWDER, 1, "§cShow ID Discord"));
		}
		if(getSetting(p, "showRoledsc")) {
			inv.setItem(10, api.defItem(Material.GREEN_CONCRETE_POWDER, 1, "§aShow Role Discord"));
		}else {
			inv.setItem(10, api.defItem(Material.RED_CONCRETE_POWDER, 1, "§cShow Role Discord"));
		}
		if(getSetting(p, "showNickdsc")) {
			inv.setItem(11, api.defItem(Material.GREEN_CONCRETE_POWDER, 1, "§aShow Nick Discord"));
		}else {
			inv.setItem(11, api.defItem(Material.RED_CONCRETE_POWDER, 1, "§cShow Nick Discord"));
		}
		inv.setItem(13, api.defItem(Material.OAK_SIGN, 1, "§aScoreboard"));
		if(p.hasPermission("mlps.canBan")) {
			if(getSetting(p, "loggedin")) {
				inv.setItem(18, api.defItem(Material.GREEN_CONCRETE_POWDER, 1, "§aLogout"));
			}else {
				inv.setItem(18, api.defItem(Material.RED_CONCRETE_POWDER, 1, "§cLogin"));
			}
			if(getSetting(p, "vanishCountinTab")) {
				inv.setItem(22, api.defItem(Material.GREEN_CONCRETE_POWDER, 1, "§aShow Vanish"));
			}else {
				inv.setItem(22, api.defItem(Material.RED_CONCRETE_POWDER, 1, "§cShow Vanish"));
			}
			if(getSetting(p, "vanish")) {
				inv.setItem(23, api.defItem(Material.GREEN_CONCRETE_POWDER, 1, "§aVanish"));
			}else {
				inv.setItem(23, api.defItem(Material.RED_CONCRETE_POWDER, 1, "§cVanish"));
			}
		}
		p.openInventory(inv);
		p.updateInventory();
	}
	
	@EventHandler
	public void invClick(InventoryClickEvent e) {
		Player p = (Player)e.getWhoClicked();
		if(e.getView().getTitle().equalsIgnoreCase("§6" + p.getName() + "'s Settings")) {
			e.setCancelled(true);
			switch(e.getCurrentItem().getItemMeta().getDisplayName()) {
			case "§aPrivate Messages": updateSetting(p, "disablePMs", true); openSettingsInv(p); break;
			case "§cPrivate Messages": updateSetting(p, "disablePMs", false); openSettingsInv(p); break;
			case "§aTeleport Requests": updateSetting(p, "disableTPAR", true); openSettingsInv(p); break;
			case "§cTeleport Requests": updateSetting(p, "disableTPAR", false); openSettingsInv(p); break;
			case "§cShow ID Discord": updateSetting(p, "showIDdsc", true); openSettingsInv(p); break;
			case "§aShow ID Discord": updateSetting(p, "showIDdsc", false); openSettingsInv(p); break;
			case "§cShow Nick Discord": updateSetting(p, "showNickdsc", true); openSettingsInv(p); break;
			case "§aShow Nick Discord": updateSetting(p, "showNickdsc", false); openSettingsInv(p); break;
			case "§cShow Role Discord": updateSetting(p, "showRoledsc", true); openSettingsInv(p); break;
			case "§aShow Role Discord": updateSetting(p, "showRoledsc", false); openSettingsInv(p); break;
			case "§cLogin": updateSetting(p, "loggedin", true); openSettingsInv(p); break;
			case "§aLogout": updateSetting(p, "loggedin", false); openSettingsInv(p); break;
			}
		}
	}
	
	static boolean getSetting(Player p, String setting) {
		boolean boo = false;
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redicore_userstats WHERE uuid = ?");
			ps.setString(1, p.getUniqueId().toString().replace("-", ""));
			ResultSet rs = ps.executeQuery();
			rs.next();
			boo = rs.getBoolean(setting);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return boo;
	}
	
	static void updateSetting(Player p, String setting, boolean newState) {
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE redicore_userstats SET " + setting + " = ? WHERE uuid = ?");
			ps.setBoolean(1, newState);
			ps.setString(2, p.getUniqueId().toString().replace("-", ""));
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}