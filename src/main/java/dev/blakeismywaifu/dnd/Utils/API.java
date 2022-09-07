package dev.blakeismywaifu.dnd.Utils;

import dev.blakeismywaifu.dnd.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Objects;
import java.util.UUID;

public class API {

	public JSONObject json;
	public JSONObject data;
	public Boolean status;

	public static String sendRequest(String uri) {
		StringBuilder content = new StringBuilder();
		try {
			URL url = new URL(uri);
			InputStreamReader streamReader = new InputStreamReader(url.openStream());
			BufferedReader bufferedReader = new BufferedReader(streamReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				content.append(line).append("\n");
			}
			bufferedReader.close();
		} catch (IOException err) {
			err.printStackTrace();
		}
		return content.toString();
	}

	public static JSONObject JSONparse(String str) {
		JSONObject out = new JSONObject();
		try {
			out = (JSONObject) new JSONParser().parse(str);
		} catch (ParseException err) {
			err.printStackTrace();
		}
		return out;
	}

	public API(UUID playerId, String id) {
		String res = API.sendRequest("https://character-service.dndbeyond.com/character/v5/character/" + id);
		this.json = API.JSONparse(res);
		this.status = (Boolean) json.get("success");
		this.data = (JSONObject) json.get("data");

		String playerMessage = " for player" + Objects.requireNonNull(Bukkit.getPlayer(playerId)).getName() + " with character id " + id;

		if (this.status) {
			PlayerCache.putCache(playerId, this.data);
			Main.log.info(ChatColor.GREEN + "API Success" + playerMessage);
		} else {
			Main.log.info(ChatColor.RED + "API Failed" + playerMessage);
		}
	}
}
