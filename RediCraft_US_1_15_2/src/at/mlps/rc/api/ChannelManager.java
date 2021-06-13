package at.mlps.rc.api;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import at.mlps.rc.main.Main;
import at.mlps.rc.mysql.lb.MySQL;

public class ChannelManager implements Listener, CommandExecutor{
	
	public static HashMap<Player, String> playerChannel = new HashMap<>();
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		APIs api = new APIs();
		Player p = e.getPlayer();
		String lang = getLangFromDB(p).replace("en-UK", "public").replace("en-uk", "public").replace("de-de", "german");
		playerChannel.put(p, lang);
		api.sendMSGReady(p, "event.channelmanager.join");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage(Main.consolesend);
		}else {
			Player p = (Player)sender;
			APIs api = new APIs();
			if(args.length == 0) {
				p.sendMessage(api.prefix("main") + api.returnStringReady(p, "usage") + " §7/channel <newChannel|current|list>");
			}else if(args.length == 1) {
				if(args[0].equalsIgnoreCase("list")) {
					StringBuilder sb = new StringBuilder();
					for(String s : fillList()) {
						sb.append(s);
						sb.append(", ");
					}
					String list = sb.toString().substring(0, (sb.toString().length() - 1));
					p.sendMessage(api.prefix("main") + api.returnStringReady(p, "cmd.channel.list").replace("%list", String.valueOf(fillList().size())).replace("%channels", list));
				}else if(args[0].equalsIgnoreCase("current")) {
					p.sendMessage(api.prefix("main") + "aktueller schaneeeel: " + playerChannel.get(p));
				}else {
					String channel = args[0].toLowerCase();
					switch(channel) {
					//case "": break;
					case "german": playerChannel.put(p, "german"); p.sendMessage(api.prefix("main") + api.returnStringReady(p, "cmd.channel.change").replace("%channel", "German")); break;
					case "public": playerChannel.put(p, "public"); p.sendMessage(api.prefix("main") + api.returnStringReady(p, "cmd.channel.change").replace("%channel", "Public")); break;
					default: playerChannel.put(p, "en-UK"); p.sendMessage(api.prefix("main") + api.returnStringReady(p, "cmd.channel.change").replace("%channel", "Public")); break;
					}
				}
			}
		}
		return false;
	}
	
	List<String> fillList() {
		List<String> channels = new ArrayList<>();
		channels.add("german");
		channels.add("public");
		return channels;
	}
	
	String getLangFromDB(Player p){
		String s = "";
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT language FROM redicore_userstats WHERE uuid = ?");
			ps.setString(1, p.getUniqueId().toString().replace("-", ""));
			ResultSet rs = ps.executeQuery();
			rs.next();
			s = rs.getString("language");
			rs.close();
			ps.close();
		} catch (SQLException e) {
			s = "public";
		}
		return s;
	}
	
}