package me.blakeismywaifu.dnd.Util;

import me.blakeismywaifu.dnd.Stats.Skill;
import me.blakeismywaifu.dnd.Stats.Stat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Dice {

	public String name;
	public String detail;
	public String type;
	public UUID uuid;
	public int out;
	public Map<String, Integer> breakdown;

	public Dice(String name, UUID uuid) {
		this.name = name;
		this.uuid = uuid;

		this.detail = details.get(name)[0];
		this.type = details.get(name)[1];
	}

	public int getOut() {
		return this.out;
	}

	private static final List<String> customDice = Arrays.asList("20", "12", "10", "100", "8", "6", "4", "initiative");

	private static final Map<String, String[]> details = new HashMap<>();
	static {
		customDice.forEach(custom -> details.put(custom, new String[]{"custom", "roll"}));
		details.put("initiative", new String[]{"initiative", "roll"});
		Stat.statOrder.forEach(stat -> {
			details.put(stat, new String[]{Stat.statShort(stat), "check"});
			details.put(stat + "save", new String[]{Stat.statShort(stat), "save"});
		});
		Skill.list.forEach(skill -> details.put(skill, new String[]{skill, "check"}));
	}

	public static List<String> diceNameList = new ArrayList<>(details.keySet());
}
