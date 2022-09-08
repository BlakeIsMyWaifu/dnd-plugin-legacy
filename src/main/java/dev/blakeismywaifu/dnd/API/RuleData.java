package dev.blakeismywaifu.dnd.API;

import org.json.simple.JSONObject;

public class RuleData {

	private final String url = "https://character-service.dndbeyond.com/character/v5/rule-data";
	private JSONObject data;

	public RuleData() {
		this.data = Request.getRequest(url);
	}

	public JSONObject getData() {
		if (this.data == null) {
			this.data = Request.getRequest(url);
		}

		return this.data;
	}
}
