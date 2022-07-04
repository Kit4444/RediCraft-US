package at.kitsoft.redicraft.cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import at.kitsoft.redicraft.api.APIs;
import at.kitsoft.redicraft.api.ActionLogger;
import at.kitsoft.redicraft.main.Main;

public class Dynmap_CMD implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage(Main.consolesend);
		}else {
			APIs api = new APIs();
			Player p = (Player)sender;
			String server = api.getServerName();
			String mapname = p.getLocation().getWorld().getName().toLowerCase();
			int x = p.getLocation().getBlockX();
			int y = p.getLocation().getBlockY();
			int z = p.getLocation().getBlockZ();
			String url = "/?worldname=" + mapname + "&mapname=flat&zoom=6&x=" + x + "&y=" + y + "&z=" + z;
			if(server.equalsIgnoreCase("Creative")) {
				p.sendMessage(api.prefix("main") + "§aURL§7: http://map.redicraft.eu:21201" + url);
			}else if(server.equalsIgnoreCase("Survival")) {
				p.sendMessage(api.prefix("main") + "§aURL§7: http://map.redicraft.eu:21203" + url);
			}else if(server.equalsIgnoreCase("Farmserver")) {
				p.sendMessage(api.prefix("main") + "§aURL§7: http://map.redicraft.eu:21205" + url);
			}else if(server.equalsIgnoreCase("SkyBlock")) {
				p.sendMessage(api.prefix("main") + "§aURL§7: http://map.redicraft.eu:21204" + url);
			}else {
				p.sendMessage(api.prefix("main") + api.returnStringReady(p, "cmd.onlinemap.invalidserver"));
			}
			ActionLogger.log(api.getServerName(), p, "Player executed onlinemap command.");
		}
		return true;
	}
}