package me.blakeismywaifu.dnd.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;

import java.util.ArrayList;
import java.util.List;

public class SuggestionBlocker implements Listener {

	@EventHandler
	public void onCommandSuggestionSend(PlayerCommandSendEvent event) {

		Player player = event.getPlayer();
		if (!player.hasPermission("dnd.player") || player.isOp()) return;

		List<String> commands = new ArrayList<>();
		commands.add("defence");
		commands.add("proficiencies");
		commands.add("armour");
		commands.add("weapons");
		commands.add("tools");
		commands.add("languages");
		commands.add("plugins");
		commands.add("roll");

		event.getCommands().clear();
		event.getCommands().addAll(commands);
	}
}
