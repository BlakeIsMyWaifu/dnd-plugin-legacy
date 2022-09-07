package dev.blakeismywaifu.dnd.Commands;

import dev.blakeismywaifu.dnd.Tasks.FullUpdate;
import dev.blakeismywaifu.dnd.Utils.PlayerCache;
import dev.blakeismywaifu.dnd.Utils.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentBuilder;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class bind implements CommandExecutor {

	Plugin plugin;

	public bind(Plugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

		if (args.length == 0) {
			sender.sendMessage(ChatColor.RED + "\nMust specify a dndbeyond character id and a player\nor run " + ChatColor.WHITE + "/bind view" + ChatColor.RED + " to view binds");
			return false;
		}

		if (args[0].equalsIgnoreCase("view")) {
			Map<UUID, String> binds = PlayerCache.getBinds();

			ComponentBuilder<TextComponent, TextComponent.Builder> viewMessage = Component.text("\n[" + binds.size() + "] Bound Players: ", NamedTextColor.GREEN).toBuilder();
			binds.forEach((key, value) -> {
				HoverEvent<Component> hoverEvent = HoverEvent.showText(Component.text(value));
				Component name = Component.text(Objects.requireNonNull(Bukkit.getPlayer(key)).getName() + ", ").hoverEvent(hoverEvent);
				viewMessage.append(name);
			});
			sender.sendMessage(viewMessage);

			return true;
		}

		Player player = null;
		if (sender instanceof Player) player = (Player) sender;
		if (args.length >= 2) player = Bukkit.getPlayer(args[1]);

		if (player == null) {
			sender.sendMessage(ChatColor.RED + "\nUnknown Player");
			return false;
		}

		if (args[0].equalsIgnoreCase("delete")) {
			if (!PlayerCache.getBinds().containsKey(player.getUniqueId())) {
				sender.sendMessage(ChatColor.RED + "\nThat player doesn't have a binding");
				return false;
			}

			PlayerCache.putBind(player.getUniqueId(), null);
			PlayerCache.putCache(player.getUniqueId(), null);

			player.getInventory().clear();
			Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(20);
			player.setHealth(20);

			sender.sendMessage(ChatColor.GREEN + "\n" + player.getName() + " has their binding remove");
			return true;
		}

		if (!Utils.isInteger(args[0])) {
			sender.sendMessage(ChatColor.RED + "\nCharacter id must be an integer");
			return false;
		}

		PlayerCache.putBind(player.getUniqueId(), args[0]);

		new FullUpdate().runTaskAsynchronously(plugin);

		sender.sendMessage(ChatColor.GREEN + "\nSuccessfully bound: " + player.getName() + " to " + args[0]);

		return true;
	}
}
