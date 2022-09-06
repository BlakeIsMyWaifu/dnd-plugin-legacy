package dev.blakeismywaifu.dnd.Commands;

import dev.blakeismywaifu.dnd.Utils.API;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class bind implements CommandExecutor {

	Plugin plugin;

	public bind(Plugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

		API api = new API("81779028");

		plugin.getLogger().info(api.json.toJSONString());

		return false;
	}
}
