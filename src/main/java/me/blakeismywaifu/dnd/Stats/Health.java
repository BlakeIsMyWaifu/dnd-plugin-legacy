package me.blakeismywaifu.dnd.Stats;

import org.json.simple.JSONObject;

public class Health {

	public Long current;
	public Long max;
	public Long temp;

	public Health(JSONObject json) {
		JSONObject data = (JSONObject) json.get("health");

		this.current = (Long) data.get("current");
		this.max = (Long) data.get("max");
		this.temp = (Long) data.get("temp");
	}
}
