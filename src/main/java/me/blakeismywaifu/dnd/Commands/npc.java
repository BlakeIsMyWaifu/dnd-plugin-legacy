package me.blakeismywaifu.dnd.Commands;

import me.blakeismywaifu.dnd.Util.NPC;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class npc implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] strings) {

		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "\nMust be a player");
			return false;
		}

		Player player = (Player) sender;
		NPC.createNPC(player);

		return true;
	}
}
