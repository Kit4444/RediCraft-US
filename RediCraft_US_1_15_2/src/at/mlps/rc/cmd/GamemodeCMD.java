package at.mlps.rc.cmd;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import at.mlps.rc.api.APIs;
import at.mlps.rc.main.Main;

public class GamemodeCMD implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage(Main.consolesend);
		}else {
			Player p = (Player)sender;
			APIs api = new APIs();
			if(args.length == 0 ) {
				if(p.hasPermission("mlps.gamemode.other")) {
					p.sendMessage(api.prefix("main") + "§7Usage: /gm* <0|1|2|3> [Player]");
					p.sendMessage(api.prefix("main") + "§7* -> §a/gm §7|§a /gamemode §7are useable");
				}else if(p.hasPermission("mlps.gamemode.own")) {
					p.sendMessage(api.prefix("main") + "§7Usage: /gm* <0|1|2|3>");
					p.sendMessage(api.prefix("main") + "§7* -> §a/gm §7|§a /gamemode §7are useable");
				}else {
					api.noPerm(p);
				}
			}else if(args.length == 1) {
				if(p.hasPermission("mlps.gamemode.own")){
					String gm = args[0];
					if(gm.equalsIgnoreCase("0")) {
						p.setGameMode(GameMode.SURVIVAL);
						p.sendMessage(api.prefix("main") + api.returnStringReady(p, "cmd.gamemode.own").replace("%gamemode", "Survival"));
					}else if(gm.equalsIgnoreCase("1")) {
						p.setGameMode(GameMode.CREATIVE);
						p.sendMessage(api.prefix("main") + api.returnStringReady(p, "cmd.gamemode.own").replace("%gamemode", "Creative"));
					}else if(gm.equalsIgnoreCase("2")) {
						p.setGameMode(GameMode.ADVENTURE);
						p.sendMessage(api.prefix("main") + api.returnStringReady(p, "cmd.gamemode.own").replace("%gamemode", "Adventure"));
					}else if(gm.equalsIgnoreCase("3")) {
						p.setGameMode(GameMode.SPECTATOR);
						p.sendMessage(api.prefix("main") + api.returnStringReady(p, "cmd.gamemode.own").replace("%gamemode", "Spectator"));
					}else {
						p.sendMessage(api.prefix("main") + "§7Usage: /gm* <0|1|2|3>");
					}
				}else {
					api.noPerm(p);
				}
			}else if(args.length >= 2 && args.length <= 2) {
				if(p.hasPermission("mlps.gamemode.other")) {
					String gm = args[0];
					Player p2 = Bukkit.getPlayerExact(args[1]);
					if(p2 == null) {
						api.sendMSGReady(p, "notonline");
					}else {
						if(gm.equalsIgnoreCase("0")) {
							p2.setGameMode(GameMode.SURVIVAL);
							p.sendMessage(api.prefix("main") + api.returnStringReady(p, "cmd.gamemode.other").replace("%gamemode", "Survival").replace("%displayer", p2.getDisplayName()));
						}else if(gm.equalsIgnoreCase("1")) {
							p2.setGameMode(GameMode.CREATIVE);
							p.sendMessage(api.prefix("main") + api.returnStringReady(p, "cmd.gamemode.other").replace("%gamemode", "Creative").replace("%displayer", p2.getDisplayName()));
						}else if(gm.equalsIgnoreCase("2")) {
							p2.setGameMode(GameMode.ADVENTURE);
							p.sendMessage(api.prefix("main") + api.returnStringReady(p, "cmd.gamemode.other").replace("%gamemode", "Adventure").replace("%displayer", p2.getDisplayName()));
						}else if(gm.equalsIgnoreCase("3")) {
							p2.setGameMode(GameMode.SPECTATOR);
							p.sendMessage(api.prefix("main") + api.returnStringReady(p, "cmd.gamemode.other").replace("%gamemode", "Spectator").replace("%displayer", p2.getDisplayName()));
						}
					}
				}else {
					api.noPerm(p);
				}
			}else {
				if(p.hasPermission("mlps.gamemode.other")) {
					p.sendMessage(api.prefix("main") + "§7Usage: /gm <0|1|2|3> [Player]");
				}else if(p.hasPermission("mlps.gamemode.own")) {
					p.sendMessage(api.prefix("main") + "§7Usage: /gm <0|1|2|3>");
				}else {
					api.noPerm(p);
				}
			}
		}
		return false;
	}
}