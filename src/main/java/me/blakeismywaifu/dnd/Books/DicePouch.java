package me.blakeismywaifu.dnd.Books;

import me.blakeismywaifu.dnd.Util.Item;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;
import java.util.List;

public class DicePouch {

	public static ItemStack item() {
		ItemStack item = Item.create(Material.WRITTEN_BOOK, ChatColor.AQUA + "Dice Pouch", null, null);

		BookMeta meta = (BookMeta) item.getItemMeta();
		meta.setTitle("~");
		meta.setAuthor(ChatColor.RED + "D&D");

		List<String> pages = new ArrayList<>();
		pages.add("test");
		meta.setPages(pages);

		item.setItemMeta(meta);

		return item;
	}
}
