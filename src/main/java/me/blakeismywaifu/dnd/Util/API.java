package me.blakeismywaifu.dnd.Util;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class API {

	public JSONObject json;
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

	public API(String id) {
		String res = API.sendRequest("http://localhost:3000/character/" + id);
		this.json = API.JSONparse(res);
		this.status = (Boolean) json.get("status");
	}
}
