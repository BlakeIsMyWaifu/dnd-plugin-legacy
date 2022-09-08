package dev.blakeismywaifu.dnd;

import dev.blakeismywaifu.dnd.API.RuleData;
import dev.blakeismywaifu.dnd.Commands.bind;
import dev.blakeismywaifu.dnd.Utils.PlayerCache;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

public final class Main extends JavaPlugin {

	public static Logger log = Logger.getLogger("Minecraft");

	public static Map<UUID, PlayerCache> cache = new HashMap<>();
	public static RuleData ruleData = new RuleData();

	@Override
	public void onEnable() {
		log.info(ChatColor.GREEN + "Minecraft-dnd Version: " + this.getDescription().getVersion());

		Map<String, CommandExecutor> commands = new HashMap<>();
		commands.put("bind", new bind(this));
		commands.forEach((name, object) -> {
			PluginCommand pluginCommand = getCommand(name);
			if (pluginCommand != null) pluginCommand.setExecutor(object);
		});
	}

	@Override
	public void onDisable() {
		log.info(ChatColor.GREEN + "Minecraft-dnd Disabling");
	}
}
