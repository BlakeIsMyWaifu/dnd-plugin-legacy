package me.blakeismywaifu.dnd.Events;

import net.minecraft.server.v1_16_R3.EntityPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class RightClickNPC extends Event implements Cancellable {

	private final Player player;
	private final EntityPlayer npc;
	private boolean isCancelled;
	private static final HandlerList HANDLES = new HandlerList();

	public RightClickNPC(Player player, EntityPlayer npc) {
		this.player = player;
		this.npc = npc;
	}

	public Player getPlayer() {
		return player;
	}

	public EntityPlayer getNPC() {
		return npc;
	}

	@Override
	public @NotNull HandlerList getHandlers() {
		return HANDLES;
	}

	public static HandlerList getHandlerList() {
		return HANDLES;
	}

	@Override
	public boolean isCancelled() {
		return isCancelled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.isCancelled = cancel;
	}
}
