package me.blakeismywaifu.dnd.Tasks;

import me.blakeismywaifu.dnd.Stats.Health;
import me.blakeismywaifu.dnd.Util.PlayerCache;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.simple.JSONObject;

import java.util.Map;
import java.util.UUID;

public class QuickUpdate extends BukkitRunnable {

	@Override
	public void run() {
		Map<UUID, String> players = PlayerCache.getBinds();
		for (Map.Entry<UUID, String> entry : players.entrySet()) {
			Player player = Bukkit.getPlayer(entry.getKey());
			if (player == null || !player.isOnline() || player.getGameMode() != GameMode.ADVENTURE || !player.hasPermission("dnd.player")) return;

			player.setSaturation(20f);

			JSONObject json = PlayerCache.getCache().get(entry.getKey());
			if (json == null) return;

			Health health = new Health(json);

			if (health.current == 0) {
				player.setGameMode(GameMode.SPECTATOR);
				return;
			}

			player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health.max);
			player.setHealth(health.current);
			player.setAbsorptionAmount(health.temp);

			String tempHealth = ChatColor.GRAY + "(" + health.temp + ") " + ChatColor.RED;
			player.sendActionBar(ChatColor.RED + "Health: [ " + (health.temp > 0 ? tempHealth : "") + health.current + " / " + health.max + " ]");
		}
	}
}
