package at.mlps.rc.cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import at.mlps.rc.api.APIs;
import at.mlps.rc.main.Main;

public class InvseeCMD implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage(Main.consolesend);
		}else {
			Player p = (Player)sender;
			if(args.length == 1) {
				Player p2 = Bukkit.getPlayerExact(args[0]);
				if(p2 == null) {
					APIs.sendMSGReady(p, "cmd.invsee.playeroffline");
				}else {
					if(p.hasPermission("mlps.invsee")) {
						if(p != p2) {
							p.openInventory(p2.getInventory());
							p.sendMessage(APIs.prefix("main") + APIs.returnStringReady(p, "cmd.invsee.success").replace("%displayer", p2.getDisplayName()));
						}else {
							APIs.sendMSGReady(p, "cmd.invsee.notown");
						}
					}else {
						APIs.noPerm(p);
					}
				}
			}else {
				p.sendMessage(APIs.prefix("main") + APIs.returnStringReady(p, "usage") + " §7/invsee <Name>");
			}
		}
		return true;
	}
}