package me.blakeismywaifu.dnd.Util;

import me.blakeismywaifu.dnd.Books.DicePouch;
import me.blakeismywaifu.dnd.Stats.*;
import me.blakeismywaifu.dnd.Stats.Character;
import me.blakeismywaifu.dnd.Stats.SavingThrow.SavingThrowModifier;
import me.blakeismywaifu.dnd.Stats.SavingThrow.SavingThrowStat;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class UpdatePlayer {

	private static ItemStack blankSlot(int slot) {
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.DARK_GRAY + "Slot: " + slot);
		return Item.create(Item.main, "", lore, 23);
	}

	private static ItemStack placeholderBook(String name) {
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.GREEN + "" + ChatColor.ITALIC + "Click me to swap books!");
		return Item.create(Item.main, name, lore, null);
	}

	public static void update(JSONObject json, Player player) {

		PlayerCache.putCache(player.getUniqueId(), json);

		IntStream.range(0, 7).forEach(i -> player.getInventory().setItem(i, blankSlot(i + 1)));

		player.getInventory().setItem(7, DicePouch.item());

		if (player.getInventory().getItem(8) == null) {
			List<String> lore = new ArrayList<>();
			lore.add(ChatColor.DARK_GRAY + "Book Slot");
			ItemStack blankBook = Item.create(Item.main, "", lore, null);
			player.getInventory().setItem(8, blankBook);
		}

		player.getInventory().setItem(9, Character.item(json));
		player.getInventory().setItem(10, Misc.item(json));
		player.getInventory().setItem(11, Skill.item(json));
		List<ItemStack> statItems = Stat.items(json);
		IntStream.range(0, statItems.size()).forEach(i -> player.getInventory().setItem(i + 12, statItems.get(i)));

		player.getInventory().setItem(18, Defences.item(json));
		player.getInventory().setItem(19, Conditions.item(json));
		player.getInventory().setItem(20, SavingThrowModifier.item(json));
		List<ItemStack> savingThrowStatItems = SavingThrowStat.items(json);
		IntStream.range(0, savingThrowStatItems.size()).forEach(i -> player.getInventory().setItem(i + 21, savingThrowStatItems.get(i)));

		player.getInventory().setItem(27, Senses.item(json));
		IntStream.range(28, 32).forEach(i -> player.getInventory().setItem(i, Proficiencies.item(json, Proficiencies.types[i - 28])));

		String[] bookNames = new String[]{"Actions", "Spells", "Equipment", "Feats"};
		IntStream.range(0, 4).forEach(i -> player.getInventory().setItem(i + 32, placeholderBook(bookNames[i])));
	}
}
