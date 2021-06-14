package me.blakeismywaifu.dnd.Stats;

import me.blakeismywaifu.dnd.Util.Item;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Senses {

	public Long perception;
	public Long investigation;
	public Long insight;
	public JSONArray addition;

	public Senses(JSONObject json) {
		JSONObject data = (JSONObject) json.get("senses");

		this.perception = (Long) data.get("perception");
		this.investigation = (Long) data.get("investigation");
		this.insight = (Long) data.get("insight");
		this.addition = (JSONArray) data.get("addition");
	}

	public static ItemStack item(JSONObject json) {
		Senses data = new Senses(json);
		ArrayList<String> lore = new ArrayList<>();
		lore.add("Perception: " + ChatColor.WHITE + data.perception);
		lore.add("Investigation: " + ChatColor.WHITE + data.investigation);
		lore.add("Insight: " + ChatColor.WHITE + data.insight);
		if (data.addition.size() == 0) {
			lore.add("Addional Senses: " + ChatColor.ITALIC + "None");
		} else {
			lore.add("Addional Senses: ");
			for (Object additionObject : data.addition) {
				JSONObject addition = (JSONObject) additionObject;
				lore.add(ChatColor.WHITE + "● " + addition.get("sense") + " " + addition.get("value") + " ft.");
			}
		}
		return Item.create(Item.main, "Senses:", lore, null);
	}
}
