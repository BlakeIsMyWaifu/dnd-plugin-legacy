package me.blakeismywaifu.dnd.Stats;

import me.blakeismywaifu.dnd.Util.Item;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static me.blakeismywaifu.dnd.Stats.Stat.statShort;

public class Skill {

	public String proficiency;
	public String stat;
	public String skill;
	public String vantage;
	public Long modifier;

	public Skill(JSONObject data) {
		this.proficiency = (String) data.get("proficiency");
		this.stat = (String) data.get("stat");
		this.skill = (String) data.get("skill");
		this.vantage = (String) data.get("vantage");
		this.modifier = (Long) data.get("modifier");
	}

	public static ItemStack item(JSONObject json) {
		JSONArray list = (JSONArray) json.get("skills");
		ArrayList<String> lore = new ArrayList<>();
		for (Object object : list) {
			Skill skill = new Skill((JSONObject) object);
			ChatColor colour = skill.proficiency.equals("Not") ? ChatColor.GRAY : (skill.proficiency.equals("Half") ? ChatColor.YELLOW : ChatColor.GOLD);
			String adjustment = skill.vantage == null ? " " : (skill.vantage.equals("Advantage") ? ChatColor.GREEN + " A " : ChatColor.RED + " D ");
			lore.add(colour + "● " + skill.skill + " +" + skill.modifier + adjustment + ChatColor.DARK_GRAY + statShort(skill.stat));
		}
		return Item.create(Item.main, "Skills: ", lore, 2);
	}

	public static List<String> list = Arrays.asList(
		"acrobatics", "animalhandling", "arcana", "athleitcs", "deception",
		"history", "insight", "intimidation", "investigation", "medicine", "nature", "perception",
		"performance", "persuasion", "religion", "slightofhand", "stealth", "survival"
	);
}
