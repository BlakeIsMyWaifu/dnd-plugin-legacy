package me.blakeismywaifu.dnd.Commands;

import me.blakeismywaifu.dnd.Util.MapCreater;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;
import org.jetbrains.annotations.NotNull;

public class map implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "\nMust be a player");
			return false;
		}

		if (args.length == 0) {
			sender.sendMessage(ChatColor.RED + "\nMust supply a valid image url");
			return false;
		}

		Player player = (Player) sender;

		MapView view = Bukkit.createMap(player.getWorld());
		view.getRenderers().clear();

		MapCreater renderer = new MapCreater();
		if (!renderer.load(args[0])) {
			sender.sendMessage(ChatColor.RED + "\nImage could not be loaded");
			return false;
		}

		view.addRenderer(renderer);

		ItemStack map = new ItemStack(Material.FILLED_MAP);
		MapMeta meta = (MapMeta) map.getItemMeta();
		meta.setMapView(view);
		map.setItemMeta(meta);

		player.getInventory().addItem(map);
		sender.sendMessage(ChatColor.GREEN + "Map created and added to your inventory!");

		return true;
	}
}
