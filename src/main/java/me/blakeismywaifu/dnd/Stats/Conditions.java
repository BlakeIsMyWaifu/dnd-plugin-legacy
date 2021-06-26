package me.blakeismywaifu.dnd.Stats;

import me.blakeismywaifu.dnd.Util.Item;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Conditions {

	public static ItemStack item(JSONObject json) {
		JSONArray data = (JSONArray) json.get("conditions");
		List<String> lore = new ArrayList<>();
		for (Object condition : data) lore.add("● " + condition);
		if (lore.size() == 0) lore.add(ChatColor.ITALIC + "None");
		return Item.create(Item.main, "Conditions:", lore, 10);
	}
}
