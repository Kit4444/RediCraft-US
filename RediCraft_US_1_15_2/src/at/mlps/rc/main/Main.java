package at.mlps.rc.main;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import at.mlps.rc.api.APIs;
import at.mlps.rc.event.ScoreboardCLS;
import at.mlps.rc.mysql.lpb.MySQL;
import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin implements Listener{
	
	public static String mysqlprefix = "§eMySQL §7- ";
	public static MySQL mysql;
	public static String consolesend = prefix() + "§cPlease use this command ingame!";
	public static ArrayList<String> serverlist = new ArrayList<>();
	public static Main instance;
	static File file = new File("plugins/RCUSS");
	static File msgf = new File("plugins/RCUSS/msg.yml");
	static File pdata = new File("plugins/RCUSS/playerdata.yml");
	public static String prefix() {
		return APIs.prefix("main");
	}
	
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
		regDB();
		UpdateOnline(true);
		registerMisc();
		Manager man = new Manager();
		man.init();
		if(Bukkit.getPluginManager().getPlugin("Vault") != null) {
			Bukkit.getServicesManager().register(Economy.class, new Vault(), Bukkit.getPluginManager().getPlugin("Vault"), ServicePriority.Normal);
		}
		Bukkit.getConsoleSender().sendMessage(prefix() + "§aPlugin wurde geladen.");
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
		Bukkit.getConsoleSender().sendMessage(prefix() + "§cPlugin wurde gestoppt");
	}
	
	private void registerMisc() {
		fillList();
		APIs.loadConfig();
		ScoreboardCLS sb = new ScoreboardCLS();
		sb.sbSched(0, 100, 20);
	}
	
	private void regDB() {
		File config = new File("plugins/RCUSS/config.yml");
		if(!config.exists()) {
			try {
				config.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(config);
		cfg.addDefault("MySQL.Host", "localhost");
		cfg.addDefault("MySQL.Port", 3306);
		cfg.addDefault("MySQL.Database", "database");
		cfg.addDefault("MySQL.Username", "username");
		cfg.addDefault("MySQL.Password", "password");
		cfg.options().copyDefaults(true);
		try {
			cfg.save(config);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		String host = cfg.getString("MySQL.Host");
		int port = cfg.getInt("MySQL.Port");
		String db = cfg.getString("MySQL.Database");
		String user = cfg.getString("MySQL.Username");
		String pass = cfg.getString("MySQL.Password");
		at.mlps.rc.mysql.lb.MySQL.connect(host, String.valueOf(port), db, user, pass);
		mysql = new MySQL(host, port, db, user, pass);
		try {
			mysql.connect();
		}catch (SQLException e) {}
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
			ps.setString(2, APIs.getServerName());
			ps.executeUpdate();
			ps.closeOnCompletion();
		}catch (SQLException e) {
			
		}
	}
}