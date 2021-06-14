package me.blakeismywaifu.dnd.Util;

import me.blakeismywaifu.dnd.Main;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static org.bukkit.Bukkit.getServer;

public class Item {

	public static final Material main = Material.PHANTOM_MEMBRANE;

	public static ItemStack create(Material material, String name, @Nullable List<String> lore, @Nullable Integer modeldata) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.RESET + name);
		if (lore != null) {
			IntStream.range(0, lore.size()).forEach(i -> lore.set(i, ChatColor.RESET + "" + ChatColor.GRAY + lore.get(i)));
			meta.setLore(lore);
		}
		meta.setCustomModelData(modeldata);
		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack meta(ItemStack item, @Nullable Map<Attribute, AttributeModifier> attributes, @Nullable Map<Enchantment, Integer> enchantments, boolean unbreakable) {
		ItemMeta meta = item.getItemMeta();
		if (attributes != null) {
			Iterator it = attributes.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				Attribute attribute = (Attribute) pair.getKey();
				AttributeModifier modifier = (AttributeModifier) pair.getValue();
				meta.addAttributeModifier(attribute, modifier);
				it.remove();
			}
		}
		if (enchantments != null) {
			Iterator it = enchantments.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				Enchantment enchantment = (Enchantment) pair.getKey();
				Integer level = (Integer) pair.getValue();
				meta.addEnchant(enchantment, level, true);
				it.remove();
			}
		}
		meta.setUnbreakable(unbreakable);
		return item;
	}

	public static ItemStack colour(ItemStack item, int colour) {
		LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
		meta.setColor(Color.fromRGB(colour));
		item.setItemMeta(meta);
		return item;
	}

	public static void recipe(String recipeName, ItemStack item, Map<Character, ItemStack> ingrediants, String ...pattern) {
		Plugin plugin = Main.getPlugin(Main.class);
		NamespacedKey namespacedKey = new NamespacedKey(plugin, recipeName);

		ShapedRecipe recipe = new ShapedRecipe(namespacedKey, item);
		recipe.shape(pattern);

		Iterator ingrediant = ingrediants.entrySet().iterator();
		while (ingrediant.hasNext()) {
			Map.Entry pair = (Map.Entry) ingrediant.next();
			recipe.setIngredient((char) pair.getKey(), (ItemStack) pair.getValue());
			ingrediant.remove();
		}

		getServer().addRecipe(recipe);
	}
}
