package at.mlps.rc.cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import at.mlps.rc.api.APIs;
import at.mlps.rc.main.Main;

public class PingCMD implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage(Main.consolesend);
		}else {
			Player p = (Player)sender;
			int ping = ((CraftPlayer)p).getHandle().ping;
			if(args.length == 0) {
				p.sendMessage(Main.prefix() + APIs.returnStringReady(p, "usage") + "§7 /ping <me|Player|all>");
			}else if(args.length == 1) {
				if(args[0].equalsIgnoreCase("me")) {
					p.sendMessage(Main.prefix() + APIs.returnStringReady(p, "cmd.ping.own").replace("%ping", String.valueOf(ping)));
				}else if(args[0].equalsIgnoreCase("all")) {
					for(Player all : Bukkit.getOnlinePlayers()) {
						int ping1 = ((CraftPlayer)all).getHandle().ping;
						p.sendMessage(Main.prefix() + APIs.returnStringReady(p, "cmd.ping.all").replace("%displayer", all.getDisplayName()).replace("%ping", String.valueOf(ping1)));
					}
				}else {
					Player p2 = Bukkit.getPlayerExact(args[0]);
					if(p2 == null) {
						APIs.sendMSGReady(p, "cmd.ping.notonline");
					}else {
						int ping2 = ((CraftPlayer)p2).getHandle().ping;
						p.sendMessage(Main.prefix() + APIs.returnStringReady(p, "cmd.ping.other").replace("%displayer", p2.getDisplayName()).replace("%ping", String.valueOf(ping2)));
					}
				}
			}else {
				p.sendMessage(Main.prefix() + APIs.returnStringReady(p, "usage") + "§7 /ping <me|Player|all>");
			}
		}
		return false;
	}
}