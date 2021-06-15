package me.blakeismywaifu.dnd.Stats;

import me.blakeismywaifu.dnd.Util.Item;
import org.apache.commons.lang.StringUtils;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Stat {

	public Long total;
	public Long modifier;
	public Long base;
	public Long racial;
	public Long ability;
	public Long misc;
	public Long stacking;
	public Long set;

	public Stat(JSONObject data) {
		this.total = (Long) data.get("total");
		this.modifier = (Long) data.get("modifier");
		this.base = (Long) data.get("base");
		this.racial = (Long) data.get("racial");
		this.ability = (Long) data.get("ability");
		this.misc = (Long) data.get("misc");
		this.stacking = (Long) data.get("stacking");
		this.set = (Long) data.get("set");
	}

	public static final List<String> statOrder = Arrays.asList("strength", "dexterity", "consitution", "intelligence", "wisdom", "charisma");
	public static final List<String> shortName = Arrays.asList("STR", "DEX", "CON", "INT", "WIS", "CHA");
	public static String statShort(String stat) {
		return shortName.get(statOrder.indexOf(stat));
	}
	public static String statLong(String stat) {
		return statOrder.get(shortName.indexOf(stat));
	}
	public static int statCounter(String stat, int start) {
		int index = statOrder.indexOf(stat);
		return index + start;
	}

	public static List<ItemStack> items(JSONObject json) {
		JSONObject stats = (JSONObject) json.get("stats");
		List<ItemStack> items = new ArrayList<>();
		Stat.statOrder.forEach(stat -> {
			Stat data = new Stat((JSONObject) stats.get(stat));
			ArrayList<String> lore = new ArrayList<>();
			lore.add("Total: " + data.total);
			lore.add(StringUtils.capitalize(stat));
			items.add(Item.create(Item.main, "Modifier: +" + data.modifier, lore, statCounter(stat, 1)));
		});
		return items;
	}
}
