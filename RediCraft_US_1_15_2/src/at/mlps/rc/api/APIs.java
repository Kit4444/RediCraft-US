package at.mlps.rc.api;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.IOUtils;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import at.mlps.rc.main.Main;
import at.mlps.rc.mysql.lb.MySQL;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class APIs {
	
	public static void loadConfig() {
		File file = new File("plugins/RCUSS/language.yml");
		if(!file.exists()) { try { file.createNewFile(); } catch (IOException e) { e.printStackTrace(); } }
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		//cfg.addDefault("Language..", "&");
		//key = Language.<LangKey>.<cmd|event|misc>.<whatexactly>.state
		
		//english
		cfg.addDefault("Language.EN.noPerm", "&cInsufficent Permissions!");
		cfg.addDefault("Language.EN.notAvailable", "&cThis is currently not available.");
		cfg.addDefault("Language.EN.usage", "&7Usage: ");
		cfg.addDefault("Language.EN.cmd.login.success", "&7You are now logged in.");
		cfg.addDefault("Language.EN.cmd.logout.success", "&7You are now logged out.");
		cfg.addDefault("Language.EN.cmd.login.already", "&7You are already logged in.");
		cfg.addDefault("Language.EN.cmd.logout.already", "&7You are already logged out.");
		cfg.addDefault("Language.EN.cmd.tg.visible", "&7Your group is now visible.");
		cfg.addDefault("Language.EN.cmd.tg.invisible", "&7Your group is now invisible.");
		cfg.addDefault("Language.EN.cmd.afk.join", "&7You are now AFK.");
		cfg.addDefault("Language.EN.cmd.afk.leave", "&7You are not longer AFK.");
		cfg.addDefault("Language.EN.cmd.scoreboard.off", "&7Turned Scoreboard off.");
		cfg.addDefault("Language.EN.cmd.scoreboard.default", "&7Changed Scoreboard to view default.");
		cfg.addDefault("Language.EN.cmd.scoreboard.job", "&7Changed Scoreboard to view job.");
		cfg.addDefault("Language.EN.cmd.scoreboard.admin", "&7Changed Scoreboard to view Admin");
		cfg.addDefault("Language.EN.cmd.scoreboard.data", "&7Changed Scoreboard to view Serverdata.");
		cfg.addDefault("Language.EN.cmd.scoreboard.creajob", "&cYou can't have the jobboard in Creative!");
		cfg.addDefault("Language.EN.cmd.cc.private", "&7You cleared your private chat.");
		cfg.addDefault("Language.EN.cmd.cc.public", "&7You cleared the public chat.");
		cfg.addDefault("Language.EN.cmd.cc.anonymous", "&7You cleared the public chat anonymously.");
		cfg.addDefault("Language.EN.cmd.cc.forall.public", "%displayer &7has cleared the chat.");
		cfg.addDefault("Language.EN.cmd.cc.forall.anonymous", "&7A staff member has cleared the chat.");
		cfg.addDefault("Language.EN.cmd.setid.idalreadyexists", "&7This ID is already in use.");
		cfg.addDefault("Language.EN.cmd.setid.updatesuccessfull", "%displayer &7has now ID &a%id");
		cfg.addDefault("Language.EN.cmd.setid.limitexceeded", "&7The ID should be between &a%minid &7and &a%maxid &7and needs to be &lunique&7.");
		cfg.addDefault("Language.EN.cmd.setid.playernotonline", "%displayer &7is not online");
		cfg.addDefault("Language.EN.cmd.setpf.playernotonline", "%displayer &7is not online");
		cfg.addDefault("Language.EN.cmd.setpf.playerreset", "%displayer &7's prefix has been resetted");
		cfg.addDefault("Language.EN.cmd.setpf.newprefix", "&7Prefix has been updated for %displayer&7. Prefix: &f%prefix");
		cfg.addDefault("Language.EN.cmd.setpf.prefixtoolong", "&7The prefix is only allowed for up to 16 Chars. Used: &c%length");
		cfg.addDefault("Language.EN.cmd.clearlag", "&7Removed &6%entities &7Items on &6%worldcount &7Worlds.");
		cfg.addDefault("Language.EN.cmd.gamemode", "&7Your gamemode has been changed to &a%gamemode");
		cfg.addDefault("Language.EN.cmd.gamemode.notonline", "&7The player is not online.");
		cfg.addDefault("Language.EN.cmd.ping.own", "&7Your ping is &a%ping &7ms");
		cfg.addDefault("Language.EN.cmd.ping.all", "%displayer &7's ping is &a%ping &7ms");
		cfg.addDefault("Language.EN.cmd.ping.notonline", "&7This player is not online.");
		cfg.addDefault("Language.EN.cmd.ping.other", "%displayer &7's ping is &a%ping &7ms");
		cfg.addDefault("Language.EN.cmd.time.set", "&7Worldtime in &a%world &7has been changed to &a%time");
		cfg.addDefault("Language.EN.cmd.time.info", "&7Worldtime in &a%world &7is &a%time");
		cfg.addDefault("Language.EN.cmd.weather.notime", "&7Changed weather in &a%world &7to &a%weather");
		cfg.addDefault("Language.EN.cmd.weather.time", "&7Changed weather in &a%world &7to &a%weather &7for &a%time");
		cfg.addDefault("Language.EN.cmd.weather.clear", "&7Weather cleared in &a%world");
		cfg.addDefault("Language.EN.cmd.openinv", "&7You have opened the Navigator.");
		cfg.addDefault("Language.EN.cmd.blockpm.add", "&7Private Messages are now blocked.");
		cfg.addDefault("Language.EN.cmd.blockpm.remove", "&7Private Messages are now unblocked.");
		cfg.addDefault("Language.EN.cmd.msg.playernotonline", "&7The player is not online.");
		cfg.addDefault("Language.EN.cmd.msg.blockedmsg", "&7You can't message anyone private if you have blocked yourself your MSGs.");
		cfg.addDefault("Language.EN.cmd.msg.bypassmsg", "&7The user has blocked MSGs, you bypassed it.");
		cfg.addDefault("Language.EN.cmd.msg.you", "&cyou");
		cfg.addDefault("Language.EN.cmd.r.noentry", "&7You don't have any outstanding messages to answer.");
		cfg.addDefault("Language.EN.cmd.r.playernotonline", "&7The player is not online.");
		cfg.addDefault("Language.EN.cmd.msg.playerblocked", "§7This player has blocked private messages.");
		cfg.addDefault("Language.EN.cmd.listhomes.homes", "&a%homecount &7saved on disk.");
		cfg.addDefault("Language.EN.cmd.sethome.homeexistsalready", "&7This home exists already");
		cfg.addDefault("Language.EN.cmd.sethome.successfully", "&7Home has been successfully saved. Home: &a%home &7| Count: &a%count");
		cfg.addDefault("Language.EN.cmd.sethome.limitexceeded.bypass", "&7Exceeded &a%maxhomes &7Homes limit. Bypassing it.");
		cfg.addDefault("Language.EN.cmd.sethome.limitexceeded.nobypass", "&7You can't have more than &c%maxhomes Homes&7. Delete homes you don't need anymore.");
		cfg.addDefault("Language.EN.cmd.delhome.successfully", "&7Deleted Home &a%home &7successfully from disk.");
		cfg.addDefault("Language.EN.cmd.delhome.notexisting", "&7This home doesn't exists.");
		cfg.addDefault("Language.EN.cmd.home.nothome", "&7This home doesn't exists.");
		cfg.addDefault("Language.EN.cmd.home.teleport", "&7You has been teleported to &a%home");
		cfg.addDefault("Language.EN.cmd.money.player.own", "&7You have &a%money &7Coins");
		cfg.addDefault("Language.EN.cmd.money.bank.own", "&7You have &a%money &7Coins on your bank");
		cfg.addDefault("Language.EN.cmd.money.player.other", "%displayer &7has &a%money &7Coins");
		cfg.addDefault("Language.EN.cmd.money.bank.other", "%displayer &7has &a%money &7Coins on his bank");
		cfg.addDefault("Language.EN.cmd.setmoney.playernotonline", "&7This player is not online");
		cfg.addDefault("Language.EN.cmd.setmoney.belowzero", "&7You can't set the money below 0 Coins");
		cfg.addDefault("Language.EN.cmd.setmoney.successfull", "&7You have set the money from %displayer &7 to &a%money &7Coins successfull.");
		cfg.addDefault("Language.EN.cmd.addmoney.playernotonline", "&7This player is not online");
		cfg.addDefault("Language.EN.cmd.addmoney.successfull", "&7You have added &a%money &7Coins to %displayer &7successfull.");
		cfg.addDefault("Language.EN.cmd.removemoney.playernotonline", "&7This player is not online");
		cfg.addDefault("Language.EN.cmd.removemoney.successfull", "&7You have removed &a%money &7Coins from %displayer");
		cfg.addDefault("Language.EN.cmd.removemoney.belowzero", "&7You can't set the money below 0 Coins.");
		cfg.addDefault("Language.EN.cmd.pay.playernotonline", "&7This player is not online");
		cfg.addDefault("Language.EN.cmd.pay.successfull", "&7You've paid %displayer &7successfully &a%money &7Coins");
		cfg.addDefault("Language.EN.cmd.pay.belowzero", "&7You can't pay more than you have.");
		cfg.addDefault("Language.EN.cmd.setbankmoney.playernotonline", "&7This player is not online.");
		cfg.addDefault("Language.EN.cmd.setbankmoney.belowzero", "&7You can't set the money below 0 Coins");
		cfg.addDefault("Language.EN.cmd.setbankmoney.successfull", "&7You have set the bankmoney from %displayer &7 to &a%money &7Coins successfull.");
		cfg.addDefault("Language.EN.cmd.bankdeposit.moreaspossible", "&7You can't deposit more as you have.");
		cfg.addDefault("Language.EN.cmd.bankdeposit.successfull", "&7You have deposed &a%money &7Coins to your bank.");
		cfg.addDefault("Language.EN.cmd.bankwithdraw.moreaspossible", "&7You can't deposit more as you have.");
		cfg.addDefault("Language.EN.cmd.bankwithdraw.successfull", "&7You have withdrawn &a%money &7Coins from your bank.");
		cfg.addDefault("Language.EN.cmd.setvillager", "&7You have set the Shopvillager");
		cfg.addDefault("Language.EN.cmd.fly.other", "&7The player is not online.");
		cfg.addDefault("Language.EN.cmd.workbench", "&7You have opened the workbench.");
		cfg.addDefault("Language.EN.cmd.fly.own.true", "&7You can fly now.");
		cfg.addDefault("Language.EN.cmd.fly.own.false", "&7You can't fly anymore.");
		cfg.addDefault("Language.EN.cmd.fly.other.true", "%displayer &7 can now fly.");
		cfg.addDefault("Language.EN.cmd.fly.other.false", "%displayer &7 can't fly anymore.");
		cfg.addDefault("Language.EN.cmd.fly.other.offline", "&7This player is offline.");
		cfg.addDefault("Language.EN.event.shopvillager.open", "&7You have opened the Shop.");
		cfg.addDefault("Language.EN.event.shopvillager.hurt", "&7You can't hurt the villager.");
		cfg.addDefault("Language.EN.event.afk.leave", "&7You are not longer AFK.");
		cfg.addDefault("Language.EN.event.navigator.sendPlayer.success", "&7You has been sent to server &a%server");
		cfg.addDefault("Language.EN.event.navigator.sendPlayer.locked", "&a%server &7is currently locked.");
		cfg.addDefault("Language.EN.event.navigator.sendPlayer.monitorinfo", "&eInfo: This server is currently monitored. Issues or instability may come up!");
		cfg.addDefault("Language.EN.event.navigator.sendPlayer.offline", "&a%server &cis currently offline.");
		cfg.addDefault("Language.EN.event.navigator.sendPlayer.failed", "&cFailed to send you to Server &a%server");
		cfg.addDefault("Language.EN.event.villagershop.fly", "&7You already purchased the fly-mode.");
		cfg.addDefault("Language.EN.event.villagershop.color", "&7You already purchased the Color-Chat");
		cfg.addDefault("Language.EN.event.villagershop.plots", "&7You already purchased the More-Plots");
		cfg.addDefault("Language.EN.event.villagershop.effects", "&7You already purchased the Effects");
		cfg.addDefault("Language.EN.event.villagershop.healwait", "&7You have to wait %time mins to heal you again");
		cfg.addDefault("Language.EN.event.villagershop.healsuccess", "&7You have healed yourself");
		cfg.addDefault("Language.EN.event.villagershop.money", "&7You have &a%money &7Coins");
		cfg.addDefault("Language.EN.event.villagershop.notenoughmoney", "&7You don't have enough money to purchase this.");
		cfg.addDefault("Language.EN.event.villagershop.purchase.effects", "&7You have purchased the effects");
		cfg.addDefault("Language.EN.event.villagershop.purchase.userfly", "&7You have purchased the Fly-Mode");
		cfg.addDefault("Language.EN.event.villagershop.purchase.colorchat", "&7You have purchased the Color-Chat");
		cfg.addDefault("Language.EN.event.villagershop.purchase.plots", "&7You have purchased the More-Plots");
		cfg.addDefault("Language.EN.event.worldteleporter.freebuild", "&7You has been teleported to &aFreebuild");
		cfg.addDefault("Language.EN.event.worldteleporter.nether", "&7You has been teleported to &cNether");
		cfg.addDefault("Language.EN.event.worldteleporter.plotworld", "&7You has been teleported to &aPlotworld");
		cfg.addDefault("Language.EN.event.worldteleporter.theend", "&7You has been teleported to &0The End");
		cfg.addDefault("Language.EN.event.worldteleporter.notset", "&7This spawn isn't setted yet.");
		cfg.addDefault("Language.EN.event.join.welcomemessage", "&7Welcome on &a%server&7, %displayer");
		cfg.addDefault("Language.EN.event.deathevent", "%target &7has been killed by %killer");
		cfg.addDefault("Language.EN.restarter.time.200000", "&7The Server will be restarted in &a4 Hours");
		cfg.addDefault("Language.EN.restarter.time.210000", "&7The Server will be restarted in &a3 Hours");
		cfg.addDefault("Language.EN.restarter.time.220000", "&7The Server will be restarted in &a2 Hours");
		cfg.addDefault("Language.EN.restarter.time.223000", "&7The Server will be restarted in &a90 Minutes");
		cfg.addDefault("Language.EN.restarter.time.230000", "&7The Server will be restarted in &a60 Minutes");
		cfg.addDefault("Language.EN.restarter.time.233000", "&7The Server will be restarted in &a30 Minutes");
		cfg.addDefault("Language.EN.restarter.time.234500", "&7The Server will be restarted in &a15 Minutes");
		cfg.addDefault("Language.EN.restarter.time.235500", "&7The Server will be restarted in &a5 Minutes");
		cfg.addDefault("Language.EN.restarter.time.235600", "&7The Server will be restarted in &e4 Minutes");
		cfg.addDefault("Language.EN.restarter.time.235700", "&7The Server will be restarted in &e3 Minutes");
		cfg.addDefault("Language.EN.restarter.time.235800", "&7The Server will be restarted in &c2 Minutes");
		cfg.addDefault("Language.EN.restarter.time.235900", "&7The Server will be restarted in &c1 Minute");
		cfg.addDefault("Language.EN.restarter.time.235950", "&7The Server will be restarted in &410 Seconds");
		cfg.addDefault("Language.EN.restarter.time.235951", "&7The Server will be restarted in &49 Seconds");
		cfg.addDefault("Language.EN.restarter.time.235952", "&7The Server will be restarted in &48 Seconds");
		cfg.addDefault("Language.EN.restarter.time.235953", "&7The Server will be restarted in &47 Seconds");
		cfg.addDefault("Language.EN.restarter.time.235954", "&7The Server will be restarted in &46 Seconds");
		cfg.addDefault("Language.EN.restarter.time.235955", "&7The Server will be restarted in &45 Seconds");
		cfg.addDefault("Language.EN.restarter.time.235956", "&7The Server will be restarted in &44 Seconds");
		cfg.addDefault("Language.EN.restarter.time.235957", "&7The Server will be restarted in &43 Seconds");
		cfg.addDefault("Language.EN.restarter.time.235958", "&7The Server will be restarted in &42 Seconds");
		cfg.addDefault("Language.EN.restarter.time.235959", "&7The Server will be restarted in &41 Seconds");
		cfg.addDefault("Language.EN.scoreboard.sideboard.money", "&7Money:");
		cfg.addDefault("Language.EN.scoreboard.sideboard.rank", "&7Rank:");
		cfg.addDefault("Language.EN.scoreboard.sideboard.playerid", "&7PlayerID:");
		cfg.addDefault("Language.EN.scoreboard.playerlist.top", "&aRedi&cCraft|&aYour Minecraft Network");
		cfg.addDefault("Language.EN.scoreboard.playerlist.bottom", "&7Time: &a%time|&7Server: &a%servername &7/ &a%serverid");
		
		//german
		cfg.addDefault("Language.DE.noPerm", "&cUnzureichende Berechtigung!");
		cfg.addDefault("Language.DE.notAvailable", "&cDas ist aktuell nicht verfügbar.");
		cfg.addDefault("Language.DE.usage", "&7Verwende: ");
		cfg.addDefault("Language.DE.cmd.login.success", "&7Du bist nun eingeloggt.");
		cfg.addDefault("Language.DE.cmd.logout.success", "&7Du bist nun ausgeloggt.");
		cfg.addDefault("Language.DE.cmd.login.already", "&7Du bist bereits eingeloggt.");
		cfg.addDefault("Language.DE.cmd.logout.already", "&7Du bist bereits ausgeloggt.");
		cfg.addDefault("Language.DE.cmd.tg.visible", "&7Deine Gruppe ist nun sichtbar.");
		cfg.addDefault("Language.DE.cmd.tg.invisible", "&7Deine Gruppe ist nun nicht sichtbar.");
		cfg.addDefault("Language.DE.cmd.afk.join", "&7Du bist nun AFK.");
		cfg.addDefault("Language.DE.cmd.afk.leave", "&7Du bist nicht mehr AFK.");
		cfg.addDefault("Language.DE.cmd.scoreboard.off", "&7Du hast das Scoreboard ausgeschaltet.");
		cfg.addDefault("Language.DE.cmd.scoreboard.default", "&7Du siehst nun das standardmäßige Scoreboard.");
		cfg.addDefault("Language.DE.cmd.scoreboard.job", "&7Du siehst nun das Job-Scoreboard.");
		cfg.addDefault("Language.DE.cmd.scoreboard.admin", "&7Du siehst nun das Adminboard.");
		cfg.addDefault("Language.DE.cmd.scoreboard.data", "&7Du siehst nun den Leistungsspiegel.");
		cfg.addDefault("Language.DE.cmd.scoreboard.creajob", "&cDu kannst den Jobview in Kreativ nicht sehen!");
		cfg.addDefault("Language.DE.cmd.cc.private", "&7Du hast deinen privaten Chat geleert.");
		cfg.addDefault("Language.DE.cmd.cc.public", "&7Du hast den öffentlichen Chat geleert.");
		cfg.addDefault("Language.DE.cmd.cc.anonymous", "&7Du hast den öffentlichen Chat anonym geleert.");
		cfg.addDefault("Language.DE.cmd.cc.forall.public", "%displayer &7hat den Chat geleert.");
		cfg.addDefault("Language.DE.cmd.cc.forall.anonymous", "&7Ein Staff Member hat den Chat geleert.");
		cfg.addDefault("Language.DE.cmd.setid.idalreadyexists", "&7Diese ID existiert bereits.");
		cfg.addDefault("Language.DE.cmd.setid.updatesuccessfull", "%displayer &7hat nun ID &a%id &7.");
		cfg.addDefault("Language.DE.cmd.setid.limitexceeded", "&7Die ID sollte zwischen &a%minid &7und &a%maxid &7und &leinmalig &7sein.");
		cfg.addDefault("Language.DE.cmd.setid.playernotonline", "%displayer &7ist nicht online.");
		cfg.addDefault("Language.DE.cmd.setpf.playernotonline", "%displayer &7ist nicht online.");
		cfg.addDefault("Language.DE.cmd.setpf.playerreset", "%displayer &7's Prefix wurde zurückgesetzt.");
		cfg.addDefault("Language.DE.cmd.setpf.newprefix", "&7Prefix wurde geupdated.|&7Spieler: %displayer |&7Prefix:&f %prefix");
		cfg.addDefault("Language.DE.cmd.setpf.prefixtoolong", "&7Der Prefix darf nicht länger als &c%length &7/ &a16 &7Zeichen sein.");
		cfg.addDefault("Language.DE.cmd.clearlag", "&7Auf &6%worldcount Welten &7wurden &6%entities &7Items entfernt.");
		cfg.addDefault("Language.DE.cmd.gamemode.change", "&7Dein Gamemode wurde auf &6%gamemode &7geändert.");
		cfg.addDefault("Language.DE.cmd.gamemode.notonline", "&7Dieser Spieler ist nicht online.");
		cfg.addDefault("Language.DE.cmd.ping.own", "&7Dein Ping ist &a%ping &7ms");
		cfg.addDefault("Language.DE.cmd.ping.all", "%displayer &7's Ping ist &a%ping &7ms");
		cfg.addDefault("Language.DE.cmd.ping.notonline", "&7Dieser Spieler ist nicht online");
		cfg.addDefault("Language.DE.cmd.ping.other", "%displayer &7's Ping ist &a%ping &7ms");
		cfg.addDefault("Language.DE.cmd.time.set", "&7Die Zeit in &a%world &7wurde auf &a%time &7gesetzt.");
		cfg.addDefault("Language.DE.cmd.time.info", "&7Die Zeit in &a%world &7ist &a%time");
		cfg.addDefault("Language.DE.cmd.weather.notime", "&7Wetter in &a%world &7auf &a%wetter &7geändert.");
		cfg.addDefault("Language.DE.cmd.weather.time", "&7Wetter in &a%world &7auf &a%wetter &7für &a%seconds &7geändert");
		cfg.addDefault("Language.DE.cmd.weather.clear", "&7Wetter wurde auf Sonne zurückgesetzt");
		cfg.addDefault("Language.DE.cmd.openinv", "&7Du hast den Navigator geöffnet.");
		cfg.addDefault("Language.DE.cmd.blockpm.add", "&7Private Nachrichten sind nun geblockt.");
		cfg.addDefault("Language.DE.cmd.blockpm.remove", "&7Private Nachrichten sind nun nicht mehr geblockt.");
		cfg.addDefault("Language.DE.cmd.msg.playernotonline", "&7Der Spieler ist nicht online.");
		cfg.addDefault("Language.DE.cmd.msg.blockedmsg", "&7Du kannst keine privaten Nachrichten schreiben, wenn du die Nachrichten selbst geblockt hast.");
		cfg.addDefault("Language.DE.cmd.msg.bypassmsg", "&7Der Spieler hat private Nachrichten geblockt. Du kannst diesem trotzdem eine Nachricht schreiben.");
		cfg.addDefault("Language.DE.cmd.msg.you", "&cdu");
		cfg.addDefault("Language.DE.cmd.msg.playerblocked", "§7Dieser Spieler hat private Nachrichten blockiert.");
		cfg.addDefault("Language.DE.cmd.r.noentry", "&7Du hast keine offenen nicht beantworteten privaten Nachrichten.");
		cfg.addDefault("Language.DE.cmd.r.playernotonline", "&7Der Spieler ist nicht online.");
		cfg.addDefault("Language.DE.cmd.listhomes.homes", "&a%homecount &7Homes gespeichert.");
		cfg.addDefault("Language.DE.cmd.sethome.homeexistsalready", "&7Dieses Home existiert bereits.");
		cfg.addDefault("Language.DE.cmd.sethome.successfully", "&7Home wurde erfolgreich gespeichert. Home: &a%home &7| Homeanzahl: &a%count");
		cfg.addDefault("Language.DE.cmd.sethome.limitexceeded.bypass", "&7Du hast das Homelimit von &a%maxhomes Homes &7erreicht - Du kannst es trotzdem speichern, da du immun gegen die Maxhomes-Anzahl bist.");
		cfg.addDefault("Language.DE.cmd.sethome.limitexceeded.nobypass", "&7Du hast das Homelimit von &a%maxhomes Homes &7erreicht - Du kannst keine weiteren Homes speichern.");
		cfg.addDefault("Language.DE.cmd.delhome.successfully", "&7Das Home &a%home &7wurde erfolgreich gelöscht.");
		cfg.addDefault("Language.DE.cmd.delhome.notexisting", "&7Dieses Home existiert nicht.");
		cfg.addDefault("Language.DE.cmd.home.nothome", "&7Dieses Home existiert nicht.");
		cfg.addDefault("Language.DE.cmd.home.teleport", "&7Du wurdest zu Home &a%home &7teleportiert");
		cfg.addDefault("Language.DE.cmd.setvillager", "&7Du hast den Shopvillager gesetzt.");
		cfg.addDefault("Language.DE.cmd.money.player.own", "&7Du hast &a%money &7Coins");
		cfg.addDefault("Language.DE.cmd.money.bank.own", "&7Du hast &a%money &7Coins auf der Bank");
		cfg.addDefault("Language.DE.cmd.money.player.other", "%displayer &7hat &a%money &7Coins");
		cfg.addDefault("Language.DE.cmd.money.bank.other", "%displayer &7hat &a%money &7Coins auf der Bank");
		cfg.addDefault("Language.DE.cmd.setmoney.playernotonline", "&7Dieser Spieler ist nicht online.");
		cfg.addDefault("Language.DE.cmd.setmoney.belowzero", "&7Du kannst das Geld nicht in das negative setzen.");
		cfg.addDefault("Language.DE.cmd.setmoney.successfull", "&7Du hast %displayer &7auf &a%money &7Coins gesetzt.");
		cfg.addDefault("Language.DE.cmd.addmoney.playernotonline", "&7Dieser Spieler ist nicht online.");
		cfg.addDefault("Language.DE.cmd.addmoney.successfull", "&7Du hast %displayer &7auf &a%money &7Coins gesetzt.");
		cfg.addDefault("Language.DE.cmd.removemoney.playernotonline", "&7Dieser Spieler ist nicht online.");
		cfg.addDefault("Language.DE.cmd.removemoney.successfull", "&7Du hast %displayer &7auf &a%money &7Coins gesetzt.");
		cfg.addDefault("Language.DE.cmd.removemoney.belowzero", "&7Du kannst das Geld nicht in das negative setzen.");
		cfg.addDefault("Language.DE.cmd.pay.playernotonline", "&7Dieser Spieler ist nicht online.");
		cfg.addDefault("Language.DE.cmd.pay.successfull", "&7Du hast %displayer &a%money &7Coins gezahlt.");
		cfg.addDefault("Language.DE.cmd.pay.belowzero", "&7Du kannst nicht mehr bezahlen als du tatsächlich hast.");
		cfg.addDefault("Language.DE.cmd.setbankmoney.playernotonline", "&7Dieser Spieler ist nicht online.");
		cfg.addDefault("Language.DE.cmd.setbankmoney.belowzero", "&7Du kannst das Geld nicht in das negative setzen.");
		cfg.addDefault("Language.DE.cmd.setbankmoney.successfull", "&7Du hast %displayer &7's Bankaccount auf &a%money &7Coins gesetzt.");
		cfg.addDefault("Language.DE.cmd.bankdeposit.moreaspossible", "&7Du kannst nicht mehr auf deine Bank überweisen als du hast.");
		cfg.addDefault("Language.DE.cmd.bankdeposit.successfull", "&7Du hast &a%money &7Coins auf deine Bank überwiesen.");
		cfg.addDefault("Language.DE.cmd.bankwithdraw.moreaspossible", "&7Du kannst nicht mehr von deiner Bank abheben als du hast.");
		cfg.addDefault("Language.DE.cmd.bankwithdraw.successfull", "&7Du hast &a%money &7Coins von deiner Bank abgehoben.");
		cfg.addDefault("Language.DE.cmd.fly.other", "&7Dieser Spieler ist nicht online.");
		cfg.addDefault("Language.DE.cmd.workbench", "&7Du hast die Werkbank geöffnet.");
		cfg.addDefault("Language.DE.cmd.fly.own.true", "&7Du kannst nun fliegen.");
		cfg.addDefault("Language.DE.cmd.fly.own.false", "&7Du kannst nicht mehr fliegen.");
		cfg.addDefault("Language.DE.cmd.fly.other.true", "%displayer &7kann nun fliegen.");
		cfg.addDefault("Language.DE.cmd.fly.other.false", "%displayer &7kann nicht mehr fliegen.");
		cfg.addDefault("Language.DE.cmd.fly.other.offline", "&7Dieser Spieler ist nicht online.");
		cfg.addDefault("Language.DE.event.shopvillager.open", "&7Du hast den Shop geöffnet.");
		cfg.addDefault("Language.DE.event.shopvillager.hurt", "&7Du kannst dem Villager nicht wehtun.");
		cfg.addDefault("Language.DE.event.afk.leave", "&7Du bist nicht mehr AFK.");
		cfg.addDefault("Language.DE.event.navigator.sendPlayer.success", "&7Du wurdest zum Server &a%server &7gesendet.");
		cfg.addDefault("Language.DE.event.navigator.sendPlayer.locked", "&a%server &7ist zur zeit gesperrt.");
		cfg.addDefault("Language.DE.event.navigator.sendPlayer.monitorinfo", "&eInfo: Dieser Server wird derzeit beobachtet. Probleme oder instabilitäten könnten auftreten.");
		cfg.addDefault("Language.DE.event.navigator.sendPlayer.offline", "&a%server &7ist zur zeit offline.");
		cfg.addDefault("Language.DE.event.navigator.sendPlayer.failed", "&cEin fehler ist aufgetreten als du gesendet werden solltest.");
		cfg.addDefault("Language.DE.event.villagershop.fly", "&7Du hast Fly bereits gekauft");
		cfg.addDefault("Language.DE.event.villagershop.color", "&7Du hast die Chatfarben bereits gekauft");
		cfg.addDefault("Language.DE.event.villagershop.plots", "&7Du hast dir bereits mehr plots gekauft");
		cfg.addDefault("Language.DE.event.villagershop.effects", "&7Du hast dir bereits die Effekte gekauft");
		cfg.addDefault("Language.DE.event.villagershop.healwait", "&7Du musst noch &a%time &7Minuten warten um dich wieder zu heilen.");
		cfg.addDefault("Language.DE.event.villagershop.healsuccess", "&7Du hast dich nun geheilt");
		cfg.addDefault("Language.DE.event.villagershop.money", "&7Du hast &a%money &7Coins");
		cfg.addDefault("Language.DE.event.villagershop.notenoughmoney", "&7Du hast nicht genügend Geld um das zu kaufen.");
		cfg.addDefault("Language.DE.event.villagershop.purchase.effects", "&7Du hast dir nun die Effekte gekauft");
		cfg.addDefault("Language.DE.event.villagershop.purchase.userfly", "&7Du hast dir nun den Flymode gekauft");
		cfg.addDefault("Language.DE.event.villagershop.purchase.colorchat", "&7Du hast dir nun Chatfarben gekauft");
		cfg.addDefault("Language.DE.event.villagershop.purchase.plots", "&7Du hast dir nun mehr Plots gekauft");
		cfg.addDefault("Language.DE.event.worldteleporter.freebuild", "&7Du wurdest auf die &aFreebuild &7Welt teleportiert.");
		cfg.addDefault("Language.DE.event.worldteleporter.nether", "&7Du wurdest in den &cNether &7teleportiert.");
		cfg.addDefault("Language.DE.event.worldteleporter.plotworld", "&7Du wurdest auf die &aPlotwelt &7teleportiert.");
		cfg.addDefault("Language.DE.event.worldteleporter.theend", "&7Du wurdest in das &aEnde &7teleportiert.");
		cfg.addDefault("Language.DE.event.worldteleporter.notset", "&7Dieser Spawn ist nicht gesetzt.");
		cfg.addDefault("Language.DE.event.join.welcomemessage", "&7Willkommen auf &a%server&7, %displayer");
		cfg.addDefault("Language.DE.event.deathevent", "%target wurde von %killer &7getötet");
		cfg.addDefault("Language.DE.restarter.time.200000", "&7Der Server wird neugestartet in &a4 Stunden");
		cfg.addDefault("Language.DE.restarter.time.210000", "&7Der Server wird neugestartet in &a3 Stunden");
		cfg.addDefault("Language.DE.restarter.time.220000", "&7Der Server wird neugestartet in &a2 Stunden");
		cfg.addDefault("Language.DE.restarter.time.223000", "&7Der Server wird neugestartet in &a90 Minuten");
		cfg.addDefault("Language.DE.restarter.time.230000", "&7Der Server wird neugestartet in &a60 Minuten");
		cfg.addDefault("Language.DE.restarter.time.233000", "&7Der Server wird neugestartet in &a30 Minuten");
		cfg.addDefault("Language.DE.restarter.time.234500", "&7Der Server wird neugestartet in &a15 Minuten");
		cfg.addDefault("Language.DE.restarter.time.235500", "&7Der Server wird neugestartet in &a5 Minuten");
		cfg.addDefault("Language.DE.restarter.time.235600", "&7Der Server wird neugestartet in &e4 Minuten");
		cfg.addDefault("Language.DE.restarter.time.235700", "&7Der Server wird neugestartet in &e3 Minuten");
		cfg.addDefault("Language.DE.restarter.time.235800", "&7Der Server wird neugestartet in &c2 Minuten");
		cfg.addDefault("Language.DE.restarter.time.235900", "&7Der Server wird neugestartet in &c1 Minute");
		cfg.addDefault("Language.DE.restarter.time.235950", "&7Der Server wird neugestartet in &410 Sekunden");
		cfg.addDefault("Language.DE.restarter.time.235951", "&7Der Server wird neugestartet in &49 Sekunden");
		cfg.addDefault("Language.DE.restarter.time.235952", "&7Der Server wird neugestartet in &48 Sekunden");
		cfg.addDefault("Language.DE.restarter.time.235953", "&7Der Server wird neugestartet in &47 Sekunden");
		cfg.addDefault("Language.DE.restarter.time.235954", "&7Der Server wird neugestartet in &46 Sekunden");
		cfg.addDefault("Language.DE.restarter.time.235955", "&7Der Server wird neugestartet in &45 Sekunden");
		cfg.addDefault("Language.DE.restarter.time.235956", "&7Der Server wird neugestartet in &44 Sekunden");
		cfg.addDefault("Language.DE.restarter.time.235957", "&7Der Server wird neugestartet in &43 Sekunden");
		cfg.addDefault("Language.DE.restarter.time.235958", "&7Der Server wird neugestartet in &42 Sekunden");
		cfg.addDefault("Language.DE.restarter.time.235959", "&7Der Server wird neugestartet in &41 Sekunde");
		cfg.addDefault("Language.DE.scoreboard.sideboard.money", "&7Geld:");
		cfg.addDefault("Language.DE.scoreboard.sideboard.rank", "&7Rang:");
		cfg.addDefault("Language.DE.scoreboard.sideboard.playerid", "&7SpielerID:");
		cfg.addDefault("Language.DE.scoreboard.playerlist.top", "&aRedi&cCraft|&aDein Minecraft-Netzwerk");
		cfg.addDefault("Language.DE.scoreboard.playerlist.bottom", "&7Zeit: &a%time|&7Server: &a%servername &7/ &a%serverid");
		
		cfg.options().copyDefaults(true);
		try { cfg.save(file); } catch (IOException e) { e.printStackTrace(); }
	}
	
	public static void noPerm(Player p) {
		if(retLang(p).equalsIgnoreCase("en-uk")) {
			p.sendMessage(Main.prefix() + retString("en-uk", "noPerm"));
		}else if(retLang(p).equalsIgnoreCase("de-de")) {
			p.sendMessage(Main.prefix() + retString("de-de", "noPerm"));
		}
	}
	
	public static void notAvailable(Player p) {
		if(retLang(p).equalsIgnoreCase("en-uk")) {
			p.sendMessage(Main.prefix() + retString("en-uk", "notAvailable"));
		}else if(retLang(p).equalsIgnoreCase("de-de")) {
			p.sendMessage(Main.prefix() + retString("de-de", "notAvailable"));
		}
	}
	
	public static void sendMSGReady(Player p, String path) {
		if(retLang(p).equalsIgnoreCase("en-uk")) {
			p.sendMessage(Main.prefix() + retString("en-uk", path));
		}else if(retLang(p).equalsIgnoreCase("de-de")) {
			p.sendMessage(Main.prefix() + retString("de-de", path));
		}
	}
	
	public static String returnStringReady(Player p, String path) {
		String s = "";
		if(retLang(p).equalsIgnoreCase("en-uk")) {
			s = retString("en-uk", path);
		}else if(retLang(p).equalsIgnoreCase("de-de")) {
			s = retString("de-de", path);
		}
		return s;
	}
	
	private static String retLang(Player p) {
		String langKey = "en-UK";
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redicore_userstats WHERE uuid = ?");
			ps.setString(1, p.getUniqueId().toString().replace("-", ""));
			ResultSet rs = ps.executeQuery();
			rs.next();
			langKey = rs.getString("language");
		}catch (SQLException e) { e.printStackTrace(); return null; }
		return langKey;
	}

	private static String retString(String lang, String path) {
		File file = new File("plugins/RCUSS/language.yml");
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		String string = "";
		if(lang.equalsIgnoreCase("en-uk")) {
			if(cfg.contains("Language.EN." + path)) {
				string = cfg.getString("Language.EN." + path).replace("&", "§");
			}else {
				string = "§cThis path doesn't exists.";
			}
		}else if(lang.equalsIgnoreCase("de-de")) {
			if(cfg.contains("Language.DE." + path)) {
				string = cfg.getString("Language.DE." + path).replace("&", "§");
			}else {
				string = "§cDieser Pfad existiert nicht.";
			}
		}
		return string;
	}
	
	static File file = new File("server.properties");
	
	public static String getServerName() {
		Properties p = new Properties();
		String s = "";
		try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))){
			p.load(bis);
		}catch (Exception ex) {
			s = "null";
		}
		s = p.getProperty("server-name");
		return s;
	}
	
	public static String getServerId() {
		Properties p = new Properties();
		String s = "";
		try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))){
			p.load(bis);
		}catch (Exception ex) {
			s = "null";
		}
		s = p.getProperty("server-id");
		return s;
	}
	
	public static int getPlayers(String server, String type) {
		int i = 0;
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redicore_serverstats WHERE servername = ?");
			ps.setString(1, server);
			ResultSet rs = ps.executeQuery();
			rs.next();
			i = rs.getInt(type);
		}catch (SQLException e) { e.printStackTrace(); }
		return i;
	}
	
	public static String getUUIDfromName(String name) {
		String url = "https://api.mojang.com/users/profiles/minecraft/" + name;
		String uuid = "";
		try {
			@SuppressWarnings("deprecation")
			String UUIDJson = IOUtils.toString(new URL(url));
			if(UUIDJson.isEmpty()) return "ERRORED";
			JSONObject UUIDObject = (JSONObject) JSONValue.parseWithException(UUIDJson);
			uuid =  UUIDObject.get("id").toString();
		}catch(IOException|ParseException e) {
			uuid = "ERRORED";
		}
		return uuid;
	}
	
	public static String getNamefromUUID(String uuid) {
		String url = "https://api.mojang.com/user/profiles/" + uuid.replace("-", "") + "/names";
		String name = "";
		try {
			@SuppressWarnings("deprecation")
			String nameJson = IOUtils.toString(new URL(url));
			JSONArray nameVal = (JSONArray) JSONValue.parseWithException(nameJson);
			String playerSlot = nameVal.get(nameVal.size()-1).toString();
			JSONObject UUIDObject = (JSONObject) JSONValue.parseWithException(playerSlot);
			name =  UUIDObject.get("name").toString();
		}catch(IOException|ParseException e) {
			name = "ERRORED";
		}
		return name;
	}
	
	public static String prefix(String type) {
		String s = "";
		if(MySQL.isConnected()) {
			try {
				PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redicore_igprefix WHERE type = ?");
				if(type.equalsIgnoreCase("main")) {
					ps.setString(1, "main");
					ResultSet rs = ps.executeQuery();
					rs.next();
					s = rs.getString("prefix");
					rs.close();
					ps.close();
				}else if(type.equalsIgnoreCase("scoreboard")) {
					ps.setString(1, "scoreboard");
					ResultSet rs = ps.executeQuery();
					rs.next();
					s = rs.getString("prefix");
					rs.close();
					ps.close();
				}else if(type.equalsIgnoreCase("pmsystem")) {
					ps.setString(1, "pmsys");
					ResultSet rs = ps.executeQuery();
					rs.next();
					s = rs.getString("prefix");
					rs.close();
					ps.close();
				}else {
					ps.setString(1, type);
					ResultSet rs = ps.executeQuery();
					rs.next();
					s = rs.getString("prefix");
					rs.close();
					ps.close();
				}
			}catch (SQLException e) { e.printStackTrace(); }
		}else {
			if(type.equalsIgnoreCase("prefix")) {
				s = "prefix1";
			}else if(type.equalsIgnoreCase("scoreboard")) {
				s = "prefix2";
			}else if(type.equalsIgnoreCase("pmsystem")) {
				s = "prefix3";
			}else {
				s = "prefix4";
			}
		}
		return s;
	}
	
	public static ItemStack defItem(Material mat, int avg, String dpname) {
		ItemStack is = new ItemStack(mat, avg);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(dpname);
		is.setItemMeta(im);
		return is;
	}
	
	public static ItemStack potionItem(int avg, PotionType effect, String dpname) {
		ItemStack item = new ItemStack(Material.POTION, avg);
		PotionMeta potion = (PotionMeta) item.getItemMeta();
		potion.setBasePotionData(new PotionData(effect, false, false));
		potion.setDisplayName(dpname);
		item.setItemMeta(potion);
		return item;
	}
	
	public static ItemStack enchItem(Material mat, int avg, String dpname, Enchantment ench) {
		ItemStack item = new ItemStack(mat, avg);
		ItemMeta mitem = item.getItemMeta();
		mitem.setDisplayName(dpname);
		mitem.addEnchant(ench, 1, true);
		item.setItemMeta(mitem);
		return item;
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack skullItem(int avg, String dpname, String skullowner) {
		ItemStack skull = new ItemStack(Material.PLAYER_HEAD, avg);
		SkullMeta skullmeta = (SkullMeta) skull.getItemMeta();
		skullmeta.setOwner(skullowner);
		skullmeta.setDisplayName(dpname);
		skull.setItemMeta(skullmeta);
		return skull;
	}
	
	public static ItemStack l2Item(Material mat, int avg, String dpname, String lore1, String lore2) {
	    ArrayList<String> lore = new ArrayList<String>();
	    ItemStack item = new ItemStack(mat, avg);
	    ItemMeta mitem = item.getItemMeta();
	    lore.add(lore1);
	    lore.add(lore2);
	    mitem.setLore(lore);
	    mitem.setDisplayName(dpname);
	    item.setItemMeta(mitem);
	    return item;
	  }
	
	@Deprecated
	public static ItemStack onlineItem(Material mat, int avg, String dpname, int online) {
		ArrayList<String> lore = new ArrayList<>();
		ItemStack item = new ItemStack(mat, avg);
		ItemMeta mitem = item.getItemMeta();
		lore.add("§aOnline§7: " + online);
		mitem.setLore(lore);
		mitem.setDisplayName(dpname);
		item.setItemMeta(mitem);
		return item;
	}
	
	public static ItemStack naviItem(Material mat, String dpname, String servername) {
		ArrayList<String> lore = new ArrayList<>();
		ItemStack item = new ItemStack(mat, 1);
		ItemMeta mitem = item.getItemMeta();
		boolean online = getData(servername, "online");
		boolean locked = getData(servername, "locked");
		boolean monitor = getData(servername, "monitoring");
		if(online){
			lore.add("§7Online: §ayes");
			lore.add("§7Online: §a" + getPlayers(servername) + " §7Players");
		}else {
			lore.add("§7Online: §cno");
		}
		if(locked) {
			lore.add("§7Locked: §cyes");
		}
		if(monitor) {
			lore.add("§7Monitoring: §cyes");
		}
		mitem.setLore(lore);
		mitem.setDisplayName(dpname);
		item.setItemMeta(mitem);
		return item;
	}
	
	private static boolean getData(String server, String column) {
		boolean boo = false;
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redicore_serverstats WHERE servername = ?");
			ps.setString(1, server);
			ResultSet rs = ps.executeQuery();
			rs.next();
			boo = rs.getBoolean(column);
			ps.close();
			rs.close();
		}catch (SQLException e) { e.printStackTrace(); }
		return boo;
	}
	
	private static int getPlayers(String server) {
		int i = 0;
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redicore_serverstats WHERE servername = ?");
			ps.setString(1, server);
			ResultSet rs = ps.executeQuery();
			rs.next();
			i = rs.getInt("currPlayers");
			ps.close();
			rs.close();
		}catch (SQLException e) { e.printStackTrace(); }
		return i;
	}
	
	public static void sendHotbarMessage(Player p, String message) {
		p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
	}
}