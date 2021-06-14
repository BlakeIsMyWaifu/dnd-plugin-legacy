package me.blakeismywaifu.dnd.Commands.TabComplete;

import me.blakeismywaifu.dnd.Util.Dice;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class rollTab implements TabCompleter {

	@Override
	public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

		List<String> result = new ArrayList<>();
		if (args.length == 1) {
			for (String option : Dice.diceNameList) {
				if (option.toLowerCase().startsWith(args[0].toLowerCase())) result.add(option);
			}
			return result;
		}

		return Dice.diceNameList;
	}
}
