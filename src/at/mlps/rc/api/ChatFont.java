package at.mlps.rc.api;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.md_5.bungee.api.ChatColor;

public class ChatFont {
	
	private static final Pattern HEX_PATTERN = Pattern.compile("#[0-9a-fA-F]{6}");

	public static String translate(String text){
		Matcher matcher = HEX_PATTERN.matcher(text);
		while(matcher.find()){ text = text.replace(matcher.group(), ChatColor.of(matcher.group()).toString()); }
		return text;
	}
}