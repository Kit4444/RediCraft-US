package at.mlps.rc.cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import at.mlps.rc.api.APIs;
import at.mlps.rc.main.Main;

public class TimeCMD implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage(Main.consolesend);
		}else {
			Player p = (Player)sender;
			if(args.length == 0) {
				p.sendMessage(Main.prefix() + "§7Usage: /time <set|add|remove|info> [time]");
			}else if(args.length == 1) {
				if(args[0].equalsIgnoreCase("info")) {
					p.sendMessage(Main.prefix() + APIs.returnStringReady(p, "cmd.time.info").replace("%world", p.getWorld().getName()).replace("%time", String.valueOf(p.getWorld().getTime())));
				}
			}else if(args.length == 2) {
				if(args[0].equalsIgnoreCase("set")) {
					if(p.hasPermission("mlps.setTime")) {
						if(args[1].equalsIgnoreCase("day")) {
							p.getWorld().setTime(0);
							p.sendMessage(Main.prefix() + APIs.returnStringReady(p, "cmd.time.set").replace("%world", p.getWorld().getName()).replace("%time", String.valueOf(p.getWorld().getTime())));
						}else if(args[1].equalsIgnoreCase("midnight")) {
							p.getWorld().setTime(18000);
							p.sendMessage(Main.prefix() + APIs.returnStringReady(p, "cmd.time.set").replace("%world", p.getWorld().getName()).replace("%time", String.valueOf(p.getWorld().getTime())));
						}else if(args[1].equalsIgnoreCase("night")) {
							p.getWorld().setTime(13000);
							p.sendMessage(Main.prefix() + APIs.returnStringReady(p, "cmd.time.set").replace("%world", p.getWorld().getName()).replace("%time", String.valueOf(p.getWorld().getTime())));
						}else if(args[1].equalsIgnoreCase("noon")) {
							p.getWorld().setTime(6000);
							p.sendMessage(Main.prefix() + APIs.returnStringReady(p, "cmd.time.set").replace("%world", p.getWorld().getName()).replace("%time", String.valueOf(p.getWorld().getTime())));
						}else {
							long time = Long.valueOf(args[1]);
							p.getWorld().setTime(time);
							p.sendMessage(Main.prefix() + APIs.returnStringReady(p, "cmd.time.set").replace("%world", p.getWorld().getName()).replace("%time", String.valueOf(p.getWorld().getTime())));
						}
						
					}else {
						APIs.noPerm(p);
					}
				}else if(args[0].equalsIgnoreCase("remove")) {
					if(p.hasPermission("mlps.setTime")) {
						long ctime = p.getWorld().getTime();
						long time = Long.valueOf(args[1]);
						long newtime = (ctime - time);
						p.getWorld().setTime(newtime);
						p.sendMessage(Main.prefix() + APIs.returnStringReady(p, "cmd.time.set").replace("%world", p.getWorld().getName()).replace("%time", String.valueOf(p.getWorld().getTime())));
					}else {
						APIs.noPerm(p);
					}
				}else if(args[0].equalsIgnoreCase("add")) {
					if(p.hasPermission("mlps.setTime")) {
						long ctime = p.getWorld().getTime();
						long time = Long.valueOf(args[1]);
						long newtime = (ctime + time);
						p.getWorld().setTime(newtime);
						p.sendMessage(Main.prefix() + APIs.returnStringReady(p, "cmd.time.set").replace("%world", p.getWorld().getName()).replace("%time", String.valueOf(p.getWorld().getTime())));
					}else {
						APIs.noPerm(p);
					}
				}
			}
		}
		return false;
	}
}