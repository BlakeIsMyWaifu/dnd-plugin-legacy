package me.blakeismywaifu.dnd.Books;

import me.blakeismywaifu.dnd.Util.Item;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.Arrays;

public class DicePouch {

	public static ItemStack item() {
		ItemStack item = Item.create(Material.WRITTEN_BOOK, ChatColor.DARK_PURPLE + "Dice Pouch", null, null);

		BookMeta meta = (BookMeta) item.getItemMeta();
		meta.setTitle("~");
		meta.setAuthor(ChatColor.RED + "D&D");
		meta.setGeneration(null);

		meta.spigot().addPage(createPage("Custom", new String[]{"20", "12", "10", "100", "8", "6", "4"}));
		meta.spigot().addPage(createPage("Stat Check", new String[]{"strength", "dexterity", "consitution", "intelligence", "wisdom", "charisma"}));
		meta.spigot().addPage(createPage("Saving Throw", new String[]{"strengthsave", "dexteritysave", "consitutionsave", "intelligencesave", "wisdomsave", "charismasave"}));
		String[] skills = new String[]{"acrobatics", "animalhandling", "arcana", "athleitcs", "deception",
				"history", "insight", "intimidation", "investigation", "medicine", "nature", "perception",
				"performance", "persuasion", "religion", "slightofhand", "stealth", "survival"};
		meta.spigot().addPage(createPage("Skills (1)", Arrays.copyOfRange(skills, 0, skills.length / 2)));
		meta.spigot().addPage(createPage("Skills (2)", Arrays.copyOfRange(skills, skills.length / 2, skills.length)));
		meta.spigot().addPage(createPage("Initiative", new String[]{"initiative"}));

		item.setItemMeta(meta);

		return item;
	}

	private static BaseComponent[] createPage(String title, String[] values) {
		ComponentBuilder page = new ComponentBuilder("" + ChatColor.DARK_BLUE + ChatColor.BOLD + title + "\n\n");
		for (String value : values) {
			ComponentBuilder dice = new ComponentBuilder(ChatColor.BLUE + "● " + value + "\n");
			dice.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/roll " + value));
			dice.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(value)));
			page.append(dice.create());
		}
		return page.create();
	}
}
