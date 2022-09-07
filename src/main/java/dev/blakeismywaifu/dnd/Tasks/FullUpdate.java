package dev.blakeismywaifu.dnd.Tasks;

import dev.blakeismywaifu.dnd.Main;
import dev.blakeismywaifu.dnd.Utils.API;
import dev.blakeismywaifu.dnd.Utils.PlayerCache;
import dev.blakeismywaifu.dnd.Utils.UpdatePlayer;
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

			API api = new API(player.getUniqueId(), entry.getValue());

			JSONObject oldCache = PlayerCache.getCache().get(player.getUniqueId());
			if (!api.json.equals(oldCache)) UpdatePlayer.updateItems(player);
		}
	}
}
