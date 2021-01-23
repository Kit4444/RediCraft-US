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
			if(args.length == 0 ) {
				if(p.hasPermission("mlps.gamemode.other")) {
					p.sendMessage(APIs.prefix("main") + "§7Usage: /gm* <0|1|2|3> [Player]");
					p.sendMessage(APIs.prefix("main") + "§7* -> §a/gm §7|§a /gamemode §7are useable");
				}else if(p.hasPermission("mlps.gamemode.own")) {
					p.sendMessage(APIs.prefix("main") + "§7Usage: /gm* <0|1|2|3>");
					p.sendMessage(APIs.prefix("main") + "§7* -> §a/gm §7|§a /gamemode §7are useable");
				}else {
					APIs.noPerm(p);
				}
			}else if(args.length == 1) {
				if(p.hasPermission("mlps.gamemode.own")){
					String gm = args[0];
					if(gm.equalsIgnoreCase("0")) {
						p.setGameMode(GameMode.SURVIVAL);
						p.sendMessage(APIs.prefix("main") + APIs.returnStringReady(p, "cmd.gamemode.change").replace("%gamemode", "Survival"));
					}else if(gm.equalsIgnoreCase("1")) {
						p.setGameMode(GameMode.CREATIVE);
						p.sendMessage(APIs.prefix("main") + APIs.returnStringReady(p, "cmd.gamemode.change").replace("%gamemode", "Creative"));
					}else if(gm.equalsIgnoreCase("2")) {
						p.setGameMode(GameMode.ADVENTURE);
						p.sendMessage(APIs.prefix("main") + APIs.returnStringReady(p, "cmd.gamemode.change").replace("%gamemode", "Adventure"));
					}else if(gm.equalsIgnoreCase("3")) {
						p.setGameMode(GameMode.SPECTATOR);
						p.sendMessage(APIs.prefix("main") + APIs.returnStringReady(p, "cmd.gamemode.change").replace("%gamemode", "Spectator"));
					}else {
						p.sendMessage(APIs.prefix("main") + "§7Usage: /gm* <0|1|2|3>");
					}
				}else {
					APIs.noPerm(p);
				}
			}else if(args.length >= 2 && args.length <= 2) {
				if(p.hasPermission("mlps.gamemode.other")) {
					String gm = args[0];
					Player p2 = Bukkit.getPlayerExact(args[1]);
					if(p2 == null) {
						APIs.sendMSGReady(p, "cmd.gamemode.notonline");
					}else {
						if(gm.equalsIgnoreCase("0")) {
							p2.setGameMode(GameMode.SURVIVAL);
							p2.sendMessage(APIs.prefix("main") + APIs.returnStringReady(p2, "cmd.gamemode.change").replace("%gamemode", "Survival"));
						}else if(gm.equalsIgnoreCase("1")) {
							p2.setGameMode(GameMode.CREATIVE);
							p2.sendMessage(APIs.prefix("main") + APIs.returnStringReady(p2, "cmd.gamemode.change").replace("%gamemode", "Creative"));
						}else if(gm.equalsIgnoreCase("2")) {
							p2.setGameMode(GameMode.ADVENTURE);
							p2.sendMessage(APIs.prefix("main") + APIs.returnStringReady(p2, "cmd.gamemode.change").replace("%gamemode", "Adventure"));
						}else if(gm.equalsIgnoreCase("3")) {
							p2.setGameMode(GameMode.SPECTATOR);
							p2.sendMessage(APIs.prefix("main") + APIs.returnStringReady(p2, "cmd.gamemode.change").replace("%gamemode", "Spectator"));
						}
					}
				}else {
					APIs.noPerm(p);
				}
			}else {
				if(p.hasPermission("mlps.gamemode.other")) {
					p.sendMessage(APIs.prefix("main") + "§7Usage: /gm <0|1|2|3> [Player]");
				}else if(p.hasPermission("mlps.gamemode.own")) {
					p.sendMessage(APIs.prefix("main") + "§7Usage: /gm <0|1|2|3>");
				}else {
					APIs.noPerm(p);
				}
			}
		}
		return false;
	}
}