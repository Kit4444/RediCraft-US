package at.mlps.rc.cmd;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import at.mlps.rc.api.APIs;
import at.mlps.rc.main.Main;

public class PM_System implements CommandExecutor{
	
	static ArrayList<UUID> bpm = new ArrayList<>();
	File msgf = new File("plugins/RCUSS/msg.yml");
	
	private String prefix() {
		APIs api = new APIs();
		return api.prefix("pmsystem");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage(Main.consolesend);
		}else {
			APIs api = new APIs();
			Player p = (Player)sender;
			if(cmd.getName().equalsIgnoreCase("msg")) {
				if(args.length == 0) {
					p.sendMessage(api.prefix("main") + api.returnStringReady(p, "usage") + "§7 /msg <Player> <Message>");
				}else {
					Player p2 = Bukkit.getPlayerExact(args[0]);
					if(p2 == null) {
						api.sendMSGReady(p, "cmd.msg.playernotonline");
					}else {
						YamlConfiguration cfg = YamlConfiguration.loadConfiguration(msgf);
						StringBuilder sb = new StringBuilder();
						for(int i = 1; i < args.length; i++) {
							sb.append(args[i]).append(" ");
						}
						String msg = sb.toString().trim();
						if(bpm.contains(p.getUniqueId())) {
							api.sendMSGReady(p, "cmd.msg.blockedmsg");
						}else {
							if(bpm.contains(p2.getUniqueId())) {
								if(p.hasPermission("mlps.bypassbpm")) {
									api.sendMSGReady(p, "cmd.msg.bypassmsg");
									p.sendMessage(prefix() + api.returnStringReady(p, "cmd.msg.you") + " §7» " + p2.getDisplayName() + "§7: " + ChatColor.translateAlternateColorCodes('&', msg));
									p2.sendMessage(prefix() + p.getDisplayName() + " §7» " + api.returnStringReady(p2, "cmd.msg.you") + "§7: " + ChatColor.translateAlternateColorCodes('&', msg));
									cfg.set(p.getName(), p2.getName());
									cfg.set(p2.getName(), p.getName());
									try {
										cfg.save(msgf);
									} catch (IOException e) {
										e.printStackTrace();
									}
								}else {
									api.sendMSGReady(p, "cmd.msg.playerblocked");
								}
							}else {
								p.sendMessage(prefix() + api.returnStringReady(p, "cmd.msg.you") + " §7» " + p2.getDisplayName() + "§7: " + ChatColor.translateAlternateColorCodes('&', msg));
								p2.sendMessage(prefix() + p.getDisplayName() + " §7» " + api.returnStringReady(p2, "cmd.msg.you") + "§7: " + ChatColor.translateAlternateColorCodes('&', msg));
								cfg.set(p.getName(), p2.getName());
								cfg.set(p2.getName(), p.getName());
								try {
									cfg.save(msgf);
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
					}
				}
			}else if(cmd.getName().equalsIgnoreCase("r")) {
				if(args.length == 0) {
					p.sendMessage(api.prefix("main") + api.returnStringReady(p, "usage") + "§7 /r <Message>");
				}else {
					YamlConfiguration cfg = YamlConfiguration.loadConfiguration(msgf);
					Player p2 = Bukkit.getPlayer(cfg.getString(p.getName()));
					if(p2 == null) {
						api.sendMSGReady(p, "cmd.r.playernotonline");
					}else {
						StringBuilder sb = new StringBuilder();
						for(int i = 0; i < args.length; i++) {
							sb.append(args[i]).append(" ");
						}
						String msg = sb.toString().trim();
						if(bpm.contains(p2.getUniqueId())) {
							if(p.hasPermission("mlps.bypassbpm")) {
								api.sendMSGReady(p, "cmd.msg.bypassmsg");
								p.sendMessage(prefix() + api.returnStringReady(p, "cmd.msg.you") + " §7» " + p2.getDisplayName() + "§7: " + ChatColor.translateAlternateColorCodes('&', msg));
								p2.sendMessage(prefix() + p.getDisplayName() + " §7» " + api.returnStringReady(p2, "cmd.msg.you") + "§7: " + ChatColor.translateAlternateColorCodes('&', msg));
								cfg.set(p.getName(), p2.getName());
								cfg.set(p2.getName(), p.getName());
								try {
									cfg.save(msgf);
								} catch (IOException e) {
									e.printStackTrace();
								}
							}else {
								api.sendMSGReady(p, "cmd.msg.playerblocked");
							}
						}else {
							p.sendMessage(prefix() + api.returnStringReady(p, "cmd.msg.you") + " §7» " + p2.getDisplayName() + "§7: " + ChatColor.translateAlternateColorCodes('&', msg));
							p2.sendMessage(prefix() + p.getDisplayName() + " §7» " + api.returnStringReady(p2, "cmd.msg.you") + "§7: " + ChatColor.translateAlternateColorCodes('&', msg));
						}
					}
				}
			}else if(cmd.getName().equalsIgnoreCase("blockmsg")) {
				if(bpm.contains(p.getUniqueId())) {
					bpm.remove(p.getUniqueId());
					api.sendMSGReady(p, "cmd.blockpm.remove");
				}else {
					bpm.add(p.getUniqueId());
					api.sendMSGReady(p, "cmd.blockpm.add");
				}
			}
		}
		return true;
	}
}