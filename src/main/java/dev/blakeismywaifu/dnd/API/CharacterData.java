package dev.blakeismywaifu.dnd.API;

import dev.blakeismywaifu.dnd.Utils.PlayerCache;
import org.json.simple.JSONObject;

import java.util.UUID;

public class CharacterData {

	public static void getAndCacheData(UUID playerId, String id) {
		JSONObject data = Request.getRequest("https://character-service.dndbeyond.com/character/v5/character/" + id);
		PlayerCache.putCache(playerId, data);
	}
}
