package dev.blakeismywaifu.dnd.Utils;

import dev.blakeismywaifu.dnd.Stats.Character;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;

public class UpdatePlayer {

	public static void updateItems(Player player) {

		JSONObject json = PlayerCache.getCache().get(player.getUniqueId());

		player.getInventory().setItem(9, Character.item(json));
	}
}
