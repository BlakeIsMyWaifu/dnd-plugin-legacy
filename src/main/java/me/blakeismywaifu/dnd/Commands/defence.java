package me.blakeismywaifu.dnd.Commands;

import me.blakeismywaifu.dnd.Stats.Defences;
import me.blakeismywaifu.dnd.Util.PlayerCache;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class defence implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

		Player player = null;
		if (sender instanceof Player) player = (Player) sender;
		if (args.length >= 1) player = Bukkit.getPlayer(args[1]);

		if (player == null) {
			sender.sendMessage(ChatColor.RED + "\nUnknown player");
			return false;
		}
		if (!PlayerCache.getCache().containsKey(player.getUniqueId())) {
			sender.sendMessage(ChatColor.RED + "\nPlayer not yet cached");
			return false;
		}

		LinkedHashMap<String, ArrayList<Defences>> defences = Defences.defencesMap(PlayerCache.getCache().get(player.getUniqueId()));

		ComponentBuilder message = new ComponentBuilder();
		defences.forEach((defenceType, defenceArray) -> {
			message.append("\n" + Defences.colour(defenceType) + defenceType + ": ").reset();
			if (defenceArray.size() == 0) message.append(ChatColor.ITALIC + "None");
			defenceArray.forEach(defence -> {
				ComponentBuilder defenceLine = new ComponentBuilder("\n● " + defence.defence);
				defenceLine.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(defence.source)));
				message.append(defenceLine.create());
			});
		});

		sender.sendMessage(message.create());

		return true;
	}
}
