package at.mlps.rc.cmd;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

import at.mlps.rc.api.APIs;
import at.mlps.rc.main.Main;

public class ClearLag implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage(Main.consolesend);
		}else {
			Player p = (Player)sender;
			if(p.hasPermission("mlps.clearLag")) {
				int worldentities = 0;
				for(World world : Bukkit.getWorlds()) {
					for(Entity e : world.getEntities()) {
						if(e instanceof Item) {
							worldentities++;
							e.remove();
						}
					}
				}
				p.sendMessage(APIs.prefix("main") + APIs.returnStringReady(p, "cmd.clearlag").replace("%entities", String.valueOf(worldentities)).replace("%worldcount", String.valueOf(Bukkit.getWorlds().size())));
			}else {
				APIs.noPerm(p);
			}
		}
		return true;
	}
}