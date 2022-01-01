package at.mlps.rc.cmd;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.scheduler.BukkitRunnable;

import at.mlps.rc.api.APIs;
import at.mlps.rc.api.ActionLogger;
import at.mlps.rc.main.Main;

public class StopCMD implements CommandExecutor, Listener{
	
	private Main plugin;
	
	public StopCMD(Main m) {
		this.plugin = m;
	}
	
	static boolean IStopBool = false;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		APIs api = new APIs();
		if(args.length == 0) {
			if(sender.hasPermission("mlps.admin.stopserver")) {
				sender.sendMessage(api.prefix("main") + "§7Usage: /stop <Reason>");
			}else {
				api.noPerm((Player) sender);
			}
		}else if(args.length >= 1) {
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < args.length; i++) {
				sb.append(args[i]).append(" ");
			}
			String reason = sb.toString().trim();
			if(sender.hasPermission("mlps.admin.stopserver")) {
				sender.sendMessage(api.prefix("main") + "§cServer will stop now!");
				if(sender instanceof Player) {
					ActionLogger.log(api.getServerName(), (Player)sender, "Player has stopped the server.");
				}else {
					ActionLogger.log(api.getServerName(), "Console", "User has stopped the server.");
				}
				IStopBool = true;
				Bukkit.broadcastMessage(api.prefix("main") + "§4The server will be shutdown now.\n§4You will be redirected to the Lobby!\n \n§7Reason: §a" + reason);
				for(Player all : Bukkit.getOnlinePlayers()) {
					ByteArrayOutputStream b = new ByteArrayOutputStream();
					DataOutputStream out = new DataOutputStream(b);
					try {
						out.writeUTF("Connect");
						out.writeUTF("lobby");
					}catch (Exception ex) {
						sender.sendMessage(api.prefix("main") + "§cPlease try it later again.");
						ex.printStackTrace();
					}
					all.sendPluginMessage(this.plugin, "BungeeCord", b.toByteArray());
				}
				new BukkitRunnable() {
					@Override
					public void run() {
						Bukkit.shutdown();
					}
				}.runTaskLater(Main.instance, 200);
			}else {
				api.noPerm((Player) sender);
			}
		}
		return true;
	}
	
	@EventHandler
	public void onLogin(PlayerLoginEvent e) {
		if(IStopBool == false) {
			e.allow();
		}else {
			e.disallow(Result.KICK_OTHER, "§aRedi§cCraft\n \n§7Server will be shutdown in a few seconds.\n§7Try it in a minute again.");
		}
	}
}