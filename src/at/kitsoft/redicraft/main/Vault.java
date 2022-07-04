package at.kitsoft.redicraft.main;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import at.kitsoft.redicraft.cmd.MoneyAPI;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

public class Vault implements Economy{

	@Override
	public EconomyResponse bankBalance(String arg0) {
		return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "RediCraft does not support bank accounts");
	}

	@Override
	public EconomyResponse bankDeposit(String arg0, double arg1) {
		return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "RediCraft does not support bank accounts");
	}

	@Override
	public EconomyResponse bankHas(String arg0, double arg1) {
		return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "RediCraft does not support bank accounts");
	}

	@Override
	public EconomyResponse bankWithdraw(String arg0, double arg1) {
		return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "RediCraft does not support bank accounts");
	}

	
	@SuppressWarnings("deprecation")
	@Override
	public EconomyResponse createBank(String arg0, String arg1) {
		return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "RediCraft does not support bank accounts");
	}

	@Override
	public EconomyResponse createBank(String arg0, OfflinePlayer arg1) {
		return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "RediCraft does not support bank accounts");
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean createPlayerAccount(String arg0) {
		return true;
	}

	@Override
	public boolean createPlayerAccount(OfflinePlayer arg0) {
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean createPlayerAccount(String arg0, String arg1) {
		return createPlayerAccount(arg0);
	}

	@Override
	public boolean createPlayerAccount(OfflinePlayer arg0, String arg1) {
		return createPlayerAccount(arg0);
	}

	@Override
	public String currencyNamePlural() {
		return "Coins";
	}

	@Override
	public String currencyNameSingular() {
		return "Coin";
	}

	@Override
	public EconomyResponse deleteBank(String arg0) {
		return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "RediCraft does not support bank accounts");
	}

	@SuppressWarnings("deprecation")
	@Override
	public EconomyResponse depositPlayer(String arg0, double arg1) {
		OfflinePlayer p = Bukkit.getOfflinePlayer(arg0);
		return depositPlayer(p, arg1);
	}

	@Override
	public EconomyResponse depositPlayer(OfflinePlayer arg0, double arg1) {
		int money = (int)arg1;
		int money1 = MoneyAPI.getMoney(arg0.getUniqueId());
		int imoney = (money + money1);
		MoneyAPI.setMoney(arg0.getUniqueId(), imoney);
		return new EconomyResponse(money, money1, EconomyResponse.ResponseType.SUCCESS, "");
	}

	@SuppressWarnings("deprecation")
	@Override
	public EconomyResponse depositPlayer(String arg0, String arg1, double arg2) {
		return null;
	}

	@Override
	public EconomyResponse depositPlayer(OfflinePlayer arg0, String arg1, double arg2) {
		return null;
	}

	@Override
	public String format(double arg0) {
		return null;
	}

	@Override
	public int fractionalDigits() {
		return 0;
	}

	@SuppressWarnings("deprecation")
	@Override
	public double getBalance(String arg0) {
		return getBalance(arg0);
	}

	@Override
	public double getBalance(OfflinePlayer arg0) {
		return MoneyAPI.getMoney(arg0.getUniqueId());
	}

	@SuppressWarnings("deprecation")
	@Override
	public double getBalance(String arg0, String arg1) {
		return getBalance(arg0);
	}

	@Override
	public double getBalance(OfflinePlayer arg0, String arg1) {
		return MoneyAPI.getMoney(arg0.getUniqueId());
	}

	@Override
	public List<String> getBanks() {
		return null;
	}

	@Override
	public String getName() {
		return "RediEconomy";
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean has(String arg0, double arg1) {
		return has(Bukkit.getOfflinePlayer(arg0), arg1);
	}

	@Override
	public boolean has(OfflinePlayer arg0, double arg1) {
		int money = (int)arg1;
		int money1 = MoneyAPI.getMoney(arg0.getUniqueId());
		return MoneyAPI.hasenoughmoney(money1, money);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean has(String arg0, String arg1, double arg2) {
		return has(arg0, arg2);
	}

	@Override
	public boolean has(OfflinePlayer arg0, String arg1, double arg2) {
		return has(arg0, arg2);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean hasAccount(String arg0) {
		return hasAccount(arg0);
	}

	@Override
	public boolean hasAccount(OfflinePlayer arg0) {
		return MoneyAPI.hasAccount(arg0.getUniqueId());
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean hasAccount(String arg0, String arg1) {
		return hasAccount(arg0, arg1);
	}

	@Override
	public boolean hasAccount(OfflinePlayer arg0, String arg1) {
		return hasAccount(arg0);
	}

	@Override
	public boolean hasBankSupport() {
		return false;
	}

	@SuppressWarnings("deprecation")
	@Override
	public EconomyResponse isBankMember(String arg0, String arg1) {
		return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "RediCraft does not support bank accounts");
	}

	@Override
	public EconomyResponse isBankMember(String arg0, OfflinePlayer arg1) {
		return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "RediCraft does not support bank accounts");
	}

	@SuppressWarnings("deprecation")
	@Override
	public EconomyResponse isBankOwner(String arg0, String arg1) {
		return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "RediCraft does not support bank accounts");
	}

	@Override
	public EconomyResponse isBankOwner(String arg0, OfflinePlayer arg1) {
		return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "RediCraft does not support bank accounts");
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public EconomyResponse withdrawPlayer(String arg0, double arg1) {
		return withdrawPlayer(arg0, arg1);
	}

	@Override
	public EconomyResponse withdrawPlayer(OfflinePlayer arg0, double arg1) {
		int money = (int) arg1;
		int money1 = MoneyAPI.getMoney(arg0.getUniqueId());
		int newmoney = (money1 - money);
		MoneyAPI.setMoney(arg0.getUniqueId(), newmoney);
		return new EconomyResponse(arg1, money1, EconomyResponse.ResponseType.SUCCESS, "");
	}

	@SuppressWarnings("deprecation")
	@Override
	public EconomyResponse withdrawPlayer(String arg0, String arg1, double arg2) {
		return withdrawPlayer(arg0, arg1, arg2);
	}

	@Override
	public EconomyResponse withdrawPlayer(OfflinePlayer arg0, String arg1, double arg2) {
		return withdrawPlayer(arg0, arg2);
	}
}