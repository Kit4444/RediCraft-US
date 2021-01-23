package at.mlps.rc.cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import at.mlps.rc.api.APIs;
import at.mlps.rc.main.Main;

public class WeatherCMD implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage(Main.consolesend);
		}else {
			Player p = (Player)sender;
			if(args.length == 0) {
				p.sendMessage(APIs.prefix("main") + APIs.returnStringReady(p, "usage") + "§7 /weather <clear|rain|thunder> [time in seconds]");
			}else if(args.length == 1) {
				if(args[0].equalsIgnoreCase("clear")) {
					if(p.hasPermission("mlps.setweather")) {
						p.getWorld().setThundering(false);
						p.getWorld().setStorm(false);
						p.sendMessage(APIs.prefix("main") + APIs.returnStringReady(p, "cmd.weather.clear").replace("%world", p.getWorld().getName()));
					}else {
						APIs.noPerm(p);
					}
				}else if(args[0].equalsIgnoreCase("rain")) {
					if(p.hasPermission("mlps.setweather")) {
						p.getWorld().setThundering(false);
						p.getWorld().setStorm(true);
						p.sendMessage(APIs.prefix("main") + APIs.returnStringReady(p, "cmd.weather.notime").replace("%world", p.getWorld().getName()).replace("%weather", "rain").replace("%wetter", "Regen"));
					}else {
						APIs.noPerm(p);
					}
				}else if(args[0].equalsIgnoreCase("thunder")) {
					if(p.hasPermission("mlps.setweather")) {
						p.getWorld().setStorm(true);
						p.getWorld().setThundering(true);
						p.sendMessage(APIs.prefix("main") + APIs.returnStringReady(p, "cmd.weather.notime").replace("%world", p.getWorld().getName()).replace("%weather", "thunder").replace("%wetter", "Gewitter"));
					}else {
						APIs.noPerm(p);
					}
				}else {
					p.sendMessage(APIs.prefix("main") + APIs.returnStringReady(p, "usage") + "§7 /weather <clear|rain|thunder> [time in seconds]");
				}
			}else if(args.length == 2) {
				if(args[0].equalsIgnoreCase("rain")) {
					if(p.hasPermission("mlps.setweather")) {
						int time = Integer.valueOf(args[1]);
						p.getWorld().setThundering(false);
						p.getWorld().setStorm(true);
						p.getWorld().setWeatherDuration(time);
						p.sendMessage(APIs.prefix("main") + APIs.returnStringReady(p, "cmd.weather.time").replace("%world", p.getWorld().getName()).replace("%weather", "rain").replace("%wetter", "Regen").replace("%seconds", String.valueOf(time)));
						}else {
						APIs.noPerm(p);
					}
				}else if(args[0].equalsIgnoreCase("thunder")) {
					if(p.hasPermission("mlps.setweather")) {
						int time = Integer.valueOf(args[1]);
						p.getWorld().setStorm(true);
						p.getWorld().setThundering(true);
						p.getWorld().setThunderDuration(time);
						p.sendMessage(APIs.prefix("main") + APIs.returnStringReady(p, "cmd.weather.time").replace("%world", p.getWorld().getName()).replace("%weather", "thunder").replace("%wetter", "Gewitter").replace("%seconds", String.valueOf(time)));
					}else {
						APIs.noPerm(p);
					}
				}else {
					p.sendMessage(APIs.prefix("main") + APIs.returnStringReady(p, "usage") + "§7 /weather <clear|rain|thunder> [time in seconds]");
				}
			}
		}
		return true;
	}
}