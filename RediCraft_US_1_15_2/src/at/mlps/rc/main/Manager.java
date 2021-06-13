package at.mlps.rc.main;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;

import at.mlps.rc.api.APIs;
import at.mlps.rc.api.ChannelManager;
import at.mlps.rc.cmd.AFK_CMD;
import at.mlps.rc.cmd.CMD_SetID_SetPf;
import at.mlps.rc.cmd.ChatClear;
import at.mlps.rc.cmd.ClearLag;
import at.mlps.rc.cmd.Dynmap_CMD;
import at.mlps.rc.cmd.FlyCMD;
import at.mlps.rc.cmd.GamemodeCMD;
import at.mlps.rc.cmd.Homesystem;
import at.mlps.rc.cmd.InvseeCMD;
import at.mlps.rc.cmd.LogSystem;
import at.mlps.rc.cmd.MoneyAPI;
import at.mlps.rc.cmd.PM_System;
import at.mlps.rc.cmd.Pinfo;
import at.mlps.rc.cmd.PingCMD;
import at.mlps.rc.cmd.ScoreboardChange;
import at.mlps.rc.cmd.ServerhealthCMD;
import at.mlps.rc.cmd.SkullCMD;
import at.mlps.rc.cmd.SpawnVillager;
import at.mlps.rc.cmd.StopCMD;
import at.mlps.rc.cmd.TPA_System;
import at.mlps.rc.cmd.TP_Command;
import at.mlps.rc.cmd.TimeCMD;
import at.mlps.rc.cmd.Vanish_CMD;
import at.mlps.rc.cmd.WeatherCMD;
import at.mlps.rc.cmd.WorkBenchCMD;
import at.mlps.rc.cmd.Setspawn;
import at.mlps.rc.event.Blocker;
import at.mlps.rc.event.JoinQuitEvents;
import at.mlps.rc.event.ScoreboardCLS;
import at.mlps.rc.event.Serverteleporter;
import at.mlps.rc.mysql.lpb.MySQL;

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
		at.mlps.rc.mysql.lb.MySQL.connect(host, String.valueOf(port), db, user, pass);
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
		Main.instance.getCommand("channel").setExecutor(new ChannelManager());
		Main.instance.getCommand("tphere").setExecutor(new TP_Command());
		Main.instance.getCommand("tp").setExecutor(new TP_Command());
		Main.instance.getCommand("vanish").setExecutor(new Vanish_CMD());
		
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
		pl.registerEvents(new ChannelManager(), Main.instance);
		pl.registerEvents(new Vanish_CMD(), Main.instance);
		//pl.registerEvents(new KilledStats(), Main.instance);
		
		APIs api = new APIs();
		api.loadConfig();
		api.onLoad();
		
		ScoreboardCLS sb = new ScoreboardCLS();
		sb.downloadStrings();
	}
}