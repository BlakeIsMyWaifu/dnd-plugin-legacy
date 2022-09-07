package dev.blakeismywaifu.dnd.Utils;

import dev.blakeismywaifu.dnd.Main;
import net.kyori.adventure.text.Component;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.bukkit.Bukkit.getServer;

public class Item {

	public static final Material main = Material.PHANTOM_MEMBRANE;

	public static ItemStack create(Material material, String name, @Nullable List<String> lore, @Nullable Integer modelData) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		Component displayName = Component.text(ChatColor.RESET + name);
		meta.displayName(displayName);
		if (lore != null) {
			List<Component> styledLore = new ArrayList<>();
			lore.forEach(i -> styledLore.add(Component.text(ChatColor.RED + "" + ChatColor.GRAY + i)));
			meta.lore(styledLore);
		}
		meta.setCustomModelData(modelData);
		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack meta(ItemStack item, @Nullable Map<Attribute, AttributeModifier> attributes, @Nullable Map<Enchantment, Integer> enchantments, boolean unbreakable) {
		ItemMeta meta = item.getItemMeta();
		if (attributes != null) {
			Iterator<Map.Entry<Attribute, AttributeModifier>> it = attributes.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<Attribute, AttributeModifier> pair = it.next();
				Attribute attribute = pair.getKey();
				AttributeModifier modifier = pair.getValue();
				meta.addAttributeModifier(attribute, modifier);
				it.remove();
			}
		}
		if (enchantments != null) {
			Iterator<Map.Entry<Enchantment, Integer>> it = enchantments.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<Enchantment, Integer> pair = it.next();
				Enchantment enchantment = pair.getKey();
				Integer level = pair.getValue();
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

	public static void recipe(String recipeName, ItemStack item, Map<Character, ItemStack> ingredients, String ...pattern) {
		Plugin plugin = Main.getPlugin(Main.class);
		NamespacedKey namespacedKey = new NamespacedKey(plugin, recipeName);

		ShapedRecipe recipe = new ShapedRecipe(namespacedKey, item);
		recipe.shape(pattern);

		Iterator<Map.Entry<Character, ItemStack>> ingredient = ingredients.entrySet().iterator();
		while (ingredient.hasNext()) {
			Map.Entry<Character, ItemStack> pair = ingredient.next();
			recipe.setIngredient(pair.getKey(), pair.getValue());
			ingredient.remove();
		}

		getServer().addRecipe(recipe);
	}
}
