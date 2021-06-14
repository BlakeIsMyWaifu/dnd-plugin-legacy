package me.blakeismywaifu.dnd.Commands;

import me.blakeismywaifu.dnd.Main;
import me.blakeismywaifu.dnd.Stats.Misc;
import me.blakeismywaifu.dnd.Stats.SavingThrow.SavingThrowStat;
import me.blakeismywaifu.dnd.Stats.Skill;
import me.blakeismywaifu.dnd.Stats.Stat;
import me.blakeismywaifu.dnd.Util.PlayerCache;
import me.blakeismywaifu.dnd.Util.Dice;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;
import java.util.stream.Collectors;

public class roll implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "\nMust be a player to roll");
			return false;
		}

		Player player = (Player) sender;
		if (!PlayerCache.getCache().containsKey(player.getUniqueId())) {
			sender.sendMessage(ChatColor.RED + "\nNo cached data");
			return false;
		}

		if (args.length == 0) {
			sender.sendMessage(ChatColor.RED + "\nNo dice specified");
			return false;
		}

		if (!Dice.diceNameList.contains(args[0].toLowerCase())) {
			sender.sendMessage(ChatColor.RED + "\nUnknown dice");
			return false;
		}

		List<Dice> dice = rollDice(args[0], player.getUniqueId());
		Dice result = dice.get(0);

		Main.log.info(String.valueOf(dice.size()));

		TextComponent main = new TextComponent("\n" + player.getDisplayName() + ChatColor.BLUE + " rolled " + StringUtils.capitalize(result.detail) + ": " + StringUtils.capitalize(result.type) + " = " + ChatColor.AQUA + result.out);
		String resultBreakdown = ChatColor.GREEN + breakdown(result.breakdown);
		main.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(dice.size() == 1 ? resultBreakdown : resultBreakdown + "\n" + ChatColor.RED + breakdown(dice.get(1).breakdown))));

		Bukkit.broadcast(main);

		return true;
	}

	private String breakdown(Map<String, Integer> breakdown) {
		return "Size: " + breakdown.get("size") +
				"\nModifier: " + breakdown.get("modifier") +
				"\nRollled: " + breakdown.get("roll") +
				"\n" + breakdown.get("roll") + " + " + breakdown.get("modifier") + " = " + (breakdown.get("roll") + breakdown.get("modifier"));
	}

	private List<Dice> rollDice(String name, UUID uuid) {
		Dice dice = new Dice(name, uuid);
		JSONObject data = PlayerCache.getCache().get(uuid);

		int size = 20;
		int modifier = 0;

		if (dice.detail.equals("custom")) size = Integer.parseInt(name);

		else if (dice.detail.equals("initiative")) modifier = Math.toIntExact(new Misc(data).initiative);

		else if (Stat.statOrder.contains(dice.detail)) modifier = Math.toIntExact(new Stat(data).modifier);

		else if (Stat.statOrder.stream().map(s -> s + "save").collect(Collectors.toList()).contains(dice.detail)) {
			JSONObject list = (JSONObject) data.get("savingThrow");
			modifier = Math.toIntExact(new SavingThrowStat((JSONObject) list.get(dice.detail)).modifier);
		}

		else if (Skill.list.contains(dice.detail)) {
			JSONArray list = (JSONArray) data.get("skills");
			Skill skill = new Skill((JSONObject) list.get(Skill.list.indexOf(dice.detail)));
			modifier = Math.toIntExact(skill.modifier);
			if (skill.vantage != null) {
				List<Dice> rolls = Arrays.asList(rollDice(dice, size, modifier), rollDice(new Dice(name, uuid), size, modifier));
				rolls.sort(Comparator.comparing(Dice::getOut));
				return rolls;
			}
		}

		return Collections.singletonList(rollDice(dice, size, modifier));
	}

	private Dice rollDice(Dice dice, int size, int modifier) {
		int roll;
		roll = (int) (Math.random() * size);
		dice.out = roll + modifier;
		dice.breakdown = new HashMap<>();
		dice.breakdown.put("size", size);
		dice.breakdown.put("modifier", modifier);
		dice.breakdown.put("roll", roll);

		Main.log.info("size " + size);
		Main.log.info("modifier " + modifier);

		return dice;
	}
}
