package at.kitsoft.redicraft.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class MultiroleTest implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			PermissionUser pu = PermissionsEx.getUser(p);
			StringBuilder sb = new StringBuilder();
			for(String s : pu.getParentIdentifiers()) {
				sb.append(s);
				sb.append(" ");
			}
			p.sendMessage("§7Groups (§a" + pu.getParentIdentifiers().size() + "§7): §6" + sb.toString());
		}
		return false;
	}
}