package at.kitsoft.redicraft.event;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import at.kitsoft.redicraft.api.APIs;
import at.kitsoft.redicraft.main.Main;
import at.kitsoft.redicraft.mysql.lb.MySQL;

public class Serverteleporter implements Listener, CommandExecutor{
	
	private static Main plugin;
	public Serverteleporter(Main m) {
		plugin = m;
	}
	
	public static String title = "§aServer§cNavigator";
	static String dailyrew = "§aDaily Rewards";
	static String spawn = "§6Lobby";
	static String skyblock = "§7Sky§2Block";
	static String creative = "§eCreative";
	static String survival = "§cSurvival";
	static String farmserver = "§5Farmserver";
	static String bauserver = "§bStaffserver";
	static String gameslobby = "§dGameslobby";
	
	static String wt_inventory = "§aWorld§cTeleporter";
	static String wt_freebuild = "§aFreebuild";
	static String wt_overworld = "§aOverworld";
	static String wt_plotworld = "§aPlotworld";
	static String wt_theend = "§5The End";
	static String wt_nether = "§cNether";
	
	static String locked = " §7- §4locked";
	static String monitored = " §7- §9monitoring";
	static String offline = " §7- §eoffline";
	
	static File spawnfile = new File("plugins/RCUSS/spawn.yml");
	
	//main servernavi
	public static void mainnavi(Player p) {
		APIs api = new APIs();
		Inventory inv = Bukkit.createInventory(null, 9*3, title);
		inv.setItem(0, api.defItem(Material.BLACK_STAINED_GLASS_PANE, 1, "§0"));
		inv.setItem(1, api.defItem(Material.BLACK_STAINED_GLASS_PANE, 1, "§0"));
		inv.setItem(3, api.defItem(Material.BLACK_STAINED_GLASS_PANE, 1, "§0"));
		inv.setItem(4, api.defItem(Material.BLACK_STAINED_GLASS_PANE, 1, "§0"));
		inv.setItem(5, api.defItem(Material.BLACK_STAINED_GLASS_PANE, 1, "§0"));
		inv.setItem(7, api.defItem(Material.BLACK_STAINED_GLASS_PANE, 1, "§0"));
		inv.setItem(8, api.defItem(Material.BLACK_STAINED_GLASS_PANE, 1, "§0"));
		inv.setItem(9, api.defItem(Material.BLACK_STAINED_GLASS_PANE, 1, "§0"));
		inv.setItem(11, api.defItem(Material.BLACK_STAINED_GLASS_PANE, 1, "§0"));
		inv.setItem(15, api.defItem(Material.BLACK_STAINED_GLASS_PANE, 1, "§0"));
		inv.setItem(17, api.defItem(Material.BLACK_STAINED_GLASS_PANE, 1, "§0"));
		inv.setItem(18, api.defItem(Material.BLACK_STAINED_GLASS_PANE, 1, "§0"));
		inv.setItem(19, api.defItem(Material.BLACK_STAINED_GLASS_PANE, 1, "§0"));
		inv.setItem(21, api.defItem(Material.BLACK_STAINED_GLASS_PANE, 1, "§0"));
		inv.setItem(22, api.defItem(Material.BLACK_STAINED_GLASS_PANE, 1, "§0"));
		inv.setItem(23, api.defItem(Material.BLACK_STAINED_GLASS_PANE, 1, "§0"));
		inv.setItem(25, api.defItem(Material.BLACK_STAINED_GLASS_PANE, 1, "§0"));
		//inv w/ items
		inv.setItem(2, api.naviItem(Material.GRASS_BLOCK, skyblock, "SkyBlock"));
		inv.setItem(6, api.naviItem(Material.DIAMOND_PICKAXE, creative, "Creative"));
		inv.setItem(10, api.naviItem(Material.WOODEN_AXE, bauserver, "Staffserver"));
		inv.setItem(16, api.naviItem(Material.GOLDEN_SHOVEL, farmserver, "Farmserver"));
		inv.setItem(20, api.naviItem(Material.RED_BED, gameslobby, "Gameslobby"));
		inv.setItem(24, api.naviItem(Material.IRON_AXE, survival, "Survival"));
		if(api.getServerName().equalsIgnoreCase("Farmserver") || api.getServerName().equalsIgnoreCase("Survival")) {
			inv.setItem(12, api.defItem(Material.AMETHYST_SHARD, 1, wt_inventory));
			inv.setItem(13, api.defItem(Material.BLACK_STAINED_GLASS_PANE, 1, "§0"));
			inv.setItem(14, api.naviItem(Material.AMETHYST_CLUSTER, spawn, "Lobby"));
		}else {
			inv.setItem(12, api.defItem(Material.BLACK_STAINED_GLASS_PANE, 1, "§0"));
			inv.setItem(13, api.naviItem(Material.AMETHYST_CLUSTER, spawn, "Lobby"));
			inv.setItem(14, api.defItem(Material.BLACK_STAINED_GLASS_PANE, 1, "§0"));
		}
		p.openInventory(inv);
		p.updateInventory();
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		APIs api = new APIs();
		Player p = (Player)e.getWhoClicked();
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(spawnfile);
		if(e.getView().getTitle().equalsIgnoreCase(title)) {
			e.setCancelled(true);
			if(e.getCurrentItem() == null) return;
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(dailyrew)) {
				e.setCancelled(true);
				p.closeInventory();
				p.teleport(retLoc(cfg, "drew"));
				api.sendMSGReady(p, "event.navigator.local.dailyreward");
			}else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(spawn)) {
				e.setCancelled(true);
				boolean lock = getData("Lobby", "locked");
				if(lock) {
					p.sendMessage(api.prefix("main") + api.returnStringReady(p, "event.navigator.sendPlayer.locked").replace("%server", "Lobby"));
				}else {
					boolean monitor = getData("Lobby", "monitoring");
					boolean online = getData("Lobby", "online");
					if(online) {
						if(monitor) {
							api.sendMSGReady(p, "event.navigator.sendPlayer.monitorinfo");
							sendPlayer(p, "lobby", spawn);
						}else {
							sendPlayer(p, "lobby", spawn);
						}
					}else {
						p.sendMessage(api.prefix("main") + api.returnStringReady(p, "event.navigator.sendPlayer.offline").replace("%server", "Lobby"));
					}
					
				}
			}else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(wt_inventory)) {
				e.setCancelled(true);
				if(api.getServerName().equalsIgnoreCase("farmserver") || api.getServerName().equalsIgnoreCase("survival")) {
					worldteleporter(p);
				}
			}else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(creative)) {
				e.setCancelled(true);
				boolean lock = getData("Creative", "locked");
				if(lock) {
					p.sendMessage(api.prefix("main") + api.returnStringReady(p, "event.navigator.sendPlayer.locked").replace("%server", "Creative"));
				}else {
					boolean monitor = getData("Creative", "monitoring");
					boolean online = getData("Creative", "online");
					if(online) {
						if(monitor) {
							api.sendMSGReady(p, "event.navigator.sendPlayer.monitorinfo");
							sendPlayer(p, "creative", creative);
						}else {
							sendPlayer(p, "creative", creative);
						}
					}else {
						p.sendMessage(api.prefix("main") + api.returnStringReady(p, "event.navigator.sendPlayer.offline").replace("%server", "Creative"));
					}
					
				}
			}else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(survival)) {
				e.setCancelled(true);
				boolean lock = getData("Survival", "locked");
				if(lock) {
					p.sendMessage(api.prefix("main") + api.returnStringReady(p, "event.navigator.sendPlayer.locked").replace("%server", "Survival"));
				}else {
					boolean monitor = getData("Survival", "monitoring");
					boolean online = getData("Survival", "online");
					if(online) {
						if(monitor) {
							api.sendMSGReady(p, "event.navigator.sendPlayer.monitorinfo");
							sendPlayer(p, "survival", survival);
						}else {
							sendPlayer(p, "survival", survival);
						}
					}else {
						p.sendMessage(api.prefix("main") + api.returnStringReady(p, "event.navigator.sendPlayer.offline").replace("%server", "Survival"));
					}
					
				}
			}else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(skyblock)) {
				e.setCancelled(true);
				boolean lock = getData("SkyBlock", "locked");
				if(lock) {
					p.sendMessage(api.prefix("main") + api.returnStringReady(p, "event.navigator.sendPlayer.locked").replace("%server", "SkyBlock"));
				}else {
					boolean monitor = getData("SkyBlock", "monitoring");
					boolean online = getData("SkyBlock", "online");
					if(online) {
						if(monitor) {
							api.sendMSGReady(p, "event.navigator.sendPlayer.monitorinfo");
							sendPlayer(p, "skyblock", skyblock);
						}else {
							sendPlayer(p, "skyblock", skyblock);
						}
					}else {
						p.sendMessage(api.prefix("main") + api.returnStringReady(p, "event.navigator.sendPlayer.offline").replace("%server", "SkyBlock"));
					}
					
				}
			}else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(farmserver)) {
				e.setCancelled(true);
				boolean lock = getData("Farmserver", "locked");
				if(lock) {
					p.sendMessage(api.prefix("main") + api.returnStringReady(p, "event.navigator.sendPlayer.locked").replace("%server", "Farmserver"));
				}else {
					boolean monitor = getData("Farmserver", "monitoring");
					boolean online = getData("Farmserver", "online");
					if(online) {
						if(monitor) {
							api.sendMSGReady(p, "event.navigator.sendPlayer.monitorinfo");
							sendPlayer(p, "farmserver", farmserver);
						}else {
							sendPlayer(p, "farmserver", farmserver);
						}
					}else {
						p.sendMessage(api.prefix("main") + api.returnStringReady(p, "event.navigator.sendPlayer.offline").replace("%server", "Farmserver"));
					}
					
				}
			}else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(bauserver)) {
				e.setCancelled(true);
				if(p.hasPermission("mlps.isStaff")) {
					boolean lock = getData("Staffserver", "locked");
					if(lock) {
						p.sendMessage(api.prefix("main") + api.returnStringReady(p, "event.navigator.sendPlayer.locked").replace("%server", "Staffserver"));
					}else {
						boolean monitor = getData("Staffserver", "monitoring");
						boolean online = getData("Staffserver", "online");
						if(online) {
							if(monitor) {
								api.sendMSGReady(p, "event.navigator.sendPlayer.monitorinfo");
								sendPlayer(p, "staffserver", bauserver);
							}else {
								sendPlayer(p, "staffserver", bauserver);
							}
						}else {
							p.sendMessage(api.prefix("main") + api.returnStringReady(p, "event.navigator.sendPlayer.offline").replace("%server", "Staffserver"));
						}
						
					}
				}else {
					api.noPerm(p);
				}
			}else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(gameslobby)) {
				e.setCancelled(true);
				boolean lock = getData("Gameslobby", "locked");
				if(lock) {
					p.sendMessage(api.prefix("main") + api.returnStringReady(p, "event.navigator.sendPlayer.locked").replace("%server", "Gameslobby"));
				}else {
					boolean monitor = getData("Gameslobby", "monitoring");
					boolean online = getData("Gameslobby", "online");
					if(online) {
						if(monitor) {
							api.sendMSGReady(p, "event.navigator.sendPlayer.monitorinfo");
							sendPlayer(p, "gameslobby", bauserver);
						}else {
							sendPlayer(p, "gameslobby", bauserver);
						}
					}else {
						p.sendMessage(api.prefix("main") + api.returnStringReady(p, "event.navigator.sendPlayer.offline").replace("%server", "Gameslobby"));
					}
					
				}
			}
		}else if(e.getView().getTitle().equalsIgnoreCase(wt_inventory)) {
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(wt_freebuild)) {
				if(retLoc(cfg, "freebuild") == null) {
					api.sendMSGReady(p, "event.worldteleporter.notset");
				}else {
					p.teleport(retLoc(cfg, "freebuild"));
					p.sendMessage(api.prefix("main") + api.returnStringReady(p, "event.worldteleporter.success").replace("%type", "§aFreebuild"));
				}
				e.setCancelled(true);
				p.closeInventory();
			}else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(wt_overworld)) {
				if(retLoc(cfg, "freebuild") == null) {
					api.sendMSGReady(p, "event.worldteleporter.notset");
				}else {
					p.teleport(retLoc(cfg, "freebuild"));
					p.sendMessage(api.prefix("main") + api.returnStringReady(p, "event.worldteleporter.success").replace("%type", "§aOverworld"));
				}
				e.setCancelled(true);
				p.closeInventory();
			}else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(wt_nether)) {
				if(retLoc(cfg, "nether") == null) {
					api.sendMSGReady(p, "event.worldteleporter.notset");
				}else {
					p.teleport(retLoc(cfg, "nether"));
					p.sendMessage(api.prefix("main") + api.returnStringReady(p, "event.worldteleporter.success").replace("%type", "§cNether"));
				}
				e.setCancelled(true);
				p.closeInventory();
			}else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(wt_plotworld)) {
				if(retLoc(cfg, "plotworld") == null) {
					api.sendMSGReady(p, "event.worldteleporter.notset");
				}else {
					p.teleport(retLoc(cfg, "plotworld"));
					p.sendMessage(api.prefix("main") + api.returnStringReady(p, "event.worldteleporter.success").replace("%type", "§aPlotworld"));
				}
				e.setCancelled(true);
				p.closeInventory();
			}else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(wt_theend)) {
				if(retLoc(cfg, "theend") == null) {
					api.sendMSGReady(p, "event.worldteleporter.notset");
				}else {
					p.teleport(retLoc(cfg, "theend"));
					p.sendMessage(api.prefix("main") + api.returnStringReady(p, "event.worldteleporter.success").replace("%type", "§1The End"));
				}
				p.closeInventory();
				e.setCancelled(true);
			}else {
				e.setCancelled(true);
			}
		}
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
	
	private static void sendPlayer(Player p, String server, String dserver) {
		APIs api = new APIs();
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try {
			p.sendMessage(api.prefix("main") + api.returnStringReady(p, "event.navigator.sendPlayer.success").replace("%server", dserver));
			out.writeUTF("Connect");
			out.writeUTF(server);
		} catch (IOException e) {
			api.sendMSGReady(p, "event.navigator.sendPlayer.failed");
		}
		p.sendPluginMessage(plugin, "BungeeCord", b.toByteArray());
	}
	
	private Location retLoc(YamlConfiguration cfg, String type) {
		Location loc = null;
		if(cfg.contains("Spawn." + type + ".WORLD")) {
			loc = new Location(Bukkit.getWorld(cfg.getString("Spawn." + type + ".WORLD")), cfg.getDouble("Spawn." + type + ".X"), cfg.getDouble("Spawn." + type + ".Y"), cfg.getDouble("Spawn." + type + ".Z"), (float)cfg.getDouble("Spawn." + type + ".YAW"), (float)cfg.getDouble("Spawn." + type + ".PITCH"));
		}else {
			loc = null;
		}
		return loc;
	}
	
	public static void worldteleporter(Player p) {
		APIs api = new APIs();
		Inventory inv = Bukkit.createInventory(null, 9*1, wt_inventory);
		ItemStack item = api.defItem(Material.GRAY_STAINED_GLASS_PANE, 1, "");
		if(api.getServerName().equalsIgnoreCase("Farmserver")) {
			inv.setItem(0, item);
			inv.setItem(1, item);
			//inv.setItem(2, api.defItem(Material.GRASS_BLOCK, 1, wt_overworld));
			inv.setItem(2, api.wt_item(Material.GRASS_BLOCK, wt_overworld, "world"));
			inv.setItem(3, item);
			//inv.setItem(4, api.defItem(Material.NETHERRACK, 1, wt_nether));
			inv.setItem(4, api.wt_item(Material.NETHERRACK, wt_nether, "world_nether"));
			inv.setItem(5, item);
			//inv.setItem(6, api.defItem(Material.END_STONE, 1, wt_theend));
			inv.setItem(6, api.wt_item(Material.END_STONE, wt_theend, "world_the_end"));
			inv.setItem(7, item);
			inv.setItem(8, item);
		}else if(api.getServerName().equalsIgnoreCase("Survival")) {
			inv.setItem(0, item);
			inv.setItem(1, item);
			inv.setItem(2, item);
			inv.setItem(3, api.defItem(Material.GRASS_BLOCK, 1, wt_freebuild));
			inv.setItem(4, item);
			inv.setItem(5, api.defItem(Material.GRASS_BLOCK, 1, wt_plotworld));
			inv.setItem(6, item);
			inv.setItem(7, item);
			inv.setItem(8, item);
		}
		p.openInventory(inv);
		p.updateInventory();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage(Main.consolesend);
		}else {
			Player p = (Player)sender;
			mainnavi(p);
			APIs api = new APIs();
			api.sendMSGReady(p, "cmd.openinv");
		}
		return false;
	}
}