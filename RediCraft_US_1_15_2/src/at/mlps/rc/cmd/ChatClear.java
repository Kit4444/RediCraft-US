package at.mlps.rc.cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import at.mlps.rc.api.APIs;
import at.mlps.rc.main.Main;

public class ChatClear implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage(Main.consolesend);
		}else {
			Player p = (Player)sender;
			APIs api = new APIs();
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase("private")) {
					p.sendMessage("\n \n \n \n \n \n \n \n \n \n");
					p.sendMessage("\n \n \n \n \n \n \n \n \n \n");
					p.sendMessage("\n \n \n \n \n \n \n \n \n \n");
					p.sendMessage("\n \n \n \n \n \n \n \n \n \n");
					p.sendMessage("\n \n \n \n \n \n \n \n \n \n");
					p.sendMessage("\n \n \n \n \n \n \n \n \n \n");
					p.sendMessage("\n \n \n \n \n \n \n \n \n \n");
					p.sendMessage("\n \n \n \n \n \n \n \n \n \n");
					p.sendMessage("\n \n \n \n \n \n \n \n \n \n");
					p.sendMessage("\n \n \n \n \n \n \n \n \n \n");
					api.sendMSGReady(p, "cmd.cc.private");
				}else if(args[0].equalsIgnoreCase("public")) {
					if(p.hasPermission("mlps.clearchat")) {
						Bukkit.broadcastMessage("\n \n \n \n \n \n \n \n \n \n");
						Bukkit.broadcastMessage("\n \n \n \n \n \n \n \n \n \n");
						Bukkit.broadcastMessage("\n \n \n \n \n \n \n \n \n \n");
						Bukkit.broadcastMessage("\n \n \n \n \n \n \n \n \n \n");
						Bukkit.broadcastMessage("\n \n \n \n \n \n \n \n \n \n");
						Bukkit.broadcastMessage("\n \n \n \n \n \n \n \n \n \n");
						Bukkit.broadcastMessage("\n \n \n \n \n \n \n \n \n \n");
						Bukkit.broadcastMessage("\n \n \n \n \n \n \n \n \n \n");
						Bukkit.broadcastMessage("\n \n \n \n \n \n \n \n \n \n");
						Bukkit.broadcastMessage("\n \n \n \n \n \n \n \n \n \n");
						api.sendMSGReady(p, "cmd.cc.public");
						for(Player all : Bukkit.getOnlinePlayers()) {
							all.sendMessage(api.prefix("main") + api.returnStringReady(all, "cmd.cc.forall.public").replace("%displayer", p.getDisplayName()));
						}
					}else {
						api.noPerm(p);
					}
				}else if(args[0].equalsIgnoreCase("anonymous")) {
					if(p.hasPermission("mlps.clearchat")) {
						Bukkit.broadcastMessage("\n \n \n \n \n \n \n \n \n \n");
						Bukkit.broadcastMessage("\n \n \n \n \n \n \n \n \n \n");
						Bukkit.broadcastMessage("\n \n \n \n \n \n \n \n \n \n");
						Bukkit.broadcastMessage("\n \n \n \n \n \n \n \n \n \n");
						Bukkit.broadcastMessage("\n \n \n \n \n \n \n \n \n \n");
						Bukkit.broadcastMessage("\n \n \n \n \n \n \n \n \n \n");
						Bukkit.broadcastMessage("\n \n \n \n \n \n \n \n \n \n");
						Bukkit.broadcastMessage("\n \n \n \n \n \n \n \n \n \n");
						Bukkit.broadcastMessage("\n \n \n \n \n \n \n \n \n \n");
						Bukkit.broadcastMessage("\n \n \n \n \n \n \n \n \n \n");
						api.sendMSGReady(p, "cmd.cc.anonymous");
						for(Player all : Bukkit.getOnlinePlayers()) {
							api.sendMSGReady(all, "cmd.cc.forall.anonymous");
						}
					}else {
						api.noPerm(p);
					}
				}else {
					p.sendMessage(api.prefix("main") + api.returnStringReady(p, "usage") + "§7 /cc <private|public|anonymous>");
				}
			}else {
				p.sendMessage(api.prefix("main") + api.returnStringReady(p, "usage") + "§7 /cc <private|public|anonymous>");
			}
		}
		return false;
	}
}