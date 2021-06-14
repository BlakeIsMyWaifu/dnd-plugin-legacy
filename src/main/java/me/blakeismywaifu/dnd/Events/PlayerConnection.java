package me.blakeismywaifu.dnd.Events;

import me.blakeismywaifu.dnd.Util.NPC;
import me.blakeismywaifu.dnd.Util.PlayerCache;
import me.blakeismywaifu.dnd.Util.PlayerSkin;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerConnection implements Listener {

	@EventHandler
	public void onJoin(final PlayerJoinEvent event) {
		Player player = event.getPlayer();
		event.setJoinMessage(ChatColor.GRAY + "[" + ChatColor.GREEN + "+" + ChatColor.GRAY + "] " +  ChatColor.GOLD + player.getDisplayName() + " has joined the party");

		player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
		player.setHealth(20);
		player.setAbsorptionAmount(0);

		NPC.NPC.forEach(npc -> NPC.addNPCPacket(npc, player));

		if (PlayerCache.getSkin().containsKey(player.getUniqueId())) PlayerSkin.changeSkin(player, PlayerCache.getSkin().get(player.getUniqueId()));
	}

	@EventHandler
	void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		event.setQuitMessage(ChatColor.GRAY + "[" + ChatColor.RED + "-" + ChatColor.GRAY + "] " +  ChatColor.GOLD + player.getDisplayName() + " has left the party");
	}
}
