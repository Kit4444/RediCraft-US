package at.mlps.rc.cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import at.mlps.rc.api.APIs;
import at.mlps.rc.main.Main;

public class Dynmap_CMD implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage(Main.consolesend);
		}else {
			Player p = (Player)sender;
			String server = APIs.getServerName();
			if(server.equalsIgnoreCase("Creative")) {
				p.sendMessage(Main.prefix() + "§aURL§7: creativemap.redicraft.eu");
			}else if(server.equalsIgnoreCase("Survival")) {
				p.sendMessage(Main.prefix() + "§aURL§7: survivalmap.redicraft.eu");
			}else if(server.equalsIgnoreCase("Farmserver")) {
				p.sendMessage(Main.prefix() + "§aURL§7: farmmap.redicraft.eu");
			}else if(server.equalsIgnoreCase("Towny")) {
				p.sendMessage(Main.prefix() + "§aURL§7: townymap.redicraft.eu");
			}else if(server.equalsIgnoreCase("SkyBlock")) {
				p.sendMessage(Main.prefix() + "§aURL§7: skyblockmap.redicraft.eu");
			}else {
				p.sendMessage(Main.prefix() + APIs.returnStringReady(p, "cmd.onlinemap.invalidserver"));
			}
		}
		return true;
	}
}