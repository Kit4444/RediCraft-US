package at.mlps.rc.cmd;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import at.mlps.rc.api.APIs;
import at.mlps.rc.main.Main;

public class stop implements CommandExecutor{
	
	private Main plugin;
	
	public stop(Main m) {
		this.plugin = m;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length == 0) {
			if(sender.hasPermission("mlps.admin.stopserver")) {
				sender.sendMessage(Main.prefix() + "§7Usage: /stop <Reason>");
			}else {
				APIs.noPerm((Player) sender);
			}
		}else if(args.length >= 1) {
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < args.length; i++) {
				sb.append(args[i]).append(" ");
			}
			String reason = sb.toString().trim();
			if(sender.hasPermission("mlps.admin.stopserver")) {
				sender.sendMessage(Main.prefix() + "§cServer will stop now!");
				Bukkit.broadcastMessage(Main.prefix() + "§4The server will be shutdown now.\n§4You will be redirected to the Lobby!\n \n§7Reason: §a" + reason);
				for(Player all : Bukkit.getOnlinePlayers()) {
					ByteArrayOutputStream b = new ByteArrayOutputStream();
					DataOutputStream out = new DataOutputStream(b);
					try {
						out.writeUTF("Connect");
						out.writeUTF("lobby");
					}catch (Exception ex) {
						sender.sendMessage(Main.prefix() + "§cPlease try it later again.");
						ex.printStackTrace();
					}
					all.sendPluginMessage(this.plugin, "BungeeCord", b.toByteArray());
				}
				Bukkit.shutdown();
			}else {
				APIs.noPerm((Player) sender);
			}
		}
		return true;
	}
}