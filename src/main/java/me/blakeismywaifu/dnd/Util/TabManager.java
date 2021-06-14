package me.blakeismywaifu.dnd.Util;

import net.minecraft.server.v1_16_R3.ChatComponentText;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class TabManager {

	public static List<ChatComponentText> headers = new ArrayList<>();
	public static List<ChatComponentText> footers = new ArrayList<>();
	static {
		addHeader("&aTest");
	}

	private static void addHeader(String header) {
		headers.add(new ChatComponentText(format(header)));
	}

	private static void addFooter(String footer) {
		footers.add(new ChatComponentText(format(footer)));
	}

	private static String format(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}
}
