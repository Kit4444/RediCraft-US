package at.mlps.rc.main;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import at.mlps.rc.api.APIs;
import at.mlps.rc.event.ScoreboardCLS;
import at.mlps.rc.mysql.lpb.MySQL;
import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin implements Listener{
	
	static APIs api = new APIs();
	public static String mysqlprefix = "§eMySQL §7- ";
	public static MySQL mysql;
	public static String consolesend = api.prefix("main") + "§cPlease use this command ingame!";
	public static ArrayList<String> serverlist = new ArrayList<>();
	public static Main instance;
	static File file = new File("plugins/RCUSS");
	static File msgf = new File("plugins/RCUSS/msg.yml");
	static File pdata = new File("plugins/RCUSS/playerdata.yml");
	
	public void onEnable() {
		if(!file.exists()) {
			file.mkdir();
		}
		if(msgf.exists()) {
			msgf.delete();
			try {
				msgf.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			try {
				msgf.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(!pdata.exists()) {
			try {
				pdata.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		instance = this;
		registerMisc();
		Manager man = new Manager();
		man.init();
		if(Bukkit.getPluginManager().getPlugin("Vault") != null) {
			Bukkit.getServicesManager().register(Economy.class, new Vault(), Bukkit.getPluginManager().getPlugin("Vault"), ServicePriority.Normal);
		}
		UpdateOnline(true);
		Bukkit.getConsoleSender().sendMessage(api.prefix("main") + "§aPlugin wurde geladen.");
	}
	
	public void onDisable() {
		UpdateOnline(false);
		instance = null;
		at.mlps.rc.mysql.lb.MySQL.disconnect();
		try {
			mysql.disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Bukkit.getConsoleSender().sendMessage(api.prefix("main") + "§cPlugin wurde gestoppt");
	}
	
	private void registerMisc() {
		fillList();
		ScoreboardCLS sb = new ScoreboardCLS();
		sb.sbSched(0, 50, 20);
	}
	
	private void fillList() {
		serverlist.add("Survival");
		serverlist.add("SkyBlock");
		serverlist.add("Farmserver");
	}
	
	private void UpdateOnline(boolean online) {
		try {
			PreparedStatement ps = at.mlps.rc.mysql.lb.MySQL.getConnection().prepareStatement("UPDATE redicore_serverstats SET online = ? WHERE servername = ?");
			ps.setBoolean(1, online);
			ps.setString(2, api.getServerName());
			ps.executeUpdate();
			ps.closeOnCompletion();
			if(online == true) {
				PreparedStatement ps1 = at.mlps.rc.mysql.lb.MySQL.getConnection().prepareStatement("UPDATE redicore_serverstats SET onlinesince = ? WHERE servername = ?");
				SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
				ps1.setString(1, time.format(new Date()));
				ps1.setString(2, api.getServerName());
				ps1.executeUpdate();
			}
		}catch (SQLException e) { }
	}
}