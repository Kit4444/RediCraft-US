package at.mlps.rc.cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import at.mlps.rc.api.APIs;
import at.mlps.rc.main.Main;

public class SkullCMD implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage(Main.consolesend);
		}else {
			Player p = (Player)sender;
			APIs api = new APIs();
			if(args.length == 1) {
				p.getInventory().addItem(api.skullItem(1, args[0] + "'s Head", args[0]));
				p.sendMessage(api.prefix("main") + api.returnStringReady(p, "cmd.head").replace("%player", args[0]));
			}else {
				p.sendMessage(api.prefix("main") + api.returnStringReady(p, "usage") + " §7/head <Headname>");
			}
		}
		return true;
	}
}