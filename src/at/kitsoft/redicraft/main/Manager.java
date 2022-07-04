package at.kitsoft.redicraft.main;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;

import at.kitsoft.redicraft.api.APIs;
import at.kitsoft.redicraft.cmd.AFK_CMD;
import at.kitsoft.redicraft.cmd.CMD_SetID_SetPf;
import at.kitsoft.redicraft.cmd.ChatClear;
import at.kitsoft.redicraft.cmd.ClearLag;
import at.kitsoft.redicraft.cmd.Dynmap_CMD;
import at.kitsoft.redicraft.cmd.FlyCMD;
import at.kitsoft.redicraft.cmd.GamemodeCMD;
import at.kitsoft.redicraft.cmd.Homesystem;
import at.kitsoft.redicraft.cmd.InvseeCMD;
import at.kitsoft.redicraft.cmd.LogSystem;
import at.kitsoft.redicraft.cmd.MoneyAPI;
import at.kitsoft.redicraft.cmd.PM_System;
import at.kitsoft.redicraft.cmd.Pinfo;
import at.kitsoft.redicraft.cmd.PingCMD;
import at.kitsoft.redicraft.cmd.ProfileSettings;
import at.kitsoft.redicraft.cmd.RedeemGifts;
import at.kitsoft.redicraft.cmd.ScoreboardChange;
import at.kitsoft.redicraft.cmd.ServerhealthCMD;
import at.kitsoft.redicraft.cmd.Setspawn;
import at.kitsoft.redicraft.cmd.SkullCMD;
import at.kitsoft.redicraft.cmd.SkullListCMD;
import at.kitsoft.redicraft.cmd.SpawnVillager;
import at.kitsoft.redicraft.cmd.SpeedCMD;
import at.kitsoft.redicraft.cmd.StopCMD;
import at.kitsoft.redicraft.cmd.TPA_System;
import at.kitsoft.redicraft.cmd.TP_Command;
import at.kitsoft.redicraft.cmd.TimeCMD;
import at.kitsoft.redicraft.cmd.TopPlaytimeCMD;
import at.kitsoft.redicraft.cmd.Vanish_CMD;
import at.kitsoft.redicraft.cmd.WeatherCMD;
import at.kitsoft.redicraft.cmd.WorkBenchCMD;
import at.kitsoft.redicraft.event.AutoKickerMethods;
import at.kitsoft.redicraft.event.Blocker;
import at.kitsoft.redicraft.event.ColorSigns;
import at.kitsoft.redicraft.event.FullEventList;
import at.kitsoft.redicraft.event.JobSigns;
import at.kitsoft.redicraft.event.JoinQuitEvents;
import at.kitsoft.redicraft.event.ScoreboardCLS;
import at.kitsoft.redicraft.event.Serverteleporter;
import at.kitsoft.redicraft.event.XP_Boost;
import at.kitsoft.redicraft.mysql.lpb.MySQL;

public class Manager {
	
	public void init() {
		File config = new File("plugins/RCUSS/config.yml");
		if(!config.exists()) {
			try {
				config.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		File file = new File("plugins/RCUSS/ptimecache.yml");
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(config);
		cfg.addDefault("MySQL.Host", "localhost");
		cfg.addDefault("MySQL.Port", 3306);
		cfg.addDefault("MySQL.Database", "database");
		cfg.addDefault("MySQL.Username", "username");
		cfg.addDefault("MySQL.Password", "password");
		cfg.options().copyDefaults(true);
		try {
			cfg.save(config);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		String host = cfg.getString("MySQL.Host");
		int port = cfg.getInt("MySQL.Port");
		String db = cfg.getString("MySQL.Database");
		String user = cfg.getString("MySQL.Username");
		String pass = cfg.getString("MySQL.Password");
		at.kitsoft.redicraft.mysql.lb.MySQL.connect(host, String.valueOf(port), db, user, pass);
		Main.mysql = new MySQL(host, port, db, user, pass);
		try {
			Main.mysql.connect();
		}catch (SQLException e) {}
		
		Main.instance.getCommand("sb").setExecutor(new ScoreboardChange());
		Main.instance.getCommand("afk").setExecutor(new AFK_CMD());
		Main.instance.getCommand("cc").setExecutor(new ChatClear());
		Main.instance.getCommand("chatclear").setExecutor(new ChatClear());
		Main.instance.getCommand("setid").setExecutor(new CMD_SetID_SetPf());
		Main.instance.getCommand("setpf").setExecutor(new CMD_SetID_SetPf());
		Main.instance.getCommand("clearlag").setExecutor(new ClearLag());
		Main.instance.getCommand("gm").setExecutor(new GamemodeCMD());
		Main.instance.getCommand("gamemode").setExecutor(new GamemodeCMD());
		Main.instance.getCommand("ping").setExecutor(new PingCMD());
		Main.instance.getCommand("s").setExecutor(new Serverteleporter(Main.instance));
		Main.instance.getCommand("gc").setExecutor(new ServerhealthCMD());
		Main.instance.getCommand("setspawn").setExecutor(new Setspawn());
		Main.instance.getCommand("stop").setExecutor(new StopCMD(Main.instance));
		Main.instance.getCommand("time").setExecutor(new TimeCMD());
		Main.instance.getCommand("weather").setExecutor(new WeatherCMD());
		Main.instance.getCommand("msg").setExecutor(new PM_System());
		Main.instance.getCommand("r").setExecutor(new PM_System());
		Main.instance.getCommand("blockmsg").setExecutor(new PM_System());
		Main.instance.getCommand("delhome").setExecutor(new Homesystem());
		Main.instance.getCommand("home").setExecutor(new Homesystem());
		Main.instance.getCommand("sethome").setExecutor(new Homesystem());
		Main.instance.getCommand("listhomes").setExecutor(new Homesystem());
		Main.instance.getCommand("spawnvillager").setExecutor(new SpawnVillager());
		Main.instance.getCommand("money").setExecutor(new MoneyAPI());
		Main.instance.getCommand("setmoney").setExecutor(new MoneyAPI());
		Main.instance.getCommand("removemoney").setExecutor(new MoneyAPI());
		Main.instance.getCommand("addmoney").setExecutor(new MoneyAPI());
		Main.instance.getCommand("topmoney").setExecutor(new MoneyAPI());
		Main.instance.getCommand("setbankmoney").setExecutor(new MoneyAPI());
		Main.instance.getCommand("bankdeposit").setExecutor(new MoneyAPI());
		Main.instance.getCommand("bankwithdraw").setExecutor(new MoneyAPI());
		Main.instance.getCommand("pay").setExecutor(new MoneyAPI());
		Main.instance.getCommand("head").setExecutor(new SkullCMD());
		Main.instance.getCommand("headlist").setExecutor(new SkullListCMD());
		Main.instance.getCommand("login").setExecutor(new LogSystem());
		Main.instance.getCommand("logout").setExecutor(new LogSystem());
		Main.instance.getCommand("tg").setExecutor(new LogSystem());
		Main.instance.getCommand("fly").setExecutor(new FlyCMD());
		Main.instance.getCommand("wb").setExecutor(new WorkBenchCMD());
		Main.instance.getCommand("workbench").setExecutor(new WorkBenchCMD());
		Main.instance.getCommand("onlinemap").setExecutor(new Dynmap_CMD());
		Main.instance.getCommand("invsee").setExecutor(new InvseeCMD());
		Main.instance.getCommand("pinfo").setExecutor(new Pinfo());
		Main.instance.getCommand("tpa").setExecutor(new TPA_System());
		Main.instance.getCommand("tpahere").setExecutor(new TPA_System());
		Main.instance.getCommand("tpaccept").setExecutor(new TPA_System());
		Main.instance.getCommand("tpdeny").setExecutor(new TPA_System());
		Main.instance.getCommand("blocktpa").setExecutor(new TPA_System());
		Main.instance.getCommand("tphere").setExecutor(new TP_Command());
		Main.instance.getCommand("tp").setExecutor(new TP_Command());
		Main.instance.getCommand("vanish").setExecutor(new Vanish_CMD());
		Main.instance.getCommand("speed").setExecutor(new SpeedCMD());
		Main.instance.getCommand("settings").setExecutor(new ProfileSettings());
		Main.instance.getCommand("topplaytime").setExecutor(new TopPlaytimeCMD());
		Main.instance.getCommand("redeemgift").setExecutor(new RedeemGifts());
		Main.instance.getCommand("deletejobsign").setExecutor(new JobSigns());
		
		PluginManager pl = Bukkit.getPluginManager();
		pl.registerEvents(new ScoreboardCLS(), Main.instance);
		pl.registerEvents(new JoinQuitEvents(), Main.instance);
		pl.registerEvents(new Serverteleporter(Main.instance), Main.instance);
		pl.registerEvents(new Homesystem(), Main.instance);
		pl.registerEvents(new SpawnVillager(), Main.instance);
		pl.registerEvents(new MoneyAPI(), Main.instance);
		pl.registerEvents(new AFK_CMD(), Main.instance);
		pl.registerEvents(new FlyCMD(), Main.instance);
		pl.registerEvents(new StopCMD(Main.instance), Main.instance);
		pl.registerEvents(new Blocker(), Main.instance);
		pl.registerEvents(new Serverupdater(), Main.instance);
		pl.registerEvents(new Vanish_CMD(), Main.instance);
		pl.registerEvents(new SkullListCMD(), Main.instance);
		pl.registerEvents(new ProfileSettings(), Main.instance);
		pl.registerEvents(new XP_Boost(), Main.instance);
		pl.registerEvents(new RedeemGifts(), Main.instance);
		pl.registerEvents(new ColorSigns(), Main.instance);
		pl.registerEvents(new AutoKickerMethods(), Main.instance);
		pl.registerEvents(new FullEventList(), Main.instance);
		pl.registerEvents(new JobSigns(), Main.instance);
		//pl.registerEvents(new KilledStats(), Main.instance);
		
		APIs api = new APIs();
		api.loadConfig();
		api.onLoad();
		
		ScoreboardCLS sb = new ScoreboardCLS();
		sb.downloadStrings();
	}
}