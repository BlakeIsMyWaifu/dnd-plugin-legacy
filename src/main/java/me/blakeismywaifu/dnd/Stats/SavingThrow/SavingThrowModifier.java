package me.blakeismywaifu.dnd.Stats.SavingThrow;

import me.blakeismywaifu.dnd.Util.Item;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class SavingThrowModifier {

	public String type;
	public String subtype;
	public String modifier;
	public String source;

	public SavingThrowModifier(JSONObject data) {
		this.type = (String) data.get("type");
		this.subtype = (String) data.get("subtype");
		this.modifier = (String) data.get("modifier");
		this.source = (String) data.get("source");
	}

	public static ItemStack item(JSONObject json) {
		JSONObject savingThrows = (JSONObject) json.get("savingThrows");
		JSONArray modifiers = (JSONArray) savingThrows.get("modifier");
		ArrayList<String> lore = new ArrayList<>();
		for (Object modifier : modifiers) {
			SavingThrowModifier data = new SavingThrowModifier((JSONObject) modifier);
			lore.add((data.type.equals("Disadvantage") ? ChatColor.RED : ChatColor.GREEN) + "● " + data.modifier);
		}
		if (lore.size() == 0) lore.add(ChatColor.ITALIC + "None");
		return Item.create(Item.main, "Saving Throw Modifiers:", lore, 11);
	}
}
