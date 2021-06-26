package me.blakeismywaifu.dnd.Stats;

import me.blakeismywaifu.dnd.Util.Item;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Defences {

	public String type;
	public String source;
	public String defence;

	public Defences(JSONObject data) {
		this.type = (String) data.get("type");
		this.source = (String) data.get("source");
		this.defence = (String) data.get("defence");
	}

	public static ItemStack item(JSONObject json) {
		Map<String, ArrayList<Defences>> data = Defences.defencesMap(json);
		List<String> lore = new ArrayList<>();
		data.forEach((type, defences) -> {
			lore.add(colour(type) + type + ":");
			defences.forEach(defence -> lore.add(defence.defence));
			if (defences.size() == 0) lore.add(ChatColor.ITALIC + "None");
		});
		return Item.create(Item.main, "Defences:", lore, 9);
	}

	public static LinkedHashMap<String, ArrayList<Defences>> defencesMap(JSONObject json) {
		JSONArray list = (JSONArray) json.get("defences");

		LinkedHashMap<String, ArrayList<Defences>> defences = new LinkedHashMap<>();
		defences.put("Resistance", new ArrayList<>());
		defences.put("Immunity", new ArrayList<>());
		defences.put("Vulnerability", new ArrayList<>());

		for (Object object : list) {
			Defences data = new Defences((JSONObject) object);
			defences.computeIfAbsent(data.type, t -> new ArrayList<>()).add(data);
		}

		return defences;
	}

	public static ChatColor colour(String type) {
		return type.equals("Vulnerability") ? ChatColor.RED : ChatColor.GREEN;
	}
}
