package me.blakeismywaifu.dnd.Commands;

import me.blakeismywaifu.dnd.Main;
import me.blakeismywaifu.dnd.Util.API;
import me.blakeismywaifu.dnd.Util.PlayerCache;
import me.blakeismywaifu.dnd.Util.UpdatePlayer;
import me.blakeismywaifu.dnd.Util.Util;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;

public class bind implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

		if (args.length == 0) {
			sender.sendMessage(ChatColor.RED + "\nMust specify a dndbeyond character id and a player\nor run " + ChatColor.WHITE + "/bind view" + ChatColor.RED + " to view binds");
			return false;
		}

		if (args[0].equalsIgnoreCase("view")) {
			Map<UUID, String> binds = PlayerCache.getBinds();
			TextComponent main = new TextComponent(ChatColor.GREEN + "\n[" + binds.size() + "] Binded Players: ");
			binds.forEach((key, value) -> {
				TextComponent name = new TextComponent(Bukkit.getPlayer(key).getDisplayName() + ", ");
				name.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(value)));
				main.addExtra(name);
			});
			sender.sendMessage(main);
			return true;
		}

		Player player = null;
		if (sender instanceof Player) player = (Player) sender;
		if (args.length >= 2) player = Bukkit.getPlayer(args[1]);

		if (player == null) {
			sender.sendMessage(ChatColor.RED + "\nUnknown player");
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
			player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
			player.setHealth(20);

			sender.sendMessage(ChatColor.GREEN + "\n" + player.getDisplayName() + " has their binding remove");
			return true;
		}

		if (!Util.isInteger(args[0])) {
			sender.sendMessage(ChatColor.RED + "\nCharacter id must be an integer");
			return false;
		}

		PlayerCache.putBind(player.getUniqueId(), args[0]);

		API api = new API(args[0]);
		if (!api.status) {
			Main.log.info("API Error in updater: " + player.getDisplayName() + " " + args[0]);
			return false;
		}
		UpdatePlayer.update(api.json, player);

		sender.sendMessage(ChatColor.GREEN + "\nSuccessfully bound " + player.getDisplayName() + " to " + args[0]);

		return true;
	}
}
