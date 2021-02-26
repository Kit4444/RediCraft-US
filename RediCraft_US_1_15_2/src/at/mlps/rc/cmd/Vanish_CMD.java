package at.mlps.rc.cmd;

import java.util.LinkedList;
import java.util.Objects;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import at.mlps.rc.api.APIs;
import at.mlps.rc.main.Main;

public class Vanish_CMD implements CommandExecutor, Listener{
	
	private static final LinkedList<UUID> vanishedPlayers = new LinkedList<>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			if(args.length == 1) {
				Player p2 = Bukkit.getPlayer(args[0]);
				if(p2 != null) {
					if(!isVanished(p2)) {
						vanishPlayer(p2);
					}else {
						unvanishPlayer(p2);
					}
				}else {
					sender.sendMessage("Player is not online on the current server.");
				}
			}else {
				sender.sendMessage("No player specified!");
			}
		}else {
			Player p = (Player)sender;
			APIs api = new APIs();
			if(args.length == 0) {
				if(p.hasPermission("mlps.vanish.own")) {
					if(!isVanished(p)) {
						vanishPlayer(p);
						p.sendMessage(api.returnStringReady(p, "cmd.vanish.own").replace("%state", "vanished"));
					}else {
						unvanishPlayer(p);
						p.sendMessage(api.returnStringReady(p, "cmd.vanish.own").replace("%state", "unvanished"));
					}
				}else {
					api.noPerm(p);
				}
			}else if(args.length == 1) {
				if(p.hasPermission("mlps.vanish.other")) {
					Player p2 = Bukkit.getPlayer(args[0]);
					if(p2 != null) {
						if(!isVanished(p2)) {
							vanishPlayer(p2);
							p.sendMessage(api.returnStringReady(p, "cmd.vanish.other.own").replace("%state", "vanished").replace("%displayer", p2.getDisplayName()));
							p2.sendMessage(api.returnStringReady(p, "cmd.vanish.other.other").replace("%state", "vanished").replace("%displayer", p.getDisplayName()));
						}else {
							unvanishPlayer(p2);
							p.sendMessage(api.returnStringReady(p, "cmd.vanish.other.own").replace("%state", "unvanished").replace("%displayer", p2.getDisplayName()));
							p2.sendMessage(api.returnStringReady(p, "cmd.vanish.other.other").replace("%state", "unvanished").replace("%displayer", p.getDisplayName()));
						}
					}else {
						api.sendMSGReady(p, "playernotonline");
					}
				}else {
					api.noPerm(p);
				}
			}
		}
		return true;
	}
	
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent e) {
		Player p = e.getPlayer();
		for(UUID target : vanishedPlayers) {
			p.hidePlayer(Main.instance, Objects.requireNonNull(Bukkit.getPlayer(target)));
		}
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if(isVanished(p)) {
			unvanishPlayer(p);
		}
	}
	
	boolean isVanished(Player p) {
		return vanishedPlayers.contains(p.getUniqueId());
	}
	
	boolean vanishPlayer(Player p) {
		if(!vanishedPlayers.contains(p.getUniqueId())) {
			hidePlayerFromPeople(p);
			vanishedPlayers.add(p.getUniqueId());
			return true;
		}else {
			return false;
		}
	}
	
	boolean unvanishPlayer(Player p) {
		if(vanishedPlayers.contains(p.getUniqueId())) {
			unhidePlayerFromPeople(p);
			vanishedPlayers.remove(p.getUniqueId());
			return true;
		}else {
			return false;
		}
	}
	
	void hidePlayerFromPeople(Player p) {
		for(Player target : Bukkit.getOnlinePlayers()) {
			if(target != p && target != null) {
				if(p != null) {
					target.hidePlayer(Main.instance, p);
				}
			}
		}
	}
	
	void unhidePlayerFromPeople(Player p) {
		for(Player target : Bukkit.getOnlinePlayers()) {
			if(target != p && target != null) {
				if(p != null) {
					target.showPlayer(Main.instance, p);
				}
			}
		}
	}

}