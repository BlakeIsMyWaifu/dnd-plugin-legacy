package me.blakeismywaifu.dnd.Events;

import me.blakeismywaifu.dnd.Main;
import me.blakeismywaifu.dnd.Util.NPC;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class TeleportEvent implements Listener {

	@EventHandler
	public void teleportEvent(final PlayerTeleportEvent event) {
		World fromWorld = event.getFrom().getWorld();
		World toWorld = event.getTo().getWorld();
		if (!fromWorld.equals(toWorld)) NPC.NPC.forEach(npc -> NPC.addNPCPacket(npc, event.getPlayer()));
	}
}
