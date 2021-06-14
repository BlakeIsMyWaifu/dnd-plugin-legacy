package me.blakeismywaifu.dnd.Events;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class InventoryEvents implements Listener {

	@EventHandler
	public void dragEvent(InventoryDragEvent event) {
		Player player = (Player) event.getWhoClicked();
		if (player.getGameMode() == GameMode.ADVENTURE && this.hasBypassPermission(player)) {
			event.setCancelled(true);
			player.updateInventory();
		}
	}

	@EventHandler
	public void clickEvent(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		if (player.getGameMode() == GameMode.ADVENTURE && this.hasBypassPermission(player)) {
			event.setCancelled(true);
			player.updateInventory();
		}
	}

	@EventHandler
	public void dropEvent(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		if (player.getGameMode() == GameMode.ADVENTURE && this.hasBypassPermission(player)) {
			event.setCancelled(true);
			player.updateInventory();
		}
	}

	@EventHandler
	public void swapEvent(PlayerSwapHandItemsEvent event) {
		Player player = event.getPlayer();
		if (player.getGameMode() == GameMode.ADVENTURE && this.hasBypassPermission(player)) {
			event.setCancelled(true);
			player.updateInventory();
		}
	}

	private boolean hasBypassPermission(Player player) {
		return player.hasPermission("dnd.player");
	}
}
