package dev.blakeismywaifu.dnd.Commands;

import dev.blakeismywaifu.dnd.Utils.API;
import dev.blakeismywaifu.dnd.Utils.PlayerCache;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class bind implements CommandExecutor {

	Plugin plugin;

	public bind(Plugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

		Player player = null;
		if (sender instanceof Player) player = (Player) sender;

		if (player == null) {
			sender.sendMessage(ChatColor.RED + "\nUnknown Player");
			return false;
		}

		// TODO remove hard coded id
		String id = "81779028";

		PlayerCache.putBind(player.getUniqueId(), id);

		API api = new API(player.getUniqueId(), id);

		sender.sendMessage(ChatColor.GREEN + "\nSuccessfully bound: " + player.getName() + " to " + id);

		return true;
	}
}
