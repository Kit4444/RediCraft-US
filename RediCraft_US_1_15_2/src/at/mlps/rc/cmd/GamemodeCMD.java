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
					p.sendMessage(Main.prefix() + "§7Usage: /gm* <0|1|2|3> [Player] [boolean (send Message to Player 2)]");
					p.sendMessage(Main.prefix() + "§7* -> §a/gm §7|§a /gamemode §7are useable");
				}else if(p.hasPermission("mlps.gamemode.own")) {
					p.sendMessage(Main.prefix() + "§7Usage: /gm* <0|1|2|3> [Player]");
					p.sendMessage(Main.prefix() + "§7* -> §a/gm §7|§a /gamemode §7are useable");
				}else {
					APIs.noPerm(p);
				}
			}else if(args.length == 1) {
				if(p.hasPermission("mlps.gamemode.own")){
					String gm = args[0];
					if(gm.equalsIgnoreCase("0")) {
						p.setGameMode(GameMode.SURVIVAL);
						p.sendMessage(Main.prefix() + APIs.returnStringReady(p, "cmd.gamemode.change").replace("%gamemode", "Survival"));
					}else if(gm.equalsIgnoreCase("1")) {
						p.setGameMode(GameMode.CREATIVE);
						p.sendMessage(Main.prefix() + APIs.returnStringReady(p, "cmd.gamemode.change").replace("%gamemode", "Creative"));
					}else if(gm.equalsIgnoreCase("2")) {
						p.setGameMode(GameMode.ADVENTURE);
						p.sendMessage(Main.prefix() + APIs.returnStringReady(p, "cmd.gamemode.change").replace("%gamemode", "Adventure"));
					}else if(gm.equalsIgnoreCase("3")) {
						p.setGameMode(GameMode.SPECTATOR);
						p.sendMessage(Main.prefix() + APIs.returnStringReady(p, "cmd.gamemode.change").replace("%gamemode", "Spectator"));
					}else {
						p.sendMessage(Main.prefix() + "§7Usage: /gm* <0|1|2|3>");
					}
				}else {
					APIs.noPerm(p);
				}
			}else if(args.length >= 3 && args.length <= 3) {
				if(p.hasPermission("mlps.gamemode.other")) {
					String gm = args[0];
					String sendmsg = args[2];
					Player p2 = Bukkit.getPlayerExact(args[1]);
					if(p2 == null) {
						APIs.sendMSGReady(p, "cmd.gamemode.notonline");
					}else {
						if(gm.equalsIgnoreCase("0")) {
							p2.setGameMode(GameMode.SURVIVAL);
							if(sendmsg.equalsIgnoreCase("true")) {
								p2.sendMessage(Main.prefix() + APIs.returnStringReady(p2, "cmd.gamemode.change").replace("%gamemode", "Survival"));
							}else if(sendmsg.equalsIgnoreCase("false")) {
								//do nothing at all
							}else {
								p2.sendMessage(Main.prefix() + APIs.returnStringReady(p2, "cmd.gamemode.change").replace("%gamemode", "Survival"));
							}
						}else if(gm.equalsIgnoreCase("1")) {
							p2.setGameMode(GameMode.CREATIVE);
							if(sendmsg.equalsIgnoreCase("true")) {
								p2.sendMessage(Main.prefix() + APIs.returnStringReady(p2, "cmd.gamemode.change").replace("%gamemode", "Creative"));
							}else if(sendmsg.equalsIgnoreCase("false")) {
								//do nothing at all
							}else {
								p2.sendMessage(Main.prefix() + APIs.returnStringReady(p2, "cmd.gamemode.change").replace("%gamemode", "Creative"));
							}
						}else if(gm.equalsIgnoreCase("2")) {
							p2.setGameMode(GameMode.ADVENTURE);
							if(sendmsg.equalsIgnoreCase("true")) {
								p2.sendMessage(Main.prefix() + APIs.returnStringReady(p2, "cmd.gamemode.change").replace("%gamemode", "Adventure"));
							}else if(sendmsg.equalsIgnoreCase("false")) {
								//do nothing at all
							}else {
								p2.sendMessage(Main.prefix() + APIs.returnStringReady(p2, "cmd.gamemode.change").replace("%gamemode", "Adventure"));
							}
						}else if(gm.equalsIgnoreCase("3")) {
							p2.setGameMode(GameMode.SPECTATOR);
							if(sendmsg.equalsIgnoreCase("true")) {
								p2.sendMessage(Main.prefix() + APIs.returnStringReady(p2, "cmd.gamemode.change").replace("%gamemode", "Spectator"));
							}else if(sendmsg.equalsIgnoreCase("false")) {
								//do nothing at all
							}else {
								p2.sendMessage(Main.prefix() + APIs.returnStringReady(p2, "cmd.gamemode.change").replace("%gamemode", "Spectator"));
							}
						}
					}
				}else {
					APIs.noPerm(p);
				}
			}else {
				if(p.hasPermission("mlps.gamemode.other")) {
					p.sendMessage(Main.prefix() + "§7Usage: /gm <0|1|2|3> [Player] [boolean (send Message to Player 2)]");
				}else if(p.hasPermission("mlps.gamemode.own")) {
					p.sendMessage(Main.prefix() + "§7Usage: /gm <0|1|2|3> [Player]");
				}else {
					APIs.noPerm(p);
				}
			}
		}
		return false;
	}
}