package me.blakeismywaifu.dnd.Tasks;

import me.blakeismywaifu.dnd.Main;
import me.blakeismywaifu.dnd.Util.API;
import me.blakeismywaifu.dnd.Util.PlayerCache;
import me.blakeismywaifu.dnd.Util.UpdatePlayer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.simple.JSONObject;

import java.util.Map;
import java.util.UUID;

public class FullUpdate extends BukkitRunnable {

	@Override
	public void run() {
		Map<UUID, String> players = PlayerCache.getBinds();
		for (Map.Entry<UUID, String> entry : players.entrySet()) {
			Player player = Bukkit.getPlayer(entry.getKey());
			if (player == null || !player.isOnline() || player.getGameMode() != GameMode.ADVENTURE || !player.hasPermission("dnd.player")) return;

			API api = new API(entry.getValue());
			if (!api.status) {
				Main.log.info("API Error in updater: " + entry.getKey() + " " + entry.getValue() + " " + player.getDisplayName());
				return;
			}

			JSONObject oldCache = PlayerCache.getCache().get(player.getUniqueId());
			if (!api.json.equals(oldCache)) UpdatePlayer.update(api.json, player);
		}
	}
}
