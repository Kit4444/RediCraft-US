package at.kitsoft.redicraft.cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import at.kitsoft.redicraft.api.APIs;
import at.kitsoft.redicraft.api.ActionLogger;
import at.kitsoft.redicraft.main.Main;
import at.kitsoft.redicraft.main.Serverupdater;

public class ClearLag implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage(Main.consolesend);
		}else {
			APIs api = new APIs();
			Player p = (Player)sender;
			if(p.hasPermission("mlps.clearLag")) {
				Serverupdater.time = 869;
				ActionLogger.log(api.getServerName(), p, "Player executed the clearlag command.");
			}else {
				api.noPerm(p);
				ActionLogger.log(api.getServerName(), p, "Player attempted to execute the clearlag command.");
			}
		}
		return true;
	}
}