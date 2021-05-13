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
					
					clearUserChat(10, p);
					
					api.sendMSGReady(p, "cmd.cc.private");
				}else if(args[0].equalsIgnoreCase("public")) {
					if(p.hasPermission("mlps.clearchat")) {
						
						clearPublicChat(7);
						
						api.sendMSGReady(p, "cmd.cc.public");
						for(Player all : Bukkit.getOnlinePlayers()) {
							all.sendMessage(api.prefix("main") + api.returnStringReady(all, "cmd.cc.forall.public").replace("%displayer", p.getDisplayName()));
						}
					}else {
						api.noPerm(p);
					}
				}else if(args[0].equalsIgnoreCase("anonymous")) {
					if(p.hasPermission("mlps.clearchat")){
						
						clearPublicChat(7);
						
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
	
	private void clearPublicChat(int times){
		for(int i = 0; i < times; i++){
			Bukkit.broadcastMessage("\n \n \n \n \n \n \n \n \n \n");
		}
	}
	
	private void clearUserChat(int times, Player player){
		for(int i = 0; i < times; i++){
			player.sendMessage("\n \n \n \n \n \n \n \n \n \n");
		}
	}
}