package me.blakeismywaifu.dnd.Tasks;

import me.blakeismywaifu.dnd.Util.TabManager;
import net.minecraft.server.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_16_R3.Packet;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;

import static me.blakeismywaifu.dnd.Util.TabManager.headers;
import static me.blakeismywaifu.dnd.Util.TabManager.footers;

public class TabAnimation extends BukkitRunnable {

	PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
	int countHeader = 0;
	int countFooter = 0;

	@Override
	public void run() {
		if (TabManager.headers.isEmpty() && footers.isEmpty()) return;
		if (Bukkit.getOnlinePlayers().size() == 0) return;

		try {
			Field header = packet.getClass().getDeclaredField("header");
			header.setAccessible(true);

			Field footer = packet.getClass().getDeclaredField("footer");
			footer.setAccessible(true);

			if (countHeader >= headers.size()) countHeader = 0;
			if (countFooter >= footers.size()) countFooter = 0;

			header.set(packet, headers.get(countHeader));
			footer.set(packet, footers.get(countFooter));

			for (Player player : Bukkit.getOnlinePlayers()) {
				((CraftPlayer) player).getHandle().playerConnection.sendPacket((Packet<?>) packet);
			}

			countHeader++;
			countFooter++;

		} catch (Exception err) {
			err.printStackTrace();
		}
	}
}
