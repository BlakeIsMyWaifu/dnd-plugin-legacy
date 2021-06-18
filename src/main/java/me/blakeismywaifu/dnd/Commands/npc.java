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
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "\nMust be a player");
			return false;
		}

		if (args.length == 0) {
			sender.sendMessage(ChatColor.RED + "\nMust name the NPC, you monster :c");
			return false;
		}

		if (args.length == 1) {
			sender.sendMessage(ChatColor.RED + "\nMust give the NPC a skin, either from the datapack or a username");
			return false;
		}

		String colour = ChatColor.translateAlternateColorCodes('&', "ChatColor.AQUA");

		Player player = (Player) sender;
		NPC.createNPC(player, args[0], args[1]);

		return true;
	}
}
