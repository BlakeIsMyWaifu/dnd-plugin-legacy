package me.blakeismywaifu.dnd.Commands;

import com.mojang.authlib.properties.Property;
import me.blakeismywaifu.dnd.Util.PlayerCache;
import me.blakeismywaifu.dnd.Util.PlayerSkin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Map;

public class skin implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

		if (args.length == 0) {
			sender.sendMessage(ChatColor.RED + "\nMust specify a valid skin name");
			return false;
		}

		Map<String, Property> skins = PlayerSkin.skins;

		if (args[0].equals("list")) {
			String[] skinNames = skins.keySet().toArray(new String[0]);
			Arrays.sort(skinNames);
			sender.sendMessage(ChatColor.GREEN + "\nSkins: " + String.join(", ", skinNames));
			return true;
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

		Property skin;

		if (args[0].equals("restore")) {
			Property defaultSkin = PlayerCache.getDefaultSkin().get(player.getUniqueId());
			if (defaultSkin == null) {
				sender.sendMessage(ChatColor.RED + "\nThat player is still using their default skin");
				return false;
			}
			skin = defaultSkin;
		} else {
			skin = PlayerSkin.getSkin(args[0]);
		}

		if (skin == null) {
			sender.sendMessage(ChatColor.RED + "\nThat is not a valid skin name");
			return false;
		}

		PlayerCache.putSkin(player.getUniqueId(), skin);
		PlayerSkin.changeSkin(player, skin);
		sender.sendMessage(ChatColor.GREEN + "\nSuccessfully changed " + player.getDisplayName() + "'s skin to " + args[0]);

		return true;
	}
}
