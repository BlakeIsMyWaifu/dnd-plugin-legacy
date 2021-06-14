package me.blakeismywaifu.dnd.Commands;

import me.blakeismywaifu.dnd.Util.API;
import me.blakeismywaifu.dnd.Util.UpdatePlayer;
import me.blakeismywaifu.dnd.Util.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class update implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

		if (args.length == 0) {
			sender.sendMessage(ChatColor.RED + "\nMust specify a dndbeyond character id");
			return false;
		}
		if (!Util.isInteger(args[0])) {
			sender.sendMessage(ChatColor.RED + "\nCharacter id must be an integer");
			return false;
		}

		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		}
		if (args.length >= 2) {
			player = Bukkit.getPlayer(args[1]);
		}
		if (player == null) {
			sender.sendMessage(ChatColor.RED + "\nUnknown player");
			return false;
		}

		API api = new API(args[0]);
		if (!api.status) {
			sender.sendMessage(ChatColor.RED + "\nAPI Error");
			return false;
		}

		UpdatePlayer.update(api.json, player);
		player.sendMessage(ChatColor.GREEN + "\nInventory successfully updated!");
		if (player != sender) {
			sender.sendMessage(ChatColor.GREEN + "\n" + sender.getName() + " has had their inventory successfully updated!");
		}

		return true;
	}
}
