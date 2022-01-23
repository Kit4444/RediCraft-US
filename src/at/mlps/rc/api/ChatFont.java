package at.mlps.rc.api;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatFont {
	
	private final Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
	
	public String format(String message) {
		Matcher match = pattern.matcher(message);
		while(match.find()) {
			String color = message.substring(match.start(), match.end());
			message = message.replace(color, net.md_5.bungee.api.ChatColor.of(color) + "");
			match = pattern.matcher(message);
		}
		return net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', message);
	}
}