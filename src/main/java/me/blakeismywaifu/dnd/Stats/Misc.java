package me.blakeismywaifu.dnd.Stats;

import me.blakeismywaifu.dnd.Util.Item;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Misc {

	public Long proficiency;
	public Long speed;
	public Boolean inspiration;
	public Long initiative;
	public Long armour;

	public Misc(JSONObject json) {
		JSONObject data = (JSONObject) json.get("misc");

		this.proficiency = (Long) data.get("proficiency");
		this.speed = (Long) data.get("speed");
		this.inspiration = (Boolean) data.get("inspiration");
		this.initiative = (Long) data.get("initiative");
		this.armour = (Long) data.get("armour");
	}

	public static ItemStack item(JSONObject json) {
		Misc data = new Misc(json);
		ArrayList<String> lore = new ArrayList<>();
		lore.add(ChatColor.WHITE + "+" + data.proficiency + ChatColor.GRAY + " Proficiency Bonus");
		lore.add(ChatColor.WHITE + "+" + data.initiative + ChatColor.GRAY + " Initative");
		lore.add(ChatColor.WHITE + data.speed.toString() + ChatColor.GRAY + "ft. Walking Speed");
		lore.add((data.inspiration ? ChatColor.GREEN : ChatColor.RED) + "Inspiration");
		return Item.create(Item.main, "Miscellaneous:", lore, 1);
	}
}
