package at.mlps.rc.cmd;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import at.mlps.rc.api.APIs;
import at.mlps.rc.main.Main;

public class setspawn implements CommandExecutor{
	
	File file = new File("plugins/RCUSS/spawn.yml");

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage(Main.consolesend);
		}else {
			if(!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			Player p = (Player)sender;
			if(args.length == 0) {
				if(p.hasPermission("mlps.admin.setspawn")) {
					p.sendMessage(Main.prefix() + "§7Usage: /setspawn <type>");
					p.sendMessage("§7Default types: plotworld, freebuild, nether, theend");
				}else {
					APIs.noPerm(p);
				}
			}else if(args.length >= 1 && args.length <= 1) {
				if(p.hasPermission("mlps.admin.setspawn")) {
					String type = args[0].toLowerCase();
					YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
					saveCFG(cfg, p, type);
				}else {
					APIs.noPerm(p);
				}
			}
		}
		return true;
	}
	
	private void saveCFG(YamlConfiguration cfg, Player p, String type) {
		SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
		String stime = time.format(new Date());
		SimpleDateFormat date = new SimpleDateFormat("dd.MM.yy");
		String sdate = date.format(new Date());
		Location loc = p.getLocation();
		String dpname = p.getDisplayName();
		String dateformat = stime + " / " + sdate;
		double x = loc.getX();
		double y = loc.getY();
		double z = loc.getZ();
		double pitch = loc.getPitch();
		double yaw = loc.getYaw();
		String world = loc.getWorld().getName().toString();
		cfg.set("Spawn." + type + ".X", x);
		cfg.set("Spawn." + type + ".Y", y);
		cfg.set("Spawn." + type + ".Z", z);
		cfg.set("Spawn." + type + ".YAW", yaw);
		cfg.set("Spawn." + type + ".PITCH", pitch);
		cfg.set("Spawn." + type + ".ADMIN", dpname);
		cfg.set("Spawn." + type + ".DATE", dateformat);
		cfg.set("Spawn." + type + ".WORLD", world);
		try {
			cfg.save(file);
			if(type.equalsIgnoreCase("plotworld")) {
				p.sendMessage(Main.prefix() + "§aA default Spawn has been saved to disk.");
			}else if(type.equalsIgnoreCase("freebuild")) {
				p.sendMessage(Main.prefix() + "§aA default Spawn has been saved to disk.");
			}else if(type.equalsIgnoreCase("nether")) {
				p.sendMessage(Main.prefix() + "§aA default Spawn has been saved to disk.");
			}else if(type.equalsIgnoreCase("theend")) {
				p.sendMessage(Main.prefix() + "§aA default Spawn has been saved to disk.");
			}else {
				p.sendMessage(Main.prefix() + "§aA spawn has been saved to disk.");
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}