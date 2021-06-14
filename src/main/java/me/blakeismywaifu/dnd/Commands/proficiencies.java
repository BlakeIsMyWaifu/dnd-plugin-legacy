package me.blakeismywaifu.dnd.Commands;

import me.blakeismywaifu.dnd.Stats.Proficiencies;
import me.blakeismywaifu.dnd.Util.PlayerCache;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class proficiencies implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		}
		if (args.length >= 1) {
			player = Bukkit.getPlayer(args[1]);
		}

		if (player == null) {
			sender.sendMessage(ChatColor.RED + "\nUnknown player");
			return false;
		}
		if (!PlayerCache.getCache().containsKey(player.getUniqueId())) {
			sender.sendMessage(ChatColor.RED + "\nPlayer not yet cached");
			return false;
		}

		TextComponent main = new TextComponent();
		for (String type : Proficiencies.types) {
			TextComponent sub = new TextComponent("\n" + ChatColor.GREEN + StringUtils.capitalize(type) + ":");
			List<Proficiencies> list = Proficiencies.list(PlayerCache.getCache().get(player.getUniqueId()), type);
			if (list.size() == 0) {
				TextComponent text = new TextComponent("\n" + ChatColor.ITALIC + "None");
				sub.addExtra(text);
			} else {
				list.forEach(proficiency -> {
					TextComponent text = new TextComponent("\n● " + proficiency.item);
//					text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(proficiency.source).create()));
					text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(proficiency.source)));
					sub.addExtra(text);
				});
			}
			main.addExtra(sub);
		}
		sender.sendMessage(main);

		return true;
	}
}
