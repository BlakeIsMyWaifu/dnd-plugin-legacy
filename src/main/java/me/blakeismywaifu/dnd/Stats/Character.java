package me.blakeismywaifu.dnd.Stats;

import me.blakeismywaifu.dnd.Util.Item;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Character {

	public String name;
	public String gender;
	public String race;
	public String classes;
	public Long level;

	public Character(JSONObject json) {
		JSONObject data = (JSONObject) json.get("character");

		this.name = (String) data.get("name");
		this.gender = (String) data.get("gender");
		this.race = (String) data.get("race");
		this.classes = (String) data.get("classes");
		this.level = (Long) data.get("level");
	}

	public static ItemStack item(JSONObject json) {
		Character data = new Character(json);
		List<String> info = new ArrayList<>();
		if (data.gender != null) info.add(data.gender);
		info.add(data.race);
		info.add(data.classes);
		ArrayList<String> lore = new ArrayList<>();
		lore.add(String.join(", ", info));
		lore.add("Level " + data.level);
		return Item.create(Item.main, data.name, lore, null);
	}
}
