package at.mlps.rc.event;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.gamingmesh.jobs.Jobs;
import com.gamingmesh.jobs.container.JobProgression;
//import com.hotmail.steven.bconomy.account.AccountData;

import at.mlps.rc.api.APIs;
import at.mlps.rc.api.PerformanceMonitor;
import at.mlps.rc.cmd.MoneyAPI;
import at.mlps.rc.main.Main;
import at.mlps.rc.main.Serverupdater;
import at.mlps.rc.mysql.lb.MySQL;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class ScoreboardCLS implements Listener{
	
	/*  used: ⬛
	 *  unused: ⬜
	 * 
	 */
	
	int sbmain = 0;
	
	@SuppressWarnings("deprecation")
	public void setScoreboard(Player p) throws IllegalStateException, IllegalArgumentException, SQLException {
		Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective o = sb.registerNewObjective("aaa", "bbb");
		
		int ping = ((CraftPlayer)p).getHandle().ping;
		PermissionUser pu = PermissionsEx.getUser(p);
		int pusergen = APIs.getPlayers("BungeeCord", "currPlayers");
		int pusermax = APIs.getPlayers("BungeeCord", "maxPlayers");
		int pusercurr = Bukkit.getOnlinePlayers().size();
		String dbpr = APIs.prefix("scoreboard");
		int userperc = (pusergen * 100 / pusermax);
		//int money = AccountData.getAccountBalance(p.getUniqueId().toString(), "default");
		int money = MoneyAPI.getMoney(p.getUniqueId().toString());
		
		o.setDisplaySlot(DisplaySlot.SIDEBAR);
		o.setDisplayName(dbpr);
		if(Main.serverlist.contains(APIs.getServerName())) {
			if(getSB(p) == 0) {
				//do nothing - as this is the off-state of the scoreboard
			}else if(getSB(p) == 1) {
				if(sbmain >= 0 && sbmain <= 5) {
					o.getScore("§7Player:").setScore(7);
					if(userperc <= 20) {
						o.getScore("  §7(§9" + pusercurr + "§7) §a" + pusergen + " §7/§c " + pusermax).setScore(6);
					}else if(userperc >= 21 && userperc <= 40) {
						o.getScore("  §7(§9" + pusercurr + "§7) §2" + pusergen + " §7/§c " + pusermax).setScore(6);
					}else if(userperc >= 41 && userperc <= 60) {
						o.getScore("  §7(§9" + pusercurr + "§7) §e" + pusergen + " §7/§c " + pusermax).setScore(6);
					}else if(userperc >= 61 && userperc <= 80) {
						o.getScore("  §7(§9" + pusercurr + "§7) §6" + pusergen + " §7/§c " + pusermax).setScore(6);
					}else if(userperc >= 81 && userperc <= 90) {
						o.getScore("  §7(§9" + pusercurr + "§7) §c" + pusergen + " §7/§c " + pusermax).setScore(6);
					}else if(userperc >= 91) {
						o.getScore("  §7(§9" + pusercurr + "§7) §4" + pusergen + " §7/§c " + pusermax).setScore(6);
					}
					o.getScore("  §a").setScore(5);
					o.getScore("§7Ping:").setScore(4);
					o.getScore("  §a" + ping + "§7ms").setScore(3);
					o.getScore("  §b").setScore(2);
					o.getScore(APIs.returnStringReady(p, "scoreboard.sideboard.rank")).setScore(1);
					if (pu.inGroup("Developer")) {
					    o.getScore("  §dDeveloper").setScore(0);
					}else if (pu.inGroup("PMan")) {
					    o.getScore("  §6Projectmanager").setScore(0);
					}else if (pu.inGroup("CMan")) {
					    o.getScore("  §2Community Manager").setScore(0);
					}else if (pu.inGroup("AMan")) {
					    o.getScore("  §4Administrations Manager").setScore(0);
					}else if (pu.inGroup("Admin")) {
					    o.getScore("  §cAdministrator").setScore(0);
					}else if (pu.inGroup("Support")) {
					    o.getScore("  §9Support").setScore(0);
					}else if (pu.inGroup("Translator")) {
						o.getScore("  §eTranslator").setScore(0);
					}else if (pu.inGroup("Mod")) {
					    o.getScore("  §aModerator").setScore(0);
					}else if (pu.inGroup("Builder")) {
					    o.getScore("  §bBuilder").setScore(0);
					}else if (pu.inGroup("RLTM")) {
					    o.getScore("  §3Retired Legend Team Member").setScore(0);
					}else if (pu.inGroup("RTM")) {
					    o.getScore("  §3Retired Team Member").setScore(0);
					}else if (pu.inGroup("Partner")) {
					    o.getScore("  §aPartner").setScore(0);
					}else if (pu.inGroup("Beta")) {
					    o.getScore("  §5Beta-Tester").setScore(0);
					}else if (pu.inGroup("Patron")) {
					    o.getScore("  §ePatron").setScore(0);
					}else if (pu.inGroup("NitroBooster")) {
					    o.getScore("  §5Nitro Booster").setScore(0);
					}else if (pu.inGroup("Friend")) {
					    o.getScore("  §3Friend").setScore(0);
					}else {
						o.getScore("  §7Player").setScore(0);
					}
				}else if(sbmain >= 6 && sbmain <= 10) {
					o.getScore("§7Server:").setScore(7);
					o.getScore("  §a" + APIs.getServerName() + " §7/§a " + APIs.getServerId()).setScore(6);
					o.getScore("  §9").setScore(5);
					o.getScore(APIs.returnStringReady(p, "scoreboard.sideboard.money")).setScore(4);
					o.getScore("  §a" + money + "§7 Coins").setScore(3);
					o.getScore("  §8").setScore(2);
					o.getScore(APIs.returnStringReady(p, "scoreboard.sideboard.playerid")).setScore(1);
					o.getScore("  §7" + igpre(p) + " §9" + igid(p)).setScore(0);
				}
			}else if(getSB(p) == 2) {
				List<JobProgression> jobs = Jobs.getPlayerManager().getJobsPlayer(p).getJobProgression();
				int i = 0;
				int j = jobs.size();
				DecimalFormat df = new DecimalFormat("#.##");
				for(JobProgression job : jobs) {
					o.getScore("§7Exp: §a" + df.format(job.getExperience()) + " §7/§c" + df.format(job.getMaxExperience())).setScore(i);
					i++;
					o.getScore("§7Job: §6" + job.getJob().getName()).setScore(i);
					i++;
					o.getScore("§9  Job " + j).setScore(i);
					j--;
					i++;
				}
			}else if(getSB(p) == 3) {
				if(sbmain >= 0 && sbmain <= 5) {
					o.getScore("§7Newest Report").setScore(9);
					o.getScore("§a§b§c").setScore(8);
					o.getScore("§7Reporter:").setScore(7);
					o.getScore("  §1§a" + retLatestReport("reporter")).setScore(6);
					o.getScore("§7Perpetrator:").setScore(5);
					o.getScore("  §2§c" + retLatestReport("perpetrator")).setScore(4);
					o.getScore("§7Server:").setScore(3);
					o.getScore("  §3§a" + retLatestReport("server")).setScore(2);
					o.getScore("§7Reason:").setScore(1);
					o.getScore("  §4§a" + retLatestReport("reason")).setScore(0);
				}else if(sbmain >= 6 && sbmain <= 10) {
					o.getScore("§7Reports total " + APIs.getServerName()).setScore(7);
					o.getScore("§1  §a" + retRepsTotCuSe() + " §7Reports").setScore(6);
					o.getScore("§7Reports today " + APIs.getServerName()).setScore(5);
					o.getScore("§2  §a" + retRepsTodCuSe() + " §7Reports").setScore(4);
					o.getScore("§7Reports total Network").setScore(3);
					o.getScore("§3  §a" + retRepstotal() + " §7Reports").setScore(2);
					o.getScore("§7Reports today Network").setScore(1);
					o.getScore("§4  §a" + retRepsToday() + " §7Reports").setScore(0);
				}
			}else if(getSB(p) == 4) {
				Runtime runtime = Runtime.getRuntime();
				PerformanceMonitor cpu = new PerformanceMonitor();
				int cpucores = runtime.availableProcessors();
				double cpuload = cpu.getCpuUsage();
				long ramusage = (runtime.totalMemory() - runtime.freeMemory()) / 1048576L;
				long ramtotal = runtime.totalMemory() / 1048576L;
				float rampercentage = (ramusage * 100/ramtotal);
				float networkuserperc = (pusergen * 100/pusermax);
				float curruserperc = (pusercurr * 100/pusermax);
				
				if(sbmain >= 0 && sbmain <= 5) {
					o.getScore("§7CPU load: (§a" + cpucores + " §7Cores)").setScore(7);
					if(cpuload <= 10) {
						o.getScore("§1§e⬛§a⬜⬜⬜⬜⬜⬜⬜⬜⬜").setScore(6);
					}else if(cpuload >= 11 && cpuload <= 20) {
						o.getScore("§1§c⬛§e⬛§a⬜⬜⬜⬜⬜⬜⬜⬜").setScore(6);
					}else if(cpuload >= 21 && cpuload <= 30) {
						o.getScore("§1§c⬛⬛§e⬛§a⬜⬜⬜⬜⬜⬜⬜").setScore(6);
					}else if(cpuload >= 31 && cpuload <= 40) {
						o.getScore("§1§c⬛⬛⬛§e⬛§a⬜⬜⬜⬜⬜⬜").setScore(6);
					}else if(cpuload >= 41 && cpuload <= 50) {
						o.getScore("§1§c⬛⬛⬛⬛§e⬛§a⬜⬜⬜⬜⬜").setScore(6);
					}else if(cpuload >= 51 && cpuload <= 60) {
						o.getScore("§1§c⬛⬛⬛⬛⬛§e⬛§a⬜⬜⬜⬜").setScore(6);
					}else if(cpuload >= 61 && cpuload <= 70) {
						o.getScore("§1§c⬛⬛⬛⬛⬛⬛§e⬛§a⬜⬜⬜").setScore(6);
					}else if(cpuload >= 71 && cpuload <= 80) {
						o.getScore("§1§c⬛⬛⬛⬛⬛⬛⬛§e⬛§a⬜⬜").setScore(6);
					}else if(cpuload >= 81 && cpuload <= 90) {
						o.getScore("§1§c⬛⬛⬛⬛⬛⬛⬛⬛§e⬛§a⬜").setScore(6);
					}else if(cpuload >= 91 && cpuload <= 100) {
						o.getScore("§1§c⬛⬛⬛⬛⬛⬛⬛⬛⬛§e⬛").setScore(6);
					}else {
						o.getScore("§1§c⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛").setScore(6);
					}
					o.getScore("§7Ramload:").setScore(5);
					if(rampercentage <= 10) {
						o.getScore("§2§e⬛§a⬜⬜⬜⬜⬜⬜⬜⬜⬜").setScore(4);
					}else if(rampercentage >= 11 && rampercentage <= 20) {
						o.getScore("§2§c⬛§e⬛§a⬜⬜⬜⬜⬜⬜⬜⬜").setScore(4);
					}else if(rampercentage >= 21 && rampercentage <= 30) {
						o.getScore("§2§c⬛⬛§e⬛§a⬜⬜⬜⬜⬜⬜⬜").setScore(4);
					}else if(rampercentage >= 31 && rampercentage <= 40) {
						o.getScore("§2§c⬛⬛⬛§e⬛§a⬜⬜⬜⬜⬜⬜").setScore(4);
					}else if(rampercentage >= 41 && rampercentage <= 50) {
						o.getScore("§2§c⬛⬛⬛⬛§e⬛§a⬜⬜⬜⬜⬜").setScore(4);
					}else if(rampercentage >= 51 && rampercentage <= 60) {
						o.getScore("§2§c⬛⬛⬛⬛⬛§e⬛§a⬜⬜⬜⬜").setScore(4);
					}else if(rampercentage >= 61 && rampercentage <= 70) {
						o.getScore("§2§c⬛⬛⬛⬛⬛⬛§e⬛§a⬜⬜⬜").setScore(4);
					}else if(rampercentage >= 71 && rampercentage <= 80) {
						o.getScore("§2§c⬛⬛⬛⬛⬛⬛⬛§e⬛§a⬜⬜").setScore(4);
					}else if(rampercentage >= 81 && rampercentage <= 90) {
						o.getScore("§2§c⬛⬛⬛⬛⬛⬛⬛⬛§e⬛§a⬜").setScore(4);
					}else if(rampercentage >= 91 && rampercentage <= 100) {
						o.getScore("§2§c⬛⬛⬛⬛⬛⬛⬛⬛⬛§e⬛").setScore(4);
					}else {
						o.getScore("§2§c⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛").setScore(4);
					}
					o.getScore("§7Playerload Network:").setScore(3);
					if(networkuserperc <= 10) {
						o.getScore("§3§e⬛§a⬜⬜⬜⬜⬜⬜⬜⬜⬜").setScore(2);
					}else if(networkuserperc >= 11 && networkuserperc <= 20) {
						o.getScore("§3§c⬛§e⬛§a⬜⬜⬜⬜⬜⬜⬜⬜").setScore(2);
					}else if(networkuserperc >= 21 && networkuserperc <= 30) {
						o.getScore("§3§c⬛⬛§e⬛§a⬜⬜⬜⬜⬜⬜⬜").setScore(2);
					}else if(networkuserperc >= 31 && networkuserperc <= 40) {
						o.getScore("§3§c⬛⬛⬛§e⬛§a⬜⬜⬜⬜⬜⬜").setScore(2);
					}else if(networkuserperc >= 41 && networkuserperc <= 50) {
						o.getScore("§3§c⬛⬛⬛⬛§e⬛§a⬜⬜⬜⬜⬜").setScore(2);
					}else if(networkuserperc >= 51 && networkuserperc <= 60) {
						o.getScore("§3§c⬛⬛⬛⬛⬛§e⬛§a⬜⬜⬜⬜").setScore(2);
					}else if(networkuserperc >= 61 && networkuserperc <= 70) {
						o.getScore("§3§c⬛⬛⬛⬛⬛⬛§e⬛§a⬜⬜⬜").setScore(2);
					}else if(networkuserperc >= 71 && networkuserperc <= 80) {
						o.getScore("§3§c⬛⬛⬛⬛⬛⬛⬛§e⬛§a⬜⬜").setScore(2);
					}else if(networkuserperc >= 81 && networkuserperc <= 90) {
						o.getScore("§3§c⬛⬛⬛⬛⬛⬛⬛⬛§e⬛§a⬜").setScore(2);
					}else if(networkuserperc >= 91 && networkuserperc <= 100) {
						o.getScore("§3§c⬛⬛⬛⬛⬛⬛⬛⬛⬛§e⬛").setScore(2);
					}else {
						o.getScore("§3§c⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛").setScore(2);
					}
					o.getScore("§7Playerload " + APIs.getServerName()).setScore(1);
					if(curruserperc <= 10) {
						o.getScore("§4§e⬛§a⬜⬜⬜⬜⬜⬜⬜⬜⬜").setScore(0);
					}else if(curruserperc >= 11 && curruserperc <= 20) {
						o.getScore("§4§c⬛§e⬛§a⬜⬜⬜⬜⬜⬜⬜⬜").setScore(0);
					}else if(curruserperc >= 21 && curruserperc <= 30) {
						o.getScore("§4§c⬛⬛§e⬛§a⬜⬜⬜⬜⬜⬜⬜").setScore(0);
					}else if(curruserperc >= 31 && curruserperc <= 40) {
						o.getScore("§4§c⬛⬛⬛§e⬛§a⬜⬜⬜⬜⬜⬜").setScore(0);
					}else if(curruserperc >= 41 && curruserperc <= 50) {
						o.getScore("§4§c⬛⬛⬛⬛§e⬛§a⬜⬜⬜⬜⬜").setScore(0);
					}else if(curruserperc >= 51 && curruserperc <= 60) {
						o.getScore("§4§c⬛⬛⬛⬛⬛§e⬛§a⬜⬜⬜⬜").setScore(0);
					}else if(curruserperc >= 61 && curruserperc <= 70) {
						o.getScore("§4§c⬛⬛⬛⬛⬛⬛§e⬛§a⬜⬜⬜").setScore(0);
					}else if(curruserperc >= 71 && curruserperc <= 80) {
						o.getScore("§4§c⬛⬛⬛⬛⬛⬛⬛§e⬛§a⬜⬜").setScore(0);
					}else if(curruserperc >= 81 && curruserperc <= 90) {
						o.getScore("§4§c⬛⬛⬛⬛⬛⬛⬛⬛§e⬛§a⬜").setScore(0);
					}else if(curruserperc >= 91 && curruserperc <= 100) {
						o.getScore("§4§c⬛⬛⬛⬛⬛⬛⬛⬛⬛§e⬛").setScore(0);
					}else {
						o.getScore("§4§c⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛").setScore(0);
					}
				}else if(sbmain >= 6 && sbmain <= 10) {
					DecimalFormat df = new DecimalFormat("#.##");
					o.getScore("§7CPU load: (§a" + cpucores + " §7Cores)").setScore(7);
					o.getScore("  §1§a" + df.format(cpuload) + "§7% usage").setScore(6);
					o.getScore("§7Ramload: §a").setScore(5);
					o.getScore("  §2§a" + ramusage + "§7MB /§c " + ramtotal + "§7MB").setScore(4);
					o.getScore("§7Playerload Network:").setScore(3);
					o.getScore("  §3§a" + pusergen + " §7Players").setScore(2);
					o.getScore("§7Playerload " + APIs.getServerName()).setScore(1);
					o.getScore("  §4§a" + Bukkit.getOnlinePlayers().size() + " §7Players").setScore(0);
				}
			}
		}else {
			if(getSB(p) == 0) {
				//do nothing - as this is the off-state of the scoreboard
			}else if(getSB(p) == 1 || getSB(p) == 2) {
				if(sbmain >= 0 && sbmain <= 5) {
					o.getScore("§7Player:").setScore(7);
					if(userperc <= 20) {
						o.getScore("  §7(§9" + pusercurr + "§7) §a" + pusergen + " §7/§c " + pusermax).setScore(6);
					}else if(userperc >= 21 && userperc <= 40) {
						o.getScore("  §7(§9" + pusercurr + "§7) §2" + pusergen + " §7/§c " + pusermax).setScore(6);
					}else if(userperc >= 41 && userperc <= 60) {
						o.getScore("  §7(§9" + pusercurr + "§7) §e" + pusergen + " §7/§c " + pusermax).setScore(6);
					}else if(userperc >= 61 && userperc <= 80) {
						o.getScore("  §7(§9" + pusercurr + "§7) §6" + pusergen + " §7/§c " + pusermax).setScore(6);
					}else if(userperc >= 81 && userperc <= 90) {
						o.getScore("  §7(§9" + pusercurr + "§7) §c" + pusergen + " §7/§c " + pusermax).setScore(6);
					}else if(userperc >= 91) {
						o.getScore("  §7(§9" + pusercurr + "§7) §4" + pusergen + " §7/§c " + pusermax).setScore(6);
					}
					o.getScore("  §a").setScore(5);
					o.getScore("§7Ping:").setScore(4);
					o.getScore("  §a" + ping + "§7ms").setScore(3);
					o.getScore("  §b").setScore(2);
					o.getScore(APIs.returnStringReady(p, "scoreboard.sideboard.rank")).setScore(1);
					if (pu.inGroup("Developer")) {
					    o.getScore("  §dDeveloper").setScore(0);
					}else if (pu.inGroup("PMan")) {
					    o.getScore("  §6Projectmanager").setScore(0);
					}else if (pu.inGroup("CMan")) {
					    o.getScore("  §2Community Manager").setScore(0);
					}else if (pu.inGroup("AMan")) {
					    o.getScore("  §4Administrations Manager").setScore(0);
					}else if (pu.inGroup("Admin")) {
					    o.getScore("  §cAdministrator").setScore(0);
					}else if (pu.inGroup("Support")) {
					    o.getScore("  §9Support").setScore(0);
					}else if (pu.inGroup("Translator")) {
						o.getScore("  §eTranslator").setScore(0);
					}else if (pu.inGroup("Mod")) {
					    o.getScore("  §aModerator").setScore(0);
					}else if (pu.inGroup("Builder")) {
					    o.getScore("  §bBuilder").setScore(0);
					}else if (pu.inGroup("RLTM")) {
					    o.getScore("  §3Retired Legend Team Member").setScore(0);
					}else if (pu.inGroup("RTM")) {
					    o.getScore("  §3Retired Team Member").setScore(0);
					}else if (pu.inGroup("Partner")) {
					    o.getScore("  §aPartner").setScore(0);
					}else if (pu.inGroup("Beta")) {
					    o.getScore("  §5Beta-Tester").setScore(0);
					}else if (pu.inGroup("Patron")) {
					    o.getScore("  §ePatron").setScore(0);
					}else if (pu.inGroup("NitroBooster")) {
					    o.getScore("  §5Nitro Booster").setScore(0);
					}else if (pu.inGroup("Friend")) {
					    o.getScore("  §3Friend").setScore(0);
					}else {
						o.getScore("  §7Player").setScore(0);
					}
				}else if(sbmain >= 6 && sbmain <= 10) {
					o.getScore("§7Server:").setScore(7);
					o.getScore("  §a" + APIs.getServerName() + " §7/§a " + APIs.getServerId()).setScore(6);
					o.getScore("  §9").setScore(5);
					o.getScore(APIs.returnStringReady(p, "scoreboard.sideboard.money")).setScore(4);
					o.getScore("  §a" + money + "§7 Coins").setScore(3);
					o.getScore("  §8").setScore(2);
					o.getScore(APIs.returnStringReady(p, "scoreboard.sideboard.playerid")).setScore(1);
					o.getScore("  §7" + igpre(p) + " §9" + igid(p)).setScore(0);
				}
			}else if(getSB(p) == 3) {
				if(sbmain >= 0 && sbmain <= 5) {
					o.getScore("§7Newest Report").setScore(9);
					o.getScore("§a§b§c").setScore(8);
					o.getScore("§7Reporter:").setScore(7);
					o.getScore("  §1§a" + retLatestReport("reporter")).setScore(6);
					o.getScore("§7Perpetrator:").setScore(5);
					o.getScore("  §2§c" + retLatestReport("perpetrator")).setScore(4);
					o.getScore("§7Server:").setScore(3);
					o.getScore("  §3§a" + retLatestReport("server")).setScore(2);
					o.getScore("§7Reason:").setScore(1);
					o.getScore("  §4§a" + retLatestReport("reason")).setScore(0);
				}else if(sbmain >= 6 && sbmain <= 10) {
					o.getScore("§7Reports total " + APIs.getServerName()).setScore(7);
					o.getScore("§1  §a" + retRepsTotCuSe() + " §7Reports").setScore(6);
					o.getScore("§7Reports today " + APIs.getServerName()).setScore(5);
					o.getScore("§2  §a" + retRepsTodCuSe() + " §7Reports").setScore(4);
					o.getScore("§7Reports total Network").setScore(3);
					o.getScore("§3  §a" + retRepstotal() + " §7Reports").setScore(2);
					o.getScore("§7Reports today Network").setScore(1);
					o.getScore("§4  §a" + retRepsToday() + " §7Reports").setScore(0);
				}
			}else if(getSB(p) == 4) {
				Runtime runtime = Runtime.getRuntime();
				PerformanceMonitor cpu = new PerformanceMonitor();
				int cpucores = runtime.availableProcessors();
				double cpuload = cpu.getCpuUsage();
				long ramusage = (runtime.totalMemory() - runtime.freeMemory()) / 1048576L;
				long ramtotal = runtime.totalMemory() / 1048576L;
				float rampercentage = (ramusage * 100/ramtotal);
				float networkuserperc = (pusergen * 100/pusermax);
				float curruserperc = (pusercurr * 100/pusermax);
				
				if(sbmain >= 0 && sbmain <= 5) {
					o.getScore("§7CPU load: (§a" + cpucores + " §7Cores)").setScore(7);
					if(cpuload <= 10) {
						o.getScore("§1§e⬛§a⬜⬜⬜⬜⬜⬜⬜⬜⬜").setScore(6);
					}else if(cpuload >= 11 && cpuload <= 20) {
						o.getScore("§1§c⬛§e⬛§a⬜⬜⬜⬜⬜⬜⬜⬜").setScore(6);
					}else if(cpuload >= 21 && cpuload <= 30) {
						o.getScore("§1§c⬛⬛§e⬛§a⬜⬜⬜⬜⬜⬜⬜").setScore(6);
					}else if(cpuload >= 31 && cpuload <= 40) {
						o.getScore("§1§c⬛⬛⬛§e⬛§a⬜⬜⬜⬜⬜⬜").setScore(6);
					}else if(cpuload >= 41 && cpuload <= 50) {
						o.getScore("§1§c⬛⬛⬛⬛§e⬛§a⬜⬜⬜⬜⬜").setScore(6);
					}else if(cpuload >= 51 && cpuload <= 60) {
						o.getScore("§1§c⬛⬛⬛⬛⬛§e⬛§a⬜⬜⬜⬜").setScore(6);
					}else if(cpuload >= 61 && cpuload <= 70) {
						o.getScore("§1§c⬛⬛⬛⬛⬛⬛§e⬛§a⬜⬜⬜").setScore(6);
					}else if(cpuload >= 71 && cpuload <= 80) {
						o.getScore("§1§c⬛⬛⬛⬛⬛⬛⬛§e⬛§a⬜⬜").setScore(6);
					}else if(cpuload >= 81 && cpuload <= 90) {
						o.getScore("§1§c⬛⬛⬛⬛⬛⬛⬛⬛§e⬛§a⬜").setScore(6);
					}else if(cpuload >= 91 && cpuload <= 100) {
						o.getScore("§1§c⬛⬛⬛⬛⬛⬛⬛⬛⬛§e⬛").setScore(6);
					}else {
						o.getScore("§1§c⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛").setScore(6);
					}
					o.getScore("§7Ramload:").setScore(5);
					if(rampercentage <= 10) {
						o.getScore("§2§e⬛§a⬜⬜⬜⬜⬜⬜⬜⬜⬜").setScore(4);
					}else if(rampercentage >= 11 && rampercentage <= 20) {
						o.getScore("§2§c⬛§e⬛§a⬜⬜⬜⬜⬜⬜⬜⬜").setScore(4);
					}else if(rampercentage >= 21 && rampercentage <= 30) {
						o.getScore("§2§c⬛⬛§e⬛§a⬜⬜⬜⬜⬜⬜⬜").setScore(4);
					}else if(rampercentage >= 31 && rampercentage <= 40) {
						o.getScore("§2§c⬛⬛⬛§e⬛§a⬜⬜⬜⬜⬜⬜").setScore(4);
					}else if(rampercentage >= 41 && rampercentage <= 50) {
						o.getScore("§2§c⬛⬛⬛⬛§e⬛§a⬜⬜⬜⬜⬜").setScore(4);
					}else if(rampercentage >= 51 && rampercentage <= 60) {
						o.getScore("§2§c⬛⬛⬛⬛⬛§e⬛§a⬜⬜⬜⬜").setScore(4);
					}else if(rampercentage >= 61 && rampercentage <= 70) {
						o.getScore("§2§c⬛⬛⬛⬛⬛⬛§e⬛§a⬜⬜⬜").setScore(4);
					}else if(rampercentage >= 71 && rampercentage <= 80) {
						o.getScore("§2§c⬛⬛⬛⬛⬛⬛⬛§e⬛§a⬜⬜").setScore(4);
					}else if(rampercentage >= 81 && rampercentage <= 90) {
						o.getScore("§2§c⬛⬛⬛⬛⬛⬛⬛⬛§e⬛§a⬜").setScore(4);
					}else if(rampercentage >= 91 && rampercentage <= 100) {
						o.getScore("§2§c⬛⬛⬛⬛⬛⬛⬛⬛⬛§e⬛").setScore(4);
					}else {
						o.getScore("§2§c⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛").setScore(4);
					}
					o.getScore("§7Playerload Network:").setScore(3);
					if(networkuserperc <= 10) {
						o.getScore("§3§e⬛§a⬜⬜⬜⬜⬜⬜⬜⬜⬜").setScore(2);
					}else if(networkuserperc >= 11 && networkuserperc <= 20) {
						o.getScore("§3§c⬛§e⬛§a⬜⬜⬜⬜⬜⬜⬜⬜").setScore(2);
					}else if(networkuserperc >= 21 && networkuserperc <= 30) {
						o.getScore("§3§c⬛⬛§e⬛§a⬜⬜⬜⬜⬜⬜⬜").setScore(2);
					}else if(networkuserperc >= 31 && networkuserperc <= 40) {
						o.getScore("§3§c⬛⬛⬛§e⬛§a⬜⬜⬜⬜⬜⬜").setScore(2);
					}else if(networkuserperc >= 41 && networkuserperc <= 50) {
						o.getScore("§3§c⬛⬛⬛⬛§e⬛§a⬜⬜⬜⬜⬜").setScore(2);
					}else if(networkuserperc >= 51 && networkuserperc <= 60) {
						o.getScore("§3§c⬛⬛⬛⬛⬛§e⬛§a⬜⬜⬜⬜").setScore(2);
					}else if(networkuserperc >= 61 && networkuserperc <= 70) {
						o.getScore("§3§c⬛⬛⬛⬛⬛⬛§e⬛§a⬜⬜⬜").setScore(2);
					}else if(networkuserperc >= 71 && networkuserperc <= 80) {
						o.getScore("§3§c⬛⬛⬛⬛⬛⬛⬛§e⬛§a⬜⬜").setScore(2);
					}else if(networkuserperc >= 81 && networkuserperc <= 90) {
						o.getScore("§3§c⬛⬛⬛⬛⬛⬛⬛⬛§e⬛§a⬜").setScore(2);
					}else if(networkuserperc >= 91 && networkuserperc <= 100) {
						o.getScore("§3§c⬛⬛⬛⬛⬛⬛⬛⬛⬛§e⬛").setScore(2);
					}else {
						o.getScore("§3§c⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛").setScore(2);
					}
					o.getScore("§7Playerload " + APIs.getServerName()).setScore(1);
					if(curruserperc <= 10) {
						o.getScore("§4§e⬛§a⬜⬜⬜⬜⬜⬜⬜⬜⬜").setScore(0);
					}else if(curruserperc >= 11 && curruserperc <= 20) {
						o.getScore("§4§c⬛§e⬛§a⬜⬜⬜⬜⬜⬜⬜⬜").setScore(0);
					}else if(curruserperc >= 21 && curruserperc <= 30) {
						o.getScore("§4§c⬛⬛§e⬛§a⬜⬜⬜⬜⬜⬜⬜").setScore(0);
					}else if(curruserperc >= 31 && curruserperc <= 40) {
						o.getScore("§4§c⬛⬛⬛§e⬛§a⬜⬜⬜⬜⬜⬜").setScore(0);
					}else if(curruserperc >= 41 && curruserperc <= 50) {
						o.getScore("§4§c⬛⬛⬛⬛§e⬛§a⬜⬜⬜⬜⬜").setScore(0);
					}else if(curruserperc >= 51 && curruserperc <= 60) {
						o.getScore("§4§c⬛⬛⬛⬛⬛§e⬛§a⬜⬜⬜⬜").setScore(0);
					}else if(curruserperc >= 61 && curruserperc <= 70) {
						o.getScore("§4§c⬛⬛⬛⬛⬛⬛§e⬛§a⬜⬜⬜").setScore(0);
					}else if(curruserperc >= 71 && curruserperc <= 80) {
						o.getScore("§4§c⬛⬛⬛⬛⬛⬛⬛§e⬛§a⬜⬜").setScore(0);
					}else if(curruserperc >= 81 && curruserperc <= 90) {
						o.getScore("§4§c⬛⬛⬛⬛⬛⬛⬛⬛§e⬛§a⬜").setScore(0);
					}else if(curruserperc >= 91 && curruserperc <= 100) {
						o.getScore("§4§c⬛⬛⬛⬛⬛⬛⬛⬛⬛§e⬛").setScore(0);
					}else {
						o.getScore("§4§c⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛").setScore(0);
					}
				}else if(sbmain >= 6 && sbmain <= 10) {
					DecimalFormat df = new DecimalFormat("#.##");
					o.getScore("§7CPU load: (§a" + cpucores + " §7Cores)").setScore(7);
					o.getScore("  §1§a" + df.format(cpuload) + "§7% usage").setScore(6);
					o.getScore("§7Ramload: §a").setScore(5);
					o.getScore("  §2§a" + ramusage + "§7MB /§c " + ramtotal + "§7MB").setScore(4);
					o.getScore("§7Playerload Network:").setScore(3);
					o.getScore("  §3§a" + pusergen + " §7Players").setScore(2);
					o.getScore("§7Playerload " + APIs.getServerName()).setScore(1);
					o.getScore("  §4§a" + Bukkit.getOnlinePlayers().size() + " §7Players").setScore(0);
				}
			}
		}
		p.setScoreboard(sb);
		
		Team pm = getTeam(sb, "00000", retPrefix("pm", "prefix_tab"), ChatColor.GRAY); //gold
		Team cman = getTeam(sb, "00010", retPrefix("cman", "prefix_tab"), ChatColor.GRAY); //dark-green
		Team aman = getTeam(sb, "00020", retPrefix("aman", "prefix_tab"), ChatColor.GRAY); //dark-red
		Team dev = getTeam(sb, "00030", retPrefix("dev", "prefix_tab"), ChatColor.GRAY); //light-purple
		Team admin = getTeam(sb, "00040", retPrefix("admin", "prefix_tab"), ChatColor.GRAY); //red
		Team mod = getTeam(sb, "00050", retPrefix("mod", "prefix_tab"), ChatColor.GRAY); //green
		Team translator = getTeam(sb, "00055", retPrefix("translator", "prefix_tab"), ChatColor.GRAY);
		Team support = getTeam(sb, "00060", retPrefix("support", "prefix_tab"), ChatColor.GRAY); //blue
		Team builder = getTeam(sb, "00070", retPrefix("builder", "prefix_tab"), ChatColor.GRAY); //aqua
		Team rltm = getTeam(sb, "00080", retPrefix("rltm", "prefix_tab"), ChatColor.GRAY); //dark-purple
		Team rtm = getTeam(sb, "00090", retPrefix("rtm", "prefix_tab"), ChatColor.GRAY); //dark-purple
		Team partner = getTeam(sb, "00100", retPrefix("partner", "prefix_tab"), ChatColor.GRAY); //green
		Team beta = getTeam(sb, "00110", retPrefix("beta", "prefix_tab"), ChatColor.GRAY); //dark-blue & red
		Team patron = getTeam(sb, "00120", retPrefix("patron", "prefix_tab"), ChatColor.GRAY); //dark-aqua
		Team nitrobooster = getTeam(sb, "00130", retPrefix("dcnitro", "prefix_tab"), ChatColor.GRAY); //dark-blue
		Team freund = getTeam(sb, "00140", retPrefix("freund", "prefix_tab"), ChatColor.GRAY); //dark-aqua
		Team spieler = getTeam(sb, "00150", retPrefix("spieler", "prefix_tab"), ChatColor.GRAY); //gray
		Team tafk = getTeam(sb, "00160", retPrefix("tafk", "prefix_tab"), ChatColor.GRAY);
		Team afk = getTeam(sb, "00170", retPrefix("safk", "prefix_tab"), ChatColor.GRAY);
		
		for(Player all : Bukkit.getOnlinePlayers()) {
			PermissionUser pp = PermissionsEx.getUser(all);
			HashMap<String, Object> hm = new HashMap<>();
	    	hm.put("uuid", all.getUniqueId().toString().replace("-", ""));
	    	ResultSet rs = Main.mysql.select("redicore_userstats", hm);
	    	rs.next();
			if(pp.inGroup("PMan")) {
				if(rs.getBoolean("loggedin")) {
					if(isAFK(all)) {
						tafk.addPlayer(all);
						all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| ID§7: §a" + igid(all) + " §f" + igpre(all));
					}else {
						pm.addPlayer(all);
						all.setDisplayName(retPrefix("pm", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("pm", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}else {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName("§9AFK §7| " + all.getName() + "7| ID: §a" + igid(all) + " " + igpre(all));
					}else {
						spieler.addPlayer(all);
						all.setDisplayName(retPrefix("spieler", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("spieler", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}
			}else if(pp.inGroup("Developer")) {
				if(rs.getBoolean("loggedin")) {
					if(isAFK(all)) {
						tafk.addPlayer(all);
						all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| ID§7: §a" + igid(all) + " §f" + igpre(all));
					}else {
						dev.addPlayer(all);
						all.setDisplayName(retPrefix("dev", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("dev", "prefix_tab") + all.getName() + " §7| ID§7: §a" + igid(all) + " §f" + igpre(all));
					}
				}else {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName("§9AFK §7| " + all.getName() + "7| ID: §a" + igid(all) + " " + igpre(all));
					}else {
						spieler.addPlayer(all);
						all.setDisplayName(retPrefix("spieler", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("spieler", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}
			}else if(pp.inGroup("CMan")) {
				if(rs.getBoolean("loggedin")) {
					if(isAFK(all)) {
						tafk.addPlayer(all);
						all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| ID§7: §a" + igid(all) + " §f" + igpre(all));
					}else {
						cman.addPlayer(all);
						all.setDisplayName(retPrefix("cman", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("cman", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}else {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName("§9AFK §7| " + all.getName() + "7| ID: §a" + igid(all) + " " + igpre(all));
					}else {
						spieler.addPlayer(all);
						all.setDisplayName(retPrefix("spieler", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("spieler", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}
			}else if(pp.inGroup("AMan")) {
				if(rs.getBoolean("loggedin")) {
					if(isAFK(all)) {
						tafk.addPlayer(all);
						all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| ID§7: §a" + igid(all) + " §f" + igpre(all));
					}else {
						aman.addPlayer(all);
						all.setDisplayName(retPrefix("aman", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("aman", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}else {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName("§9AFK §7| " + all.getName() + "7| ID: §a" + igid(all) + " " + igpre(all));
					}else {
						spieler.addPlayer(all);
						all.setDisplayName(retPrefix("spieler", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("spieler", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}
			}else if(pp.inGroup("Admin")) {
				if(rs.getBoolean("loggedin")) {
					if(isAFK(all)) {
						tafk.addPlayer(all);
						all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| ID§7: §a" + igid(all) + " §f" + igpre(all));
					}else {
						admin.addPlayer(all);
						all.setDisplayName(retPrefix("admin", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("admin", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}else {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName("§9AFK §7| " + all.getName() + "7| ID: §a" + igid(all) + " " + igpre(all));
					}else {
						spieler.addPlayer(all);
						all.setDisplayName(retPrefix("spieler", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("spieler", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}
			}else if(pp.inGroup("Moderator")) {
				if(rs.getBoolean("loggedin")) {
					if(isAFK(all)) {
						tafk.addPlayer(all);
						all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| ID§7: §a" + igid(all) + " §f" + igpre(all));
					}else {
						mod.addPlayer(all);
						all.setDisplayName(retPrefix("mod", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("mod", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}else {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName("§9AFK §7| " + all.getName() + "7| ID: §a" + igid(all) + " " + igpre(all));
					}else {
						spieler.addPlayer(all);
						all.setDisplayName(retPrefix("spieler", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("spieler", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}
			}else if(pp.inGroup("Translator")) {
				if(rs.getBoolean("loggedin")) {
					if(isAFK(all)) {
						tafk.addPlayer(all);
						all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| ID§7: §a" + igid(all) + " §f" + igpre(all));
					}else {
						translator.addPlayer(all);
						all.setDisplayName(retPrefix("translator", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("translator", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}else {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName("§9AFK §7| " + all.getName() + "7| ID: §a" + igid(all) + " " + igpre(all));
					}else {
						spieler.addPlayer(all);
						all.setDisplayName(retPrefix("spieler", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("spieler", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}
			}else if(pp.inGroup("support")) {
				if(rs.getBoolean("loggedin")) {
					if(isAFK(all)) {
						tafk.addPlayer(all);
						all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| ID§7: §a" + igid(all) + " §f" + igpre(all));
					}else {
						support.addPlayer(all);
						all.setDisplayName(retPrefix("support", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("support", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}else {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName("§9AFK §7| " + all.getName() + "7| ID: §a" + igid(all) + " " + igpre(all));
					}else {
						spieler.addPlayer(all);
						all.setDisplayName(retPrefix("spieler", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("spieler", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}
			}else if(pp.inGroup("builder")) {
				if(rs.getBoolean("loggedin")) {
					if(isAFK(all)) {
						tafk.addPlayer(all);
						all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| ID§7: §a" + igid(all) + " §f" + igpre(all));
					}else {
						builder.addPlayer(all);
						all.setDisplayName(retPrefix("builder", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("builder", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}else {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName("§9AFK §7| " + all.getName() + "7| ID: §a" + igid(all) + " " + igpre(all));
					}else {
						spieler.addPlayer(all);
						all.setDisplayName(retPrefix("spieler", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("spieler", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}
			}else if(pp.inGroup("RetiredLegend")) {
				if(isAFK(all)) {
					afk.addPlayer(all);
					all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| ID§7: §a" + igid(all) + " §f" + igpre(all));
				}else {
					rltm.addPlayer(all);
					all.setDisplayName(retPrefix("rltm", "prefix_chat") + all.getName());
					all.setPlayerListName(retPrefix("rltm", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
				}
			}else if(pp.inGroup("Retired")) {
				if(isAFK(all)) {
					afk.addPlayer(all);
					all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| ID§7: §a" + igid(all) + " §f" + igpre(all));
				}else {
					rtm.addPlayer(all);
					all.setDisplayName(retPrefix("rtm", "prefix_chat") + all.getName());
					all.setPlayerListName(retPrefix("rtm", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
				}
			}else if(pp.inGroup("partner")) {
				if(rs.getBoolean("loggedin")) {
					afk.addPlayer(all);
					all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| ID§7: §a" + igid(all) + " §f" + igpre(all));
				}else {
					partner.addPlayer(all);
					all.setDisplayName(retPrefix("partner", "prefix_chat") + all.getName());
					all.setPlayerListName(retPrefix("partner", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
				}
			}else if(pp.inGroup("beta")) {
				if(isAFK(all)) {
					afk.addPlayer(all);
					all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| ID§7: §a" + igid(all) + " §f" + igpre(all));
				}else {
					beta.addPlayer(all);
					all.setDisplayName(retPrefix("beta", "prefix_chat") + all.getName());
					all.setPlayerListName(retPrefix("beta", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
				}
			}else if(pp.inGroup("Patron")) {
				if(rs.getBoolean("loggedin")) {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| ID§7: §a" + igid(all) + " §f" + igpre(all));
					}else {
						patron.addPlayer(all);
						all.setDisplayName(retPrefix("patron", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("patron", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}else {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName("§9AFK §7| " + all.getName() + "7| ID: §a" + igid(all) + " " + igpre(all));
					}else {
						spieler.addPlayer(all);
						all.setDisplayName(retPrefix("spieler", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("spieler", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}
			}else if(pp.inGroup("nitrobooster")) {
				if(rs.getBoolean("loggedin")) {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| ID§7: §a" + igid(all) + " §f" + igpre(all));
					}else {
						nitrobooster.addPlayer(all);
						all.setDisplayName(retPrefix("dcnitro", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("dcnitro", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}else {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName("§9AFK §7| " + all.getName() + "7| ID: §a" + igid(all) + " " + igpre(all));
					}else {
						spieler.addPlayer(all);
						all.setDisplayName(retPrefix("spieler", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("spieler", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}
			}else if(pp.inGroup("freund")) {
				if(rs.getBoolean("loggedin")) {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| ID§7: §a" + igid(all) + " §f" + igpre(all));
					}else {
						freund.addPlayer(all);
						all.setDisplayName(retPrefix("freund", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("freund", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}else {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName("§9AFK §7| " + all.getName() + "7| ID: §a" + igid(all) + " " + igpre(all));
					}else {
						spieler.addPlayer(all);
						all.setDisplayName(retPrefix("spieler", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("spieler", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}
			}else {
				if(isAFK(all)) {
					afk.addPlayer(all);
					all.setPlayerListName("§9AFK §7| " + all.getName() + "7| ID: §a" + igid(all) + " " + igpre(all));
				}else {
					spieler.addPlayer(all);
					all.setDisplayName(retPrefix("spieler", "prefix_chat") + all.getName());
					all.setPlayerListName(retPrefix("spieler", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
				}
			}
		}
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if(p.hasPermission("mlps.colorChat")) {
			e.setFormat(p.getDisplayName() + "§7 (§9" + igid(p) + "§7): " + ChatColor.translateAlternateColorCodes('&', e.getMessage().replace("%", "%%")));
		}else {
			e.setFormat(p.getDisplayName() + "§7 (§9" + igid(p) + "§7): " + e.getMessage().replace("%", "%%"));
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) throws IllegalStateException, IllegalArgumentException, SQLException {
		setScoreboard(e.getPlayer());
		e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(APIs.returnStringReady(e.getPlayer(), "event.join.welcomemessage").replace("%server", APIs.getServerName()).replace("%displayer", e.getPlayer().getDisplayName())));
	}
	
	private String igid(Player p) {
		String s = "";
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redicore_userstats WHERE uuid = ?");
			ps.setString(1, p.getUniqueId().toString().replaceAll("-", ""));
		    ResultSet rs = ps.executeQuery();
		    rs.next();
		    s = rs.getString("userid");
		}catch (SQLException e) { }
		return s;
	}
	
	private static String igpre(Player p) {
		String prefix = "";
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redicore_userstats WHERE uuid = ?");
			ps.setString(1, p.getUniqueId().toString().replaceAll("-", ""));
			ResultSet rs = ps.executeQuery();
			rs.next();
			String pre = rs.getString("userprefix");
			if(pre.equalsIgnoreCase("RESET")) {
				prefix = "";
			}else {
				prefix = pre;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return prefix;
	}
	
	private static boolean isAFK(Player p) {
		boolean boo = false;
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redicore_userstats WHERE uuid = ?");
			ps.setString(1, p.getUniqueId().toString().replace("-", ""));
			ResultSet rs = ps.executeQuery();
			rs.next();
			boo = rs.getBoolean("afk");
		}catch (SQLException e) { e.printStackTrace(); }
		return boo;
	}
	
	public Team getTeam(Scoreboard sb, String Team, String prefix, ChatColor cc) {
		Team team = sb.registerNewTeam(Team);
		team.setPrefix(prefix);
		team.setColor(cc);
		return team;
	}
	
	private int getSB(Player p) {
		int i = 0;
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redicore_userstats WHERE uuid = ?");
			ps.setString(1, p.getUniqueId().toString().replace("-", ""));
			ResultSet rs = ps.executeQuery();
			rs.next();
			i = rs.getInt("scoreboard");
			ps.close();
			rs.close();
		}catch (SQLException e) { }
		return i;
	}
	
	private String retDate(String format) {
		SimpleDateFormat time = new SimpleDateFormat(format);
		return time.format(new Date());
	}
	
	private int retRepsToday() {
		int reports = 0;
		try {
			String date = retDate("yyyy.MM.dd");
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redicore_reports WHERE time_string = ?");
			ps.setString(1, date);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				reports++;
			}
			rs.close();
			ps.close();
		}catch (SQLException e) {
			e.printStackTrace();
			reports = 9999;
		}
		return reports;
	}
	
	private int retRepstotal() {
		int reports = 0;
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redicore_reports");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				reports++;
			}
			rs.close();
			ps.close();
		}catch (SQLException e) {
			e.printStackTrace();
			reports = 9999;
		}
		return reports;
	}
	
	private int retRepsTotCuSe() {
		int reports = 0;
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redicore_reports WHERE server = ?");
			ps.setString(1, APIs.getServerName());
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				reports++;
			}
			rs.close();
			ps.close();
		}catch (SQLException e) {
			e.printStackTrace();
			reports = 9999;
		}
		return reports;
	}
	
	private int retRepsTodCuSe() {
		int reports = 0;
		try {
			String csdate = retDate("yyyy.MM.dd");
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redicore_reports WHERE time_string = ? AND server = ?");
			ps.setString(1, csdate);
			ps.setString(2, APIs.getServerName());
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				reports++;
			}
			rs.close();
			ps.close();
		}catch (SQLException e) {
			e.printStackTrace();
			reports = 9999;
		}
		return reports;
	}
	
	private String retLatestReport(String type) {
		String report = "";
		String csdate = retDate("yyyy.MM.dd");
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redicore_reports ORDER BY id DESC");
			ResultSet rs = ps.executeQuery();
			rs.next();
			if(type.equalsIgnoreCase("reporter")) {
                if(rs.getString("time_string").equalsIgnoreCase(csdate)) {
                    report = rs.getString("user_reporter");
                }else {
                    report = "n/a";
                }
            }else if(type.equalsIgnoreCase("perpetrator")) {
                if(rs.getString("time_string").equalsIgnoreCase(csdate)) {
                    report = rs.getString("user_target");
                }else {
                    report = "n/a";
                }
            }else if(type.equalsIgnoreCase("reason")) {
                if(rs.getString("time_string").equalsIgnoreCase(csdate)) {
                    if(rs.getString("reason").length() <= 15) {
                        report = rs.getString("reason");
                    }else {
                        report = rs.getString("reason").substring(0, 16);
                    }
                }else {
                    report = "n/a";
                }
            }else if(type.equalsIgnoreCase("server")) {
                if(rs.getString("time_string").equalsIgnoreCase(csdate)) {
                    report = rs.getString("server");
                }else {
                    report = "n/a";
                }
            }else {
                report = "ERRORDEF1a";
            }
			rs.close();
			ps.close();
		}catch (SQLException e) {
			report = "ERRORDEF2a";
			e.printStackTrace();
		}
		return report;
	}
	
	private String retPrefix(String rank, String type) {
		String prefix = "";
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redicore_ranks WHERE rank = ?");
			ps.setString(1, rank);
			ResultSet rs = ps.executeQuery();
			rs.next();
			prefix = rs.getString(type);
			rs.close();
			ps.close();
		}catch (SQLException e) {
			prefix = "§0ERR";
		}
		return prefix.replace("&", "§");
	}
	
	public void sbSched(int delay, int periodsb, int periodot) {
		new BukkitRunnable() {
			@Override
			public void run() {
				for(Player all : Bukkit.getOnlinePlayers()) {
					try {
						setScoreboard(all);
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}.runTaskTimer(Main.instance, delay, periodsb);
		
		new BukkitRunnable() {
			@Override
			public void run() {
				Serverupdater.updateServer();
				Serverupdater.clearlag();
				SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
				String stime = time.format(new Date());
				for(Player all : Bukkit.getOnlinePlayers()) {
					all.setPlayerListHeaderFooter(APIs.returnStringReady(all, "scoreboard.playerlist.top").replace("|", "\n"), APIs.returnStringReady(all, "scoreboard.playerlist.bottom").replace("|", "\n").replace("%time", stime).replace("%servername", APIs.getServerName()).replace("%serverid", APIs.getServerId()));
				}
				sbmain++;
				if(sbmain == 10) {
					sbmain = 0;
				}
			}
		}.runTaskTimerAsynchronously(Main.instance, delay, periodot);
	}
}