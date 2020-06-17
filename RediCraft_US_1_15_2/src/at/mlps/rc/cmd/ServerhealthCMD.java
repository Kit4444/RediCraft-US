package at.mlps.rc.cmd;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import at.mlps.rc.api.APIs;
import at.mlps.rc.api.PerformanceMonitor;
import at.mlps.rc.main.Main;
import net.minecraft.server.v1_15_R1.MinecraftServer;

public class ServerhealthCMD implements CommandExecutor{
	
	@SuppressWarnings({ "deprecation", "resource" })
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(Main.prefix() + "§7Bitte nur inGame ausführen!");
		}else {
			Player p = (Player)sender;
			Runtime runtime = Runtime.getRuntime();
			SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
			String stime = time.format(new Date());
			SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
			String sdate = date.format(new Date());
			StringBuilder sb = new StringBuilder("§7TPS from last 1m §a" );
			PerformanceMonitor cpu = new PerformanceMonitor();
			DecimalFormat df = new DecimalFormat("#.##");
			for(double tps : MinecraftServer.getServer().recentTps) {
				sb.append(format(tps));
				sb.append( "§7,§a " );
			}
			if(p.hasPermission("mlps.admin.checkServer")) {
				p.sendMessage("§7§m================§7[§cServerinfo§7]§m================");
				p.sendMessage("§7operating System: §9" + System.getProperty("os.name"));
				p.sendMessage("§7Architecture: §9" + System.getProperty("os.arch"));
				p.sendMessage("§7Java Vendor: §9" + System.getProperty("java.vendor"));
				p.sendMessage("§7Java Version: §9" + System.getProperty("java.version"));
				p.sendMessage("§7RAM: §a" + (runtime.totalMemory() - runtime.freeMemory()) / 1048576L + "§8MB §7/ §c" + runtime.totalMemory() / 1048576L + "§8MB §7(§e" + runtime.freeMemory() / 1048576L + "§8MB free§7)");
				p.sendMessage("§7CPU-Cores: §9" + runtime.availableProcessors() + " §7Cores");
				p.sendMessage("§7CPU-Load: §9" + df.format(cpu.getCpuUsage()));
				p.sendMessage("§7IP + Port: §9" + Bukkit.getIp() + "§7:§9" + Bukkit.getPort());
				p.sendMessage("§7Servername + ID: §9" + APIs.getServerName() + " §7/§9 " + APIs.getServerId());
				p.sendMessage("§7Date & Time: §9" + sdate + " §7/§9 " + stime);
				String str = sb.substring(0, sb.length() - 1);
				String str2 = str.substring(0, 28);
				p.sendMessage(str2);
			}else {
				APIs.noPerm(p);
			}
		}
		return true;
	}
	
	private String format(double tps) {
		return (( tps > 18.0 ) ? ChatColor.GREEN : ( tps > 16.0 ) ? ChatColor.YELLOW : ChatColor.RED ).toString() + (( tps > 20.0 ) ? "*" : "" ) + Math.min( Math.round(tps * 100.0) / 100.0, 20.0);
	}

}
