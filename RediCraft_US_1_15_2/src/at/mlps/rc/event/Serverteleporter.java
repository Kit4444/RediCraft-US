package at.mlps.rc.event;

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

import at.mlps.rc.api.APIs;
import at.mlps.rc.main.Main;
import at.mlps.rc.mysql.lb.MySQL;

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
	static String towny = "§6Towny";
	static String farmserver = "§5Farmserver";
	static String bauserver = "§bStaffserver";
	
	static String wt_inventory = "§aWorld§cTeleporter";
	static String wt_freebuild = "§aFreebuild";
	static String wt_plotworld = "§aPlotworld";
	static String wt_theend = "§0The End";
	static String wt_nether = "§cNether";
	
	static String locked = " §7- §4locked";
	static String monitored = " §7- §9monitoring";
	static String offline = " §7- §eoffline";
	
	static File spawnfile = new File("plugins/RCUSS/spawn.yml");
	
	//main servernavi
	public static void mainnavi(Player p) {
		Inventory inv = Bukkit.createInventory(null, 9*3, title);
		inv.setItem(0, APIs.defItem(Material.BLACK_STAINED_GLASS_PANE, 1, "§0"));
		inv.setItem(1, APIs.defItem(Material.BLACK_STAINED_GLASS_PANE, 1, "§0"));
		inv.setItem(3, APIs.defItem(Material.BLACK_STAINED_GLASS_PANE, 1, "§0"));
		inv.setItem(4, APIs.defItem(Material.BLACK_STAINED_GLASS_PANE, 1, "§0"));
		inv.setItem(5, APIs.defItem(Material.BLACK_STAINED_GLASS_PANE, 1, "§0"));
		inv.setItem(6, APIs.defItem(Material.BLACK_STAINED_GLASS_PANE, 1, "§0"));
		inv.setItem(7, APIs.defItem(Material.BLACK_STAINED_GLASS_PANE, 1, "§0"));
		inv.setItem(8, APIs.defItem(Material.BLACK_STAINED_GLASS_PANE, 1, "§0"));
		inv.setItem(9, APIs.defItem(Material.BLACK_STAINED_GLASS_PANE, 1, "§0"));
		inv.setItem(11, APIs.defItem(Material.BLACK_STAINED_GLASS_PANE, 1, "§0"));
		inv.setItem(12, APIs.defItem(Material.BLACK_STAINED_GLASS_PANE, 1, "§0"));
		inv.setItem(14, APIs.defItem(Material.BLACK_STAINED_GLASS_PANE, 1, "§0"));
		inv.setItem(15, APIs.defItem(Material.BLACK_STAINED_GLASS_PANE, 1, "§0"));
		inv.setItem(17, APIs.defItem(Material.BLACK_STAINED_GLASS_PANE, 1, "§0"));
		inv.setItem(18, APIs.defItem(Material.BLACK_STAINED_GLASS_PANE, 1, "§0"));
		inv.setItem(19, APIs.defItem(Material.BLACK_STAINED_GLASS_PANE, 1, "§0"));
		inv.setItem(21, APIs.defItem(Material.BLACK_STAINED_GLASS_PANE, 1, "§0"));
		inv.setItem(22, APIs.defItem(Material.BLACK_STAINED_GLASS_PANE, 1, "§0"));
		inv.setItem(23, APIs.defItem(Material.BLACK_STAINED_GLASS_PANE, 1, "§0"));
		inv.setItem(25, APIs.defItem(Material.BLACK_STAINED_GLASS_PANE, 1, "§0"));
		//inv w/ items
		inv.setItem(6, APIs.naviItem(Material.DIAMOND_PICKAXE, creative, "Creative"));
		inv.setItem(10, APIs.naviItem(Material.GRASS_BLOCK, skyblock, "SkyBlock"));
		inv.setItem(16, APIs.naviItem(Material.GOLDEN_SHOVEL, farmserver, "Farmserver"));
		inv.setItem(20, APIs.naviItem(Material.BRICKS, towny, "Towny"));
		inv.setItem(24, APIs.naviItem(Material.IRON_AXE, survival, "Survival"));
		if(APIs.getServerName().equalsIgnoreCase("Farmserver")) {
			inv.setItem(2, APIs.defItem(Material.EMERALD, 1, wt_inventory)); //dailyrewards
			inv.setItem(13, APIs.defItem(Material.NETHER_STAR, 1, spawn)); //spawn
			if(p.hasPermission("mlps.isTeam")) {
				inv.setItem(26, APIs.naviItem(Material.WOODEN_AXE, bauserver, "Staffserver"));
			}else {
				inv.setItem(26, APIs.defItem(Material.BLACK_STAINED_GLASS_PANE, 1, "§0"));
			}
		}else {
			if(p.hasPermission("mlps.isTeam")) {
				inv.setItem(2, APIs.naviItem(Material.WOODEN_AXE, bauserver, "Staffserver"));
				inv.setItem(26, APIs.defItem(Material.BLACK_STAINED_GLASS_PANE, 1, "§0"));
			}else {
				inv.setItem(2, APIs.defItem(Material.BLACK_STAINED_GLASS_PANE, 1, "§0"));
				inv.setItem(26, APIs.defItem(Material.BLACK_STAINED_GLASS_PANE, 1, "§0"));
			}
			inv.setItem(13, APIs.defItem(Material.NETHER_STAR, 1, spawn)); //spawn
		}
		p.openInventory(inv);
		p.updateInventory();
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		Player p = (Player)e.getWhoClicked();
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(spawnfile);
		if(e.getView().getTitle().equalsIgnoreCase(title)) {
			e.setCancelled(true);
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(dailyrew)) {
				e.setCancelled(true);
				p.closeInventory();
				p.teleport(retLoc(cfg, "drew"));
				APIs.sendMSGReady(p, "event.navigator.local.dailyreward");
			}else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(spawn)) {
				e.setCancelled(true);
				boolean lock = getData("Lobby", "locked");
				if(lock) {
					p.sendMessage(APIs.prefix("main") + APIs.returnStringReady(p, "event.navigator.sendPlayer.locked").replace("%server", "Lobby"));
				}else {
					boolean monitor = getData("Lobby", "monitoring");
					boolean online = getData("Lobby", "online");
					if(online) {
						if(monitor) {
							APIs.sendMSGReady(p, "event.navigator.sendPlayer.monitorinfo");
							sendPlayer(p, "lobby", spawn);
						}else {
							sendPlayer(p, "lobby", spawn);
						}
					}else {
						p.sendMessage(APIs.prefix("main") + APIs.returnStringReady(p, "event.navigator.sendPlayer.offline").replace("%server", "Lobby"));
					}
					
				}
			}else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(wt_inventory)) {
				e.setCancelled(true);
				if(APIs.getServerName().equalsIgnoreCase("farmserver") || APIs.getServerName().equalsIgnoreCase("survival")) {
					worldteleporter(p);
				}
			}else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(creative)) {
				e.setCancelled(true);
				boolean lock = getData("Creative", "locked");
				if(lock) {
					p.sendMessage(APIs.prefix("main") + APIs.returnStringReady(p, "event.navigator.sendPlayer.locked").replace("%server", "Creative"));
				}else {
					boolean monitor = getData("Creative", "monitoring");
					boolean online = getData("Creative", "online");
					if(online) {
						if(monitor) {
							APIs.sendMSGReady(p, "event.navigator.sendPlayer.monitorinfo");
							sendPlayer(p, "creative", creative);
						}else {
							sendPlayer(p, "creative", creative);
						}
					}else {
						p.sendMessage(APIs.prefix("main") + APIs.returnStringReady(p, "event.navigator.sendPlayer.offline").replace("%server", "Creative"));
					}
					
				}
			}else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(survival)) {
				e.setCancelled(true);
				boolean lock = getData("Survival", "locked");
				if(lock) {
					p.sendMessage(APIs.prefix("main") + APIs.returnStringReady(p, "event.navigator.sendPlayer.locked").replace("%server", "Survival"));
				}else {
					boolean monitor = getData("Survival", "monitoring");
					boolean online = getData("Survival", "online");
					if(online) {
						if(monitor) {
							APIs.sendMSGReady(p, "event.navigator.sendPlayer.monitorinfo");
							sendPlayer(p, "survival", survival);
						}else {
							sendPlayer(p, "survival", survival);
						}
					}else {
						p.sendMessage(APIs.prefix("main") + APIs.returnStringReady(p, "event.navigator.sendPlayer.offline").replace("%server", "Survival"));
					}
					
				}
			}else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(skyblock)) {
				e.setCancelled(true);
				boolean lock = getData("SkyBlock", "locked");
				if(lock) {
					p.sendMessage(APIs.prefix("main") + APIs.returnStringReady(p, "event.navigator.sendPlayer.locked").replace("%server", "SkyBlock"));
				}else {
					boolean monitor = getData("SkyBlock", "monitoring");
					boolean online = getData("SkyBlock", "online");
					if(online) {
						if(monitor) {
							APIs.sendMSGReady(p, "event.navigator.sendPlayer.monitorinfo");
							sendPlayer(p, "skyblock", skyblock);
						}else {
							sendPlayer(p, "skyblock", skyblock);
						}
					}else {
						p.sendMessage(APIs.prefix("main") + APIs.returnStringReady(p, "event.navigator.sendPlayer.offline").replace("%server", "SkyBlock"));
					}
					
				}
			}else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(farmserver)) {
				e.setCancelled(true);
				boolean lock = getData("Farmserver", "locked");
				if(lock) {
					p.sendMessage(APIs.prefix("main") + APIs.returnStringReady(p, "event.navigator.sendPlayer.locked").replace("%server", "Farmserver"));
				}else {
					boolean monitor = getData("Farmserver", "monitoring");
					boolean online = getData("Farmserver", "online");
					if(online) {
						if(monitor) {
							APIs.sendMSGReady(p, "event.navigator.sendPlayer.monitorinfo");
							sendPlayer(p, "farmserver", farmserver);
						}else {
							sendPlayer(p, "farmserver", farmserver);
						}
					}else {
						p.sendMessage(APIs.prefix("main") + APIs.returnStringReady(p, "event.navigator.sendPlayer.offline").replace("%server", "Creative"));
					}
					
				}
			}else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(towny)) {
				e.setCancelled(true);
				boolean lock = getData("Towny", "locked");
				if(lock) {
					p.sendMessage(APIs.prefix("main") + APIs.returnStringReady(p, "event.navigator.sendPlayer.locked").replace("%server", "Towny"));
				}else {
					boolean monitor = getData("Towny", "monitoring");
					boolean online = getData("Towny", "online");
					if(online) {
						if(monitor) {
							APIs.sendMSGReady(p, "event.navigator.sendPlayer.monitorinfo");
							sendPlayer(p, "towny", towny);
						}else {
							sendPlayer(p, "towny", towny);
						}
					}else {
						p.sendMessage(APIs.prefix("main") + APIs.returnStringReady(p, "event.navigator.sendPlayer.offline").replace("%server", "Towny"));
					}
					
				}
			}else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(bauserver)) {
				e.setCancelled(true);
				boolean lock = getData("Staffserver", "locked");
				if(lock) {
					p.sendMessage(APIs.prefix("main") + APIs.returnStringReady(p, "event.navigator.sendPlayer.locked").replace("%server", "Staffserver"));
				}else {
					boolean monitor = getData("Staffserver", "monitoring");
					boolean online = getData("Staffserver", "online");
					if(online) {
						if(monitor) {
							APIs.sendMSGReady(p, "event.navigator.sendPlayer.monitorinfo");
							sendPlayer(p, "bauserver", bauserver);
						}else {
							sendPlayer(p, "bauserver", bauserver);
						}
					}else {
						p.sendMessage(APIs.prefix("main") + APIs.returnStringReady(p, "event.navigator.sendPlayer.offline").replace("%server", "Staffserver"));
					}
					
				}
			}
		}else if(e.getView().getTitle().equalsIgnoreCase(wt_inventory)) {
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(wt_freebuild)) {
				if(retLoc(cfg, "freebuild") == null) {
					APIs.sendMSGReady(p, "event.worldteleporter.notset");
				}else {
					p.teleport(retLoc(cfg, "freebuild"));
					APIs.sendMSGReady(p, "event.worldteleporter.freebuild");
				}
				e.setCancelled(true);
				p.closeInventory();
			}else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(wt_nether)) {
				if(retLoc(cfg, "nether") == null) {
					APIs.sendMSGReady(p, "event.worldteleporter.notset");
				}else {
					p.teleport(retLoc(cfg, "nether"));
					APIs.sendMSGReady(p, "event.worldteleporter.nether");
				}
				e.setCancelled(true);
				p.closeInventory();
			}else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(wt_plotworld)) {
				if(retLoc(cfg, "plotworld") == null) {
					APIs.sendMSGReady(p, "event.worldteleporter.notset");
				}else {
					p.teleport(retLoc(cfg, "plotworld"));
					APIs.sendMSGReady(p, "event.worldteleporter.plotworld");
				}
				e.setCancelled(true);
				p.closeInventory();
			}else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(wt_theend)) {
				if(retLoc(cfg, "theend") == null) {
					APIs.sendMSGReady(p, "event.worldteleporter.notset");
				}else {
					p.teleport(retLoc(cfg, "theend"));
					APIs.sendMSGReady(p, "event.worldteleporter.theend");
				}
				p.closeInventory();
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
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try {
			p.sendMessage(APIs.prefix("main") + APIs.returnStringReady(p, "event.navigator.sendPlayer.success").replace("%server", dserver));
			out.writeUTF("Connect");
			out.writeUTF(server);
		} catch (IOException e) {
			APIs.sendMSGReady(p, "event.navigator.sendPlayer.failed");
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
		Inventory inv = Bukkit.createInventory(null, 9*1, wt_inventory);
		ItemStack item = APIs.defItem(Material.GRAY_STAINED_GLASS_PANE, 1, "");
		if(APIs.getServerName().equalsIgnoreCase("Farmserver")) {
			inv.setItem(0, item);
			inv.setItem(1, item);
			inv.setItem(2, APIs.defItem(Material.GRASS_BLOCK, 1, wt_freebuild));
			inv.setItem(3, item);
			inv.setItem(4, APIs.defItem(Material.NETHERRACK, 1, wt_nether));
			inv.setItem(5, item);
			inv.setItem(6, APIs.defItem(Material.END_STONE, 1, wt_theend));
			inv.setItem(7, item);
			inv.setItem(8, item);
		}else if(APIs.getServerName().equalsIgnoreCase("Survival")) {
			inv.setItem(0, item);
			inv.setItem(1, item);
			inv.setItem(2, item);
			inv.setItem(3, APIs.defItem(Material.GRASS_BLOCK, 1, wt_freebuild));
			inv.setItem(4, item);
			inv.setItem(5, APIs.defItem(Material.GRASS_BLOCK, 1, wt_plotworld));
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
			APIs.sendMSGReady(p, "cmd.openinv");
		}
		return false;
	}
}