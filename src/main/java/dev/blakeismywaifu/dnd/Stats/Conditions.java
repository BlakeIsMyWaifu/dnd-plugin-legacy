package dev.blakeismywaifu.dnd.Stats;

import dev.blakeismywaifu.dnd.Main;
import dev.blakeismywaifu.dnd.Utils.Item;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Conditions {

	public static ItemStack item(JSONObject json) {
		JSONObject ruleData = Main.ruleData.getData();
		JSONArray conditionDataArray = (JSONArray) ruleData.get("conditions");

		JSONArray activeConditions = (JSONArray) json.get("conditions");
		List<String> lore = new ArrayList<>();
		for (Object activeConditionsObject : activeConditions) {
			JSONObject activeCondition = (JSONObject) activeConditionsObject;

			int id = ((Long) activeCondition.get("id")).intValue();
			JSONObject conditionData = (JSONObject) conditionDataArray.get(id - 1);
			JSONObject conditionDefinition = (JSONObject) conditionData.get("definition");

			String conditionName = (String) conditionDefinition.get("name");

			String line = "● " + conditionName;

			Long level = (Long) activeCondition.get("level");
			if (level == null) {
				lore.add(line);
			} else {
				lore.add(line + " (level " + level + ")");
			}
		}
		if (lore.size() == 0) lore.add(ChatColor.ITALIC + "None");
		return Item.create(Item.main, "Conditions:", lore, 10);
	}
}
