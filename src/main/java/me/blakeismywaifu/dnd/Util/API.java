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

	private static String sendRequest(String uri) {
		String out = "";
		try {
			URL url = new URL(uri);
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			String str = in.readLine();
			in.close();
			if (str != null) {
				out = str;
			}
		} catch (IOException err) {
			err.printStackTrace();
		}
		return out;
	}

	public static JSONObject JSONparse(String str) {
		JSONObject out = new JSONObject();
		try {
			JSONParser jsonParser = new JSONParser();
			Object parsed = jsonParser.parse(str);
			out = (JSONObject) parsed;
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
