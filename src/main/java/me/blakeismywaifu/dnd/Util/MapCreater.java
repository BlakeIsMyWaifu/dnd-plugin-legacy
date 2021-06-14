package me.blakeismywaifu.dnd.Util;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class MapCreater extends MapRenderer {

	private BufferedImage image;
	private boolean done;

	public MapCreater() {
		this.done = false;
	}

	public MapCreater(String url) {
		load(url);
		this.done = false;
	}

	public boolean load(String url) {
		BufferedImage image;
		try {
			image = ImageIO.read(new URL(url));
			image = MapPalette.resizeImage(image);
		} catch (IOException error) {
			return false;
		}
		this.image = image;
		return true;
	}

	@Override
	public void render(MapView view, MapCanvas canvas, @NotNull Player player) {
		if (done) return;

		canvas.drawImage(0, 0, image);

		view.setTrackingPosition(false);
		done = true;
	}
}
