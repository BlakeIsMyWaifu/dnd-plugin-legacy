package dev.blakeismywaifu.dnd.API;

import dev.blakeismywaifu.dnd.Main;
import org.bukkit.ChatColor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Request {

	public static JSONObject getRequest(String uri) {
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

		JSONObject json = JSONparse(content.toString());
		Boolean status = (Boolean) json.get("success");

		if (status) {
			Main.log.info(ChatColor.GREEN + "API Success from url " + uri);
		} else {
			Main.log.info(ChatColor.RED + "API Failed from url " + uri);
		}

		return (JSONObject) json.get("data");
	}

	private static JSONObject JSONparse(String str) {
		JSONObject out = new JSONObject();
		try {
			out = (JSONObject) new JSONParser().parse(str);
		} catch (ParseException err) {
			err.printStackTrace();
		}
		return out;
	}
}
