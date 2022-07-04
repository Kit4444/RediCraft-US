package at.kitsoft.redicraft.cmd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import at.kitsoft.redicraft.api.APIs;
import at.kitsoft.redicraft.api.ActionLogger;
import at.kitsoft.redicraft.main.Main;
import at.kitsoft.redicraft.mysql.lb.MySQL;

public class MoneyAPI implements Listener, CommandExecutor {

	static int incomeTaxRate = 10;
	static int interestTaxRate = 5;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage(Main.consolesend);
		} else {
			APIs api = new APIs();
			Player p = (Player) sender;
			if (cmd.getName().equalsIgnoreCase("money")) {
				if (args.length == 0) {
					p.sendMessage(api.prefix("main") + api.returnStringReady(p, "cmd.money.player.own")
							.replace("%money", String.valueOf(getMoney(p.getUniqueId()))));
					p.sendMessage(api.prefix("main") + api.returnStringReady(p, "cmd.money.bank.own").replace("%money",
							String.valueOf(getBankMoney(p.getUniqueId()))));
					ActionLogger.log(api.getServerName(), p, "Player used money command.");
				} else if (args.length == 1) {
					Player p2 = Bukkit.getPlayerExact(args[0]);
					p.sendMessage(api.prefix("main") + api.returnStringReady(p, "cmd.money.player.other")
							.replace("%money", String.valueOf(getMoney(p2.getUniqueId())))
							.replace("%displayer", p2.getDisplayName()));
					p.sendMessage(api.prefix("main") + api.returnStringReady(p, "cmd.money.bank.other").replace(
							"%money",
							String.valueOf(getBankMoney(p2.getUniqueId())).replace("%displayer", p2.getDisplayName())));
					ActionLogger.log(api.getServerName(), p, "Player used money command for " + p2.getName() + ".");
				} else {
					p.sendMessage(api.prefix("main") + api.returnStringReady(p, "usage") + " §7/money [Player]");
				}
			} else if (cmd.getName().equalsIgnoreCase("setmoney")) {
				if (p.hasPermission("mlps.setmoney")) {
					if (args.length == 0) {
						p.sendMessage(api.prefix("main") + api.returnStringReady(p, "usage")
								+ " §7/setmoney <Player> <Money>");
					} else if (args.length >= 1 && args.length <= 2) {
						Player p2 = Bukkit.getPlayerExact(args[0]);
						if (p2 == null) {
							api.sendMSGReady(p, "cmd.setmoney.playernotonline");
						} else {
							int money = Integer.parseInt(args[1]);
							if (money < 0) {
								api.sendMSGReady(p, "cmd.setmoney.belowzero");
								ActionLogger.log(api.getServerName(), p,
										"Player attempted to use setmoney command, used negative value!");
							} else {
								setMoney(p2.getUniqueId(), money);
								p.sendMessage(api.prefix("main") + api.returnStringReady(p, "cmd.setmoney.successfull")
										.replace("%money", args[1]).replace("%displayer", p2.getDisplayName()));
								ActionLogger.log(api.getServerName(), p,
										"Player used setmoney command, setted money to " + money + " coins for "
												+ p2.getName() + ".");
							}
						}
					} else {
						p.sendMessage(api.prefix("main") + api.returnStringReady(p, "usage")
								+ " §7/setmoney <Player> <Money>");
					}
				} else {
					api.noPerm(p);
					ActionLogger.log(api.getServerName(), p, "Player attempted to use setmoney command.");
				}
			} else if (cmd.getName().equalsIgnoreCase("addmoney")) {
				if (p.hasPermission("mlps.addmoney")) {
					if (args.length >= 1 && args.length <= 2) {
						Player p2 = Bukkit.getPlayerExact(args[0]);
						if (p2 == null) {
							api.sendMSGReady(p, "cmd.addmoney.playernotonline");
						} else {
							if (args[1].matches("^[0-9]+$")) {
								int money = Integer.parseInt(args[1]);
								int oldmoney = getMoney(p2.getUniqueId());
								int newmoney = (money + oldmoney);
								setMoney(p2.getUniqueId(), newmoney);
								p.sendMessage(api.prefix("main") + api.returnStringReady(p, "cmd.addmoney.successfull")
										.replace("%money", String.valueOf(newmoney))
										.replace("%displayer", p2.getDisplayName())
										.replace("%newmoney", String.valueOf(money)));
								ActionLogger.log(api.getServerName(), p, "Player used addmoney command, added " + money
										+ " coins to" + p2.getName() + " account.");
							}
						}
					} else {
						p.sendMessage(api.prefix("main") + api.returnStringReady(p, "usage")
								+ " §7/addmoney <Player> <Money>");
					}
				} else {
					api.noPerm(p);
					ActionLogger.log(api.getServerName(), p, "Player attempted to use addmoney command.");
				}
			} else if (cmd.getName().equalsIgnoreCase("removemoney")) {
				if (p.hasPermission("mlps.removemoney")) {
					if (args.length >= 1 && args.length <= 2) {
						Player p2 = Bukkit.getPlayerExact(args[0]);
						if (p2 == null) {
							api.sendMSGReady(p, "cmd.removemoney.playernotonline");
						} else {
							if (args[1].matches("^[0-9]+$")) {
								int money = Integer.parseInt(args[1]);
								int oldmoney = getMoney(p.getUniqueId());
								if (money <= oldmoney) {
									setMoney(p2.getUniqueId(), (oldmoney - money));
									p.sendMessage(
											api.prefix("main") + api.returnStringReady(p, "cmd.removemoney.successfull")
													.replace("%money", String.valueOf(money))
													.replace("%displayer", p2.getDisplayName())
													.replace("%newmoney", String.valueOf(money)));
									ActionLogger.log(api.getServerName(), p, "Player used removemoney command, removed "
											+ money + " coins from " + p2.getName() + " account.");
								} else {
									api.sendMSGReady(p, "cmd.removemoney.belowzero");
									ActionLogger.log(api.getServerName(), p,
											"Player attempted to use removemoney command, new value would've been negative.");
								}
							}
						}
					} else {
						p.sendMessage(api.prefix("main") + api.returnStringReady(p, "usage")
								+ " §7/removemoney <Player> <Money>");
					}
				} else {
					api.noPerm(p);
				}
			} else if (cmd.getName().equalsIgnoreCase("pay")) {
				if (args.length >= 1 && args.length <= 2) {
					Player p2 = Bukkit.getPlayerExact(args[0]);
					if (p2 == null) {
						api.sendMSGReady(p, "cmd.pay.playernotonline");
					} else {
						int money = Integer.parseInt(args[1]);
						int oldmoney2 = getMoney(p2.getUniqueId());
						int oldmoney1 = getMoney(p.getUniqueId());
						if (money <= oldmoney1) {
							setMoney(p2.getUniqueId(), (oldmoney2 + money));
							setMoney(p.getUniqueId(), (oldmoney1 - money));
							ActionLogger.log(api.getServerName(), p,
									"Player used pay command, " + p2.getName() + " got " + money + " coins.");
							p.sendMessage(api.prefix("main") + api.returnStringReady(p, "cmd.pay.successfull")
									.replace("%displayer", p2.getDisplayName()).replace("%money", args[1]));
						} else {
							api.sendMSGReady(p, "cmd.pay.belowzero");
							ActionLogger.log(api.getServerName(), p,
									"Player attempted to use pay command, the value would've been negative.");
						}
					}
				} else {
					p.sendMessage(api.prefix("main") + api.returnStringReady(p, "usage") + " §7/pay <Player> <Money>");
				}
			} else if (cmd.getName().equalsIgnoreCase("topmoney")) {
				try {
					PreparedStatement ps = MySQL.getConnection()
							.prepareStatement("SELECT * FROM redicore_userstats ORDER BY money DESC LIMIT 10");
					ResultSet rs = ps.executeQuery();
					int i = 0;
					while (rs.next()) {
						i++;
						p.sendMessage("§7Place §a" + i + " §7| User: §a" + rs.getString("username") + " §7| Balance: §a"
								+ rs.getInt("money") + " §7Coins");
					}
					rs.first();
					ActionLogger.log(api.getServerName(), p, "Player used topmoney command. TOP: "
							+ rs.getString("username") + " with " + rs.getInt("money") + " coins");
				} catch (SQLException e) {
				}
			} else if (cmd.getName().equalsIgnoreCase("setbankmoney")) {
				if (args.length >= 1 && args.length <= 2) {
					if (p.hasPermission("mlps.setbankmoney")) {
						Player p2 = Bukkit.getPlayerExact(args[0]);
						if (p2 == null) {
							api.sendMSGReady(p, "cmd.setbankmoney.playernotonline");
						} else {
							int money = Integer.parseInt(args[1]);
							if (money <= 0) {
								api.sendMSGReady(p, "cmd.setbankmoney.belowzero");
							} else {
								setBankMoney(p2.getUniqueId(), money);
								p.sendMessage(
										api.prefix("main") + api.returnStringReady(p, "cmd.setbankmoney.successfull")
												.replace("%money", args[1]).replace("%displayer", p2.getDisplayName()));
								ActionLogger.log(api.getServerName(), p,
										"Player used setbankmoney command new bank balance is " + money + " coins.");
							}
						}
					} else {
						api.noPerm(p);
					}
				} else {
					p.sendMessage(api.prefix("main") + api.returnStringReady(p, "usage")
							+ " §7/setbankmoney <Player> <Money>");
				}
			} else if (cmd.getName().equalsIgnoreCase("bankdeposit")) { // add to bankaccount
				if (args.length == 1) {
					int money = Integer.parseInt(args[0]);
					int currMoney = getMoney(p.getUniqueId());
					int bankcurrMoney = getBankMoney(p.getUniqueId());
					if (money <= currMoney) {
						setBankMoney(p.getUniqueId(), (bankcurrMoney + money));
						setMoney(p.getUniqueId(), (currMoney - money));
						p.sendMessage(api.prefix("main")
								+ api.returnStringReady(p, "cmd.bankdeposit.successfull").replace("%money", args[0]));
						ActionLogger.log(api.getServerName(), p,
								"Player used bankdeposit command, deposed " + money + " coins.");
					} else {
						api.sendMSGReady(p, "cmd.bankdeposit.moreaspossible");
						ActionLogger.log(api.getServerName(), p,
								"Player attempted to use bankdeposit command, new value would've been negative.");
					}
				} else {
					p.sendMessage(api.prefix("main") + api.returnStringReady(p, "usage") + " §7/bankdeposit <Money>");
				}
			} else if (cmd.getName().equalsIgnoreCase("bankwithdraw")) { // remove from bankaccount
				if (args.length == 1) {
					int money = Integer.parseInt(args[0]);
					int currMoney = getBankMoney(p.getUniqueId());
					int usercurrMoney = getMoney(p.getUniqueId());
					if (money <= currMoney) {
						setBankMoney(p.getUniqueId(), (currMoney - money));
						setMoney(p.getUniqueId(), (usercurrMoney + money));
						p.sendMessage(api.prefix("main")
								+ api.returnStringReady(p, "cmd.bankwithdraw.successfull").replace("%money", args[0]));
						ActionLogger.log(api.getServerName(), p,
								"Player used bankwithdraw command, withdrawed " + money + " coins.");
					} else {
						api.sendMSGReady(p, "cmd.bankwithdraw.moreaspossible");
						ActionLogger.log(api.getServerName(), p,
								"Player attempted to use bankwithdraw command, new value would've been negative.");
					}
				} else {
					p.sendMessage(api.prefix("main") + api.returnStringReady(p, "usage") + " §7/bankwithdraw <Money>");
				}
			}
		}
		return false;
	}

	public static int getBankMoney(UUID uuid) {
		int i = 0;
		try {
			PreparedStatement ps = MySQL.getConnection()
					.prepareStatement("SELECT * FROM redicore_userstats WHERE uuid = ?");
			ps.setString(1, uuid.toString().replace("-", ""));
			ResultSet rs = ps.executeQuery();
			rs.next();
			i = rs.getInt("bankmoney");
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}

	public static int getMoney(UUID uuid) {
		int i = 0;
		try {
			PreparedStatement ps = MySQL.getConnection()
					.prepareStatement("SELECT * FROM redicore_userstats WHERE uuid = ?");
			ps.setString(1, uuid.toString().replace("-", ""));
			ResultSet rs = ps.executeQuery();
			rs.next();
			i = rs.getInt("money");
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}

	public static void addMoney(UUID uuid, int moneytoadd) {
		int currentMoney = getMoney(uuid);
		setMoney(uuid, (currentMoney + moneytoadd));
	}

	public static void setBankMoney(UUID uuid, int money) {
		try {
			PreparedStatement ps = MySQL.getConnection()
					.prepareStatement("UPDATE redicore_userstats SET bankmoney = ? WHERE uuid = ?");
			ps.setInt(1, money);
			ps.setString(2, uuid.toString().replace("-", ""));
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
		}
	}

	public static void setMoney(UUID uuid, int money) {
		try {
			PreparedStatement ps = MySQL.getConnection()
					.prepareStatement("UPDATE redicore_userstats SET money = ? WHERE uuid = ?");
			ps.setInt(1, money);
			ps.setString(2, uuid.toString().replace("-", ""));
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
		}
	}

	public static boolean hasAccount(UUID uuid) {
		boolean boo = false;
		HashMap<String, Object> hm = new HashMap<>();
		hm.put("uuid_ut", uuid);
		try {
			if (Main.mysql.isInDatabase("redicore_userstats", hm)) {
				boo = true;
			} else {
				boo = false;
			}
		} catch (SQLException e) {
			boo = false;
		}
		return boo;
	}

	public static boolean hasenoughmoney(int usermoney, int needmoney) {
		boolean boo = false;
		if (needmoney <= usermoney) {
			boo = true;
		} else {
			boo = false;
		}
		return boo;
	}

	// einkommen / 100 * 10
	public static int getTaxMoney(int money, int rate) {
		int divMoney = (money / 100);
		int rateMoney = (divMoney * rate);
		return rateMoney;
	}
}