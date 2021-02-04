package at.mlps.rc.cmd;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import at.mlps.rc.api.APIs;
import at.mlps.rc.main.Main;

public class FlyCMD implements CommandExecutor, Listener{
	
	static ArrayList<UUID> flylist = new ArrayList<>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage(Main.consolesend);
		}else {
			APIs api = new APIs();
			Player p = (Player)sender;
			if(args.length == 0) {
				if(p.hasPermission("mlps.canFly.own")) {
					if(flylist.contains(p.getUniqueId())) {
						flylist.remove(p.getUniqueId());
						p.setAllowFlight(false);
						api.sendMSGReady(p, "cmd.fly.own.false");
					}else {
						flylist.add(p.getUniqueId());
						p.setAllowFlight(true);
						api.sendMSGReady(p, "cmd.fly.own.true");
					}
				}else {
					api.noPerm(p);
				}
			}else if(args.length == 1) {
				Player p2 = Bukkit.getPlayerExact(args[0]);
				if(p2 == null) {
					api.sendMSGReady(p, "cmd.fly.other.offline");
				}else {
					if(p.hasPermission("mlps.canFly.other")) {
						if(flylist.contains(p2.getUniqueId())) {
							flylist.remove(p2.getUniqueId());
							p2.setAllowFlight(false);
							p.sendMessage(api.prefix("main") + api.returnStringReady(p, "cmd.fly.other.false").replace("%displayer", p2.getDisplayName()));
						}else {
							flylist.add(p2.getUniqueId());
							p2.setAllowFlight(true);
							p.sendMessage(api.prefix("main") + api.returnStringReady(p, "cmd.fly.other.true").replace("%displayer", p2.getDisplayName()));
						}
					}else {
						api.noPerm(p);
					}
				}
			}else {
				p.sendMessage(api.prefix("main") + api.returnStringReady(p, "usage") + "§7 /fly [Player]");
			}
		}
		return false;
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if(flylist.contains(p.getUniqueId())) {
			flylist.remove(p.getUniqueId());
		}
	}
}