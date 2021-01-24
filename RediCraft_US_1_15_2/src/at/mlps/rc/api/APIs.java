package at.mlps.rc.api;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import org.bukkit.Material;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.IOUtils;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import at.mlps.rc.mysql.lb.MySQL;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class APIs {
	
	public static HashMap<String, String> langCache_DE = new HashMap<>();
	public static HashMap<String, String> langCache_EN = new HashMap<>();
	
	public static void loadConfig() {
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redicraft_languagestrings");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				langCache_DE.put(rs.getString("lang_key"), rs.getString("German"));
				langCache_EN.put(rs.getString("lang_key"), rs.getString("English"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void noPerm(Player p) {
		if(retLang(p).equalsIgnoreCase("en-uk")) {
			p.sendMessage(prefix("main") + retString("en-uk", "noPerm"));
		}else if(retLang(p).equalsIgnoreCase("de-de")) {
			p.sendMessage(prefix("main") + retString("de-de", "noPerm"));
		}
	}
	
	public static void notAvailable(Player p) {
		if(retLang(p).equalsIgnoreCase("en-uk")) {
			p.sendMessage(prefix("main") + retString("en-uk", "notAvailable"));
		}else if(retLang(p).equalsIgnoreCase("de-de")) {
			p.sendMessage(prefix("main") + retString("de-de", "notAvailable"));
		}
	}
	
	public static void sendMSGReady(Player p, String path) {
		if(retLang(p).equalsIgnoreCase("en-uk")) {
			p.sendMessage(prefix("main") + retString("en-uk", path));
		}else if(retLang(p).equalsIgnoreCase("de-de")) {
			p.sendMessage(prefix("main") + retString("de-de", path));
		}
	}
	
	public static String returnStringReady(Player p, String path) {
		String s = "";
		if(retLang(p).equalsIgnoreCase("en-uk")) {
			s = retString("en-uk", path);
		}else if(retLang(p).equalsIgnoreCase("de-de")) {
			s = retString("de-de", path);
		}
		return s;
	}
	
	private static String retLang(Player p) {
		String langKey = "en-UK";
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redicore_userstats WHERE uuid = ?");
			ps.setString(1, p.getUniqueId().toString().replace("-", ""));
			ResultSet rs = ps.executeQuery();
			rs.next();
			langKey = rs.getString("language");
		}catch (SQLException e) { e.printStackTrace(); return null; }
		return langKey;
	}

	private static String retString(String lang, String path) {
		
		String string = "";
		if(lang.equalsIgnoreCase("en-uk")) {
			if(langCache_EN.containsKey(path)) {
				string = langCache_EN.get(path).replace("&", "§");
			}else {
				string = "§cThis path doesn't exists.";
			}
		}else if(lang.equalsIgnoreCase("de-de")) {
			if(langCache_DE.containsKey(path)) {
				string = langCache_DE.get(path).replace("&", "§");
			}else {
				string = "§cDieser Pfad existiert nicht.";
			}
		}
		return string;
	}
	
	static File file = new File("server.properties");
	
	public static String getServerName() {
		Properties p = new Properties();
		String s = "";
		try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))){
			p.load(bis);
		}catch (Exception ex) {
			s = "null";
		}
		s = p.getProperty("server-name");
		return s;
	}
	
	public static String getServerId() {
		Properties p = new Properties();
		String s = "";
		try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))){
			p.load(bis);
		}catch (Exception ex) {
			s = "null";
		}
		s = p.getProperty("server-id");
		return s;
	}
	
	public static int getPlayers(String server, String type) {
		int i = 0;
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redicore_serverstats WHERE servername = ?");
			ps.setString(1, server);
			ResultSet rs = ps.executeQuery();
			rs.next();
			i = rs.getInt(type);
		}catch (SQLException e) { e.printStackTrace(); }
		return i;
	}
	
	public static String getUUIDfromName(String name) {
		String url = "https://api.mojang.com/users/profiles/minecraft/" + name;
		String uuid = "";
		try {
			@SuppressWarnings("deprecation")
			String UUIDJson = IOUtils.toString(new URL(url));
			if(UUIDJson.isEmpty()) return "ERRORED";
			JSONObject UUIDObject = (JSONObject) JSONValue.parseWithException(UUIDJson);
			uuid =  UUIDObject.get("id").toString();
		}catch(IOException|ParseException e) {
			uuid = "ERRORED";
		}
		return uuid;
	}
	
	public static String getNamefromUUID(String uuid) {
		String url = "https://api.mojang.com/user/profiles/" + uuid.replace("-", "") + "/names";
		String name = "";
		try {
			@SuppressWarnings("deprecation")
			String nameJson = IOUtils.toString(new URL(url));
			JSONArray nameVal = (JSONArray) JSONValue.parseWithException(nameJson);
			String playerSlot = nameVal.get(nameVal.size()-1).toString();
			JSONObject UUIDObject = (JSONObject) JSONValue.parseWithException(playerSlot);
			name =  UUIDObject.get("name").toString();
		}catch(IOException|ParseException e) {
			name = "ERRORED";
		}
		return name;
	}
	
	public static HashMap<String, String> prefix = new HashMap<>();
	
	public static void onLoad() {
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redicore_igprefix");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				prefix.put(rs.getString("type"), rs.getString("prefix"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public static String prefix(String type) {
		String s = "";
		if(type.equalsIgnoreCase("main") || type.equalsIgnoreCase("prefix")) {
			s = prefix.get("main");
		}else if(type.equalsIgnoreCase("scoreboard")) {
			s = prefix.get("scoreboard");
		}else if(type.equalsIgnoreCase("pmsystem") || type.equalsIgnoreCase("pm")) {
			s = prefix.get("pmsys");
		}
		return s;
	}
	
	public static ItemStack defItem(Material mat, int avg, String dpname) {
		ItemStack is = new ItemStack(mat, avg);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(dpname);
		is.setItemMeta(im);
		return is;
	}
	
	public static ItemStack potionItem(int avg, PotionType effect, String dpname) {
		ItemStack item = new ItemStack(Material.POTION, avg);
		PotionMeta potion = (PotionMeta) item.getItemMeta();
		potion.setBasePotionData(new PotionData(effect, false, false));
		potion.setDisplayName(dpname);
		item.setItemMeta(potion);
		return item;
	}
	
	public static ItemStack enchItem(Material mat, int avg, String dpname, Enchantment ench) {
		ItemStack item = new ItemStack(mat, avg);
		ItemMeta mitem = item.getItemMeta();
		mitem.setDisplayName(dpname);
		mitem.addEnchant(ench, 1, true);
		item.setItemMeta(mitem);
		return item;
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack skullItem(int avg, String dpname, String skullowner) {
		ItemStack skull = new ItemStack(Material.PLAYER_HEAD, avg);
		SkullMeta skullmeta = (SkullMeta) skull.getItemMeta();
		skullmeta.setOwner(skullowner);
		skullmeta.setDisplayName(dpname);
		skull.setItemMeta(skullmeta);
		return skull;
	}
	
	public static ItemStack l2Item(Material mat, int avg, String dpname, String lore1, String lore2) {
	    ArrayList<String> lore = new ArrayList<String>();
	    ItemStack item = new ItemStack(mat, avg);
	    ItemMeta mitem = item.getItemMeta();
	    lore.add(lore1);
	    lore.add(lore2);
	    mitem.setLore(lore);
	    mitem.setDisplayName(dpname);
	    item.setItemMeta(mitem);
	    return item;
	  }
	
	@Deprecated
	public static ItemStack onlineItem(Material mat, int avg, String dpname, int online) {
		ArrayList<String> lore = new ArrayList<>();
		ItemStack item = new ItemStack(mat, avg);
		ItemMeta mitem = item.getItemMeta();
		lore.add("§aOnline§7: " + online);
		mitem.setLore(lore);
		mitem.setDisplayName(dpname);
		item.setItemMeta(mitem);
		return item;
	}
	
	public static ItemStack naviItem(Material mat, String dpname, String servername) {
		ArrayList<String> lore = new ArrayList<>();
		ItemStack item = new ItemStack(mat, 1);
		ItemMeta mitem = item.getItemMeta();
		boolean online = getData(servername, "online");
		boolean locked = getData(servername, "locked");
		boolean monitor = getData(servername, "monitoring");
		if(online){
			lore.add("§7Online: §ayes");
			lore.add("§7Online: §a" + getPlayers(servername) + " §7Players");
		}else {
			lore.add("§7Online: §cno");
		}
		if(locked) {
			lore.add("§7Locked: §cyes");
		}
		if(monitor) {
			lore.add("§7Monitoring: §cyes");
		}
		mitem.setLore(lore);
		mitem.setDisplayName(dpname);
		item.setItemMeta(mitem);
		return item;
	}
	
	private static boolean getData(String server, String column) {
		boolean boo = false;
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redicore_serverstats WHERE servername = ?");
			ps.setString(1, server);
			ResultSet rs = ps.executeQuery();
			rs.next();
			boo = rs.getBoolean(column);
			ps.close();
			rs.close();
		}catch (SQLException e) { e.printStackTrace(); }
		return boo;
	}
	
	private static int getPlayers(String server) {
		int i = 0;
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redicore_serverstats WHERE servername = ?");
			ps.setString(1, server);
			ResultSet rs = ps.executeQuery();
			rs.next();
			i = rs.getInt("currPlayers");
			ps.close();
			rs.close();
		}catch (SQLException e) { e.printStackTrace(); }
		return i;
	}
	
	public static void sendHotbarMessage(Player p, String message) {
		p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
	}
}