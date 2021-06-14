package me.blakeismywaifu.dnd;

import me.blakeismywaifu.dnd.Commands.TabComplete.rollTab;
import me.blakeismywaifu.dnd.Commands.bind;
import me.blakeismywaifu.dnd.Commands.defence;
import me.blakeismywaifu.dnd.Commands.map;
import me.blakeismywaifu.dnd.Commands.npc;
import me.blakeismywaifu.dnd.Commands.proficiencies;
import me.blakeismywaifu.dnd.Commands.roll;
import me.blakeismywaifu.dnd.Commands.skin;
import me.blakeismywaifu.dnd.Commands.update;
import me.blakeismywaifu.dnd.Events.InventoryEvents;
import me.blakeismywaifu.dnd.Events.PlayerConnection;
import me.blakeismywaifu.dnd.Events.SuggestionBlocker;
import me.blakeismywaifu.dnd.Tasks.FullUpdate;
import me.blakeismywaifu.dnd.Tasks.QuickUpdate;
import me.blakeismywaifu.dnd.Util.PlayerCache;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.logging.Logger;

public final class Main extends JavaPlugin {

	public static Logger log = Logger.getLogger("Minecraft");
	public static Map<UUID, PlayerCache> cache = new HashMap<>();

	static {
		ConfigurationSerialization.registerClass(PlayerCache.class);
	}

	@Override
	public void onEnable() {
		log.info(ChatColor.GREEN + "MinecraftDnD Version: " + this.getDescription().getVersion());

//		if (getConfig().contains("cache")) try {
//			List<PlayerCache> list = getConfig().getList("cache").stream().map(s -> PlayerCache.deserialize((Map<String, Object>) s)).collect(Collectors.toList());
//			log.info(String.valueOf(list.size()));
//			log.info(list.toString());
//		} catch (Exception error) {
//			error.printStackTrace();
//		}

		PluginManager pluginManager = getServer().getPluginManager();
		pluginManager.registerEvents(new PlayerConnection(),this);
		pluginManager.registerEvents(new InventoryEvents(), this);
		pluginManager.registerEvents(new SuggestionBlocker(), this);

		Map<String, CommandExecutor> commands = new HashMap<>();
		commands.put("bind", new bind());
		commands.put("defence", new defence());
		commands.put("map", new map());
		commands.put("npc", new npc());
		commands.put("proficiencies", new proficiencies());
		commands.put("roll", new roll());
		commands.put("skin", new skin());
		commands.put("update", new update());
		commands.forEach((name, object) -> {
			PluginCommand pluginCommand = getCommand(name);
			if (pluginCommand != null) pluginCommand.setExecutor(object);
		});

		Map<String, TabCompleter> tab = new HashMap<>();
		tab.put("roll", new rollTab());
		tab.forEach((name, object) -> {
			PluginCommand pluginCommand = getCommand(name);
			if (pluginCommand != null) pluginCommand.setTabCompleter(object);
		});

		new FullUpdate().runTaskTimer(this, 0, 400);
		new QuickUpdate().runTaskTimer(this, 0, 30);
//		new TabAnimation().runTaskTimer(this, 0, 40);
	}

	@Override
	public void onDisable() {
		log.info(ChatColor.GREEN + "MinecraftDnD Disabling");

//		getConfig().set("cache", new ArrayList<>(cache.values()));
//		saveConfig();
	}
}
