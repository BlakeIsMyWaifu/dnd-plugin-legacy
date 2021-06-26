package me.blakeismywaifu.dnd.Stats.SavingThrow;

import me.blakeismywaifu.dnd.Stats.Stat;
import me.blakeismywaifu.dnd.Util.Item;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SavingThrowStat {

	public Long modifier;
	public Boolean proficiency;

	public SavingThrowStat(JSONObject data) {
		this.modifier = (Long) data.get("modifier");
		this.proficiency = (Boolean) data.get("proficiency");
	}

	public static List<ItemStack> items(JSONObject json) {
		JSONObject savingThrows = (JSONObject) json.get("savingThrows");
		List<ItemStack> items = new ArrayList<>();
		for (String stat : Stat.statOrder) {
			SavingThrowStat data = new SavingThrowStat((JSONObject) savingThrows.get(stat));
			ArrayList<String> lore = new ArrayList<>();
			lore.add(StringUtils.capitalize(stat));
			items.add(Item.create(Item.main, (data.proficiency ? ChatColor.GOLD : "") + "Saving Throw: +" + data.modifier, lore, Stat.statCounter(stat, 12)));
		}
		return items;
	}
}
