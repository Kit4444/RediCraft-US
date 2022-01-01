package at.mlps.rc.cmd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import at.mlps.rc.api.APIs;
import at.mlps.rc.api.ActionLogger;
import at.mlps.rc.mysql.lb.MySQL;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class RedeemGifts implements CommandExecutor, Listener{
	
	/*
	 * 1*:viprole
	 * 3d:xpboost
	 * 1*:xperpts
	 * 1*:enchItems
	 */
	
	static HashMap<UUID, String> codeName = new HashMap<>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player)sender;
			APIs api = new APIs();
			if(args.length == 1) {
				String code = args[0];
				if(code.equalsIgnoreCase("list")) {
					p.sendMessage(getGifts(p));
				}else {
					if(checkCode(p, code)) {
						if(!hasCodeUsed(p, code)) {
							String giftKey = getGift(p, code);
							codeName.put(p.getUniqueId(), code);
							switch(giftKey) {
							case "1*:viprole": openDecideInv(p, "VIP Role", 150000); break;
							case "3d:xpboost": openDecideInv(p, "3 Days XP Boost", 25000); break;
							case "1*:xperpts": openDecideInv(p, "XP Shot", 10000); break;
							case "1*:enchItems": openDecideInv(p, "Enchanted Items Fullset", 400000); break;
							}
						}else {
							p.sendMessage(api.prefix("main") + "§7This code has been used already.");
						}
					}
				}
			}else {
				p.sendMessage(api.prefix("main") + api.returnStringReady(p, "usage") + "§7 /redeemgift <Giftcode|List>");
			}
		}else {
			Bukkit.getConsoleSender().sendMessage("Bitte nur Ingame ausführen!");
		}
		return false;
	}
	
	@EventHandler
	public void onInteract(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		APIs api = new APIs();
		if(e.getView().getTitle().equalsIgnoreCase("§7Gift: §aVIP Role")) {
			e.setCancelled(true);
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aAccept")) {
				PermissionUser po = PermissionsEx.getUser(p);
				if(po.inGroup("pman") || po.inGroup("cman") || po.inGroup("gmmman") || po.inGroup("dev") || po.inGroup("hr") || po.inGroup("cm") || po.inGroup("ct") || po.inGroup("st") || po.inGroup("bd") || po.inGroup("gm") || po.inGroup("aot") || po.inGroup("train") || po.inGroup("rltm") || po.inGroup("rtm") || po.inGroup("part") || po.inGroup("fs") || po.inGroup("nb") || po.inGroup("bt")) {
					p.sendMessage(api.prefix("main") + "§7We will moneytize this gift, as you have a higher role.");
					MoneyAPI.addMoney(p.getUniqueId(), 150000);
					ActionLogger.log(api.getServerName(), p, "Player attempted to redeem VIP Role, moneytized it due to having a higher role.");
				}else {
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + p.getName() + " group set vip");
					p.sendMessage(api.prefix("main") + "§7You have now the role VIP.");
					ActionLogger.log(api.getServerName(), p, "Player redeemed VIP Role.");
				}
				setCodeUsed(p, codeName.get(p.getUniqueId()));
				codeName.remove(p.getUniqueId());
				p.closeInventory();
			}else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§eMoneytize")) {
				p.sendMessage(api.prefix("main") + "§7You have moneytized your gift.");
				MoneyAPI.addMoney(p.getUniqueId(), 150000);
				setCodeUsed(p, codeName.get(p.getUniqueId()));
				codeName.remove(p.getUniqueId());
				p.closeInventory();
				ActionLogger.log(api.getServerName(), p, "Player moneytized the VIP Role.");
			}else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cDecline")) {
				setCodeUsed(p, codeName.get(p.getUniqueId()));
				codeName.remove(p.getUniqueId());
				p.sendMessage(api.prefix("main") + "§7You have declined your gift.");
				p.closeInventory();
				ActionLogger.log(api.getServerName(), p, "Player declined the VIP Role.");
			}
		}else if(e.getView().getTitle().equalsIgnoreCase("§7Gift: §a3 Days XP Boost")) {
			e.setCancelled(true);
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aAccept")) {
				setCodeUsed(p, codeName.get(p.getUniqueId()));
				codeName.remove(p.getUniqueId());
				long current = System.currentTimeMillis() / 1000;
				long threedays = 2592000;
				long in3d = current + threedays;
				setGift(p, in3d, "3d:xpboost");
				p.sendMessage(api.prefix("main") + "§7You have now 3 days Double XP Boost.");
				p.closeInventory();
				ActionLogger.log(api.getServerName(), p, "Player redeemed the 3D XP Boost.");
			}else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§eMoneytize")) {
				p.sendMessage(api.prefix("main") + "§7You have moneytized your gift.");
				MoneyAPI.addMoney(p.getUniqueId(), 25000);
				setCodeUsed(p, codeName.get(p.getUniqueId()));
				codeName.remove(p.getUniqueId());
				p.closeInventory();
				ActionLogger.log(api.getServerName(), p, "Player moneytized the 3D XP Boost.");
			}else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cDecline")) {
				setCodeUsed(p, codeName.get(p.getUniqueId()));
				codeName.remove(p.getUniqueId());
				p.sendMessage(api.prefix("main") + "§7You have declined your gift.");
				p.closeInventory();
				ActionLogger.log(api.getServerName(), p, "Player declined the 3D XP Boost.");
			}
		}else if(e.getView().getTitle().equalsIgnoreCase("§7Gift: §aXP Shot")) {
			e.setCancelled(true);
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aAccept")) {
				setCodeUsed(p, codeName.get(p.getUniqueId()));
				codeName.remove(p.getUniqueId());
				int random1 = random(0, 4);
				int random2 = 0;
				switch(random1) {
				case 0: random2 = random(0, 100);
				case 1: random2 = random(25, 200);
				case 2: random2 = random(50, 300);
				case 3: random2 = random(75, 400);
				case 4: random2 = random(100, 500);
				}
				ActionLogger.log(api.getServerName(), p, "Player redeemed the XP Shot.");
				p.giveExp(random2);
				p.sendMessage(api.prefix("main") + "§7You have received§a " + random2 + " §7XP.");
				p.closeInventory();
			}else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§eMoneytize")) {
				p.sendMessage(api.prefix("main") + "§7You have moneytized your gift.");
				MoneyAPI.addMoney(p.getUniqueId(), 10000);
				setCodeUsed(p, codeName.get(p.getUniqueId()));
				codeName.remove(p.getUniqueId());
				p.closeInventory();
				ActionLogger.log(api.getServerName(), p, "Player moneytized the XP Shot.");
			}else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cDecline")) {
				setCodeUsed(p, codeName.get(p.getUniqueId()));
				codeName.remove(p.getUniqueId());
				p.sendMessage(api.prefix("main") + "§7You have declined your gift.");
				p.closeInventory();
				ActionLogger.log(api.getServerName(), p, "Player declined the XP Shot.");
			}
		}else if(e.getView().getTitle().equalsIgnoreCase("§7Gift: §aEnchanted Items Fullset")) {
			e.setCancelled(true);
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aAccept")) {
				setCodeUsed(p, codeName.get(p.getUniqueId()));
				codeName.remove(p.getUniqueId());
				
				//--- Items ---//
				ItemStack helmet = new ItemStack(Material.NETHERITE_HELMET, 1);
				ItemStack chestplate = new ItemStack(Material.NETHERITE_CHESTPLATE, 1);
				ItemStack leggings = new ItemStack(Material.NETHERITE_LEGGINGS, 1);
				ItemStack boots = new ItemStack(Material.NETHERITE_BOOTS, 1);
				ItemStack sword = new ItemStack(Material.NETHERITE_SWORD, 1);
				ItemStack pickaxe = new ItemStack(Material.NETHERITE_PICKAXE, 1);
				ItemStack axe = new ItemStack(Material.NETHERITE_AXE, 1);
				ItemStack shovel = new ItemStack(Material.NETHERITE_SHOVEL, 1);
				ItemStack beef = new ItemStack(Material.COOKED_BEEF, 64);
				
				ItemMeta mhelmet = helmet.getItemMeta();
				ItemMeta mchest = chestplate.getItemMeta();
				ItemMeta mleggings = leggings.getItemMeta();
				ItemMeta mboot = boots.getItemMeta();
				ItemMeta msword = sword.getItemMeta();
				ItemMeta mpick = pickaxe.getItemMeta();
				ItemMeta maxe = axe.getItemMeta();
				ItemMeta mshovel = shovel.getItemMeta();
				
				mhelmet.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
				mhelmet.addEnchant(Enchantment.DURABILITY, 2, true);
				mhelmet.addEnchant(Enchantment.OXYGEN, 3, true);
				
				mchest.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
				mchest.addEnchant(Enchantment.DURABILITY, 2, true);
				
				mleggings.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
				mleggings.addEnchant(Enchantment.DURABILITY, 2, true);
				
				mboot.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
				mboot.addEnchant(Enchantment.DURABILITY, 2, true);
				mboot.addEnchant(Enchantment.DEPTH_STRIDER, 1, true);
				
				msword.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
				msword.addEnchant(Enchantment.DAMAGE_ALL, 3, true);
				msword.addEnchant(Enchantment.FIRE_ASPECT, 2, true);
				
				mpick.addEnchant(Enchantment.DURABILITY, 3, true);
				mpick.addEnchant(Enchantment.DIG_SPEED, 3, true);
				
				maxe.addEnchant(Enchantment.DURABILITY, 3, true);
				maxe.addEnchant(Enchantment.DIG_SPEED, 3, true);
				
				mshovel.addEnchant(Enchantment.DURABILITY, 3, true);
				mshovel.addEnchant(Enchantment.DIG_SPEED, 3, true);
				
				helmet.setItemMeta(mhelmet);
				chestplate.setItemMeta(mchest);
				leggings.setItemMeta(mleggings);
				boots.setItemMeta(mboot);
				sword.setItemMeta(msword);
				pickaxe.setItemMeta(mpick);
				axe.setItemMeta(maxe);
				shovel.setItemMeta(mshovel);
				p.getInventory().addItem(helmet, chestplate, leggings, boots, sword, pickaxe, axe, shovel, beef);
				p.sendMessage(api.prefix("main") + "You've got your Netherite Fullset enchanted.");
				p.closeInventory();
				ActionLogger.log(api.getServerName(), p, "Player redeemed the netherite fullset.");
			}else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§eMoneytize")) {
				p.sendMessage(api.prefix("main") + "§7You have moneytized your gift.");
				MoneyAPI.addMoney(p.getUniqueId(), 400000);
				setCodeUsed(p, codeName.get(p.getUniqueId()));
				codeName.remove(p.getUniqueId());
				p.closeInventory();
				ActionLogger.log(api.getServerName(), p, "Player moneytized the netherite fullset.");
			}else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cDecline")) {
				setCodeUsed(p, codeName.get(p.getUniqueId()));
				codeName.remove(p.getUniqueId());
				p.sendMessage(api.prefix("main") + "§7You have declined your gift.");
				p.closeInventory();
				ActionLogger.log(api.getServerName(), p, "Player declined the netherite fullset.");
			}
		}
	}
	
	private void openDecideInv(Player p, String titleAddition, int alternativeMoney) {
		Inventory inv = Bukkit.createInventory(null, 9*3, "§7Gift: §a" + titleAddition);
		APIs api = new APIs();
		inv.setItem(11, api.defItem(Material.LIME_WOOL, 1, "§aAccept"));
		inv.setItem(13, api.l2Item(Material.YELLOW_WOOL, 1, "§eMoneytize", "§7This Gift will be Moneytized", "§7You will get " + alternativeMoney + " Coins"));
		inv.setItem(15, api.defItem(Material.RED_WOOL, 1, "§cDecline"));
		p.openInventory(inv);
		p.updateInventory();
	}
	
	private void setGift(Player p, long time, String giftkey) {
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE redicore_userstats SET gift_time = ?, gift_key = ? WHERE uuid = ?");
			ps.setLong(1, time);
			ps.setString(2, giftkey);
			ps.setString(3, p.getUniqueId().toString().replace("-", ""));
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private String getGifts(Player p) {
		List<String> gifts = new ArrayList<>();
		int i = 0;
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redicore_gifts WHERE uuid = ?");
			ps.setString(1, p.getUniqueId().toString());
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				i++;
				gifts.add("§7ID: §a" + i + " §7| Giftcode: §a" + rs.getString("giftcode") + " §7| Gift: §a" + translateGiftkey(rs.getString("giftkey")) + " §7| Used:§a " + translateBoolean(rs.getBoolean("used")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(gifts.size() != 0) {
			StringBuilder sb = new StringBuilder();
			for(String s : gifts) {
				sb.append(s);
				sb.append("\n");
			}
			return sb.toString();
		}else {
			return "You do not have any Gifts.";
		}
	}
	
	private String translateBoolean(boolean toTranslate) {
		if(toTranslate) {
			return "§nyes";
		}else {
			return "§ano";
		}
	}
	
	private String translateGiftkey(String toTranslate) {
		String translated = "";
		switch(toTranslate) {
		case "1*:viprole": translated = "VIP Role"; break;
		case "3d:xpboost": translated = "3 Days Double XPs"; break;
		case "1*:xperpts": translated = "Experience Points Shot"; break;
		case "1*:enchItems": translated = "Enchanted Items Fullset"; break;
		}
		return translated;
	}
	
	private void setCodeUsed(Player p, String giftCode) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy - hh:mm:ss");
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE redicore_gifts SET used = ?, usedAt = ? WHERE giftcode = ? AND uuid = ?");
			ps.setBoolean(1, true);
			ps.setString(2, sdf.format(new Date()));
			ps.setString(3, giftCode);
			ps.setString(4, p.getUniqueId().toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private boolean hasCodeUsed(Player p, String giftCode) {
		boolean bool = false;
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redicore_gifts WHERE uuid = ? AND giftcode = ?");
			ps.setString(1, p.getUniqueId().toString());
			ps.setString(2, giftCode);
			ResultSet rs = ps.executeQuery();
			rs.next();
			bool = rs.getBoolean("used");
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bool;
	}
	
	private boolean checkCode(Player p, String giftCode) {
		boolean bool = false;
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redicore_gifts WHERE uuid = ? AND giftcode = ?");
			ps.setString(1, p.getUniqueId().toString());
			ps.setString(2, giftCode);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				bool = true;
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bool;
	}
	
	private String getGift(Player p, String giftCode) {
		String giftKey = "";
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT giftkey FROM redicore_gifts WHERE uuid = ? AND giftcode = ?");
			ps.setString(1, p.getUniqueId().toString());
			ps.setString(2, giftCode);
			ResultSet rs = ps.executeQuery();
			rs.next();
			giftKey = rs.getString("giftkey");
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return giftKey;
	}
	
	private static int random(int low, int max) {
		Random r = new Random();
		int number = r.nextInt(max);
		while(number < low) {
			number = r.nextInt(max);
		}
		return number;
	}

}