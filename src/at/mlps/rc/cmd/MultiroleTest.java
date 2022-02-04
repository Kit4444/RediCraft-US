package at.mlps.rc.cmd;

import java.util.List;

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
			List<String> groups = pu.getParentIdentifiers();
			StringBuilder sb = new StringBuilder();
			for(String s : groups) {
				sb.append(s);
				sb.append(" ");
			}
			p.sendMessage("GROUPS (" + groups.size() + "): " + sb.toString());
		}
		return false;
	}
}