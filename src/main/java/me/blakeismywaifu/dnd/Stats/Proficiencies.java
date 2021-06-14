package me.blakeismywaifu.dnd.Stats;

import me.blakeismywaifu.dnd.Util.Item;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Proficiencies {

	public String source;
	public String item;

	public Proficiencies(JSONObject json) {
		this.source = (String) json.get("source");
		this.item = (String) json.get("item");
	}

	public static List<Proficiencies> list(JSONObject json, String type) {
		JSONObject proficiencies = (JSONObject) json.get("proficiencies");
		JSONArray list = (JSONArray) proficiencies.get(type);
		List<Proficiencies> out = new ArrayList<>();
		for (Object object : list) out.add(new Proficiencies((JSONObject) object));
		return out;
	}

	public static ItemStack item(JSONObject json, String type) {
		List<Proficiencies> data = list(json, type);
		List<String> lore = new ArrayList<>();
		data.forEach(a -> lore.add("● " + a.item));
		if (lore.size() == 0) lore.add(ChatColor.ITALIC + "None");
		return Item.create(Item.main, StringUtils.capitalize(type) + ":", lore, null);
	}

	public static String[] types = new String[]{"armour", "weapons", "tools", "languages"};
}
