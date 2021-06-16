package me.blakeismywaifu.dnd.Util;

import com.mojang.authlib.properties.Property;
import me.blakeismywaifu.dnd.Main;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerCache implements ConfigurationSerializable {

	public UUID uuid;
	public String bind;
	public JSONObject cache;
	public Property skin;

	public PlayerCache(UUID uuid) {
		this.uuid = uuid;
	}

	public PlayerCache(UUID uuid, String bind, JSONObject cache, Property skin) {
		this.uuid = uuid;
		this.bind = bind;
		this.cache = cache;
		this.skin = skin;
	}

	private static void createCache(UUID uuid) {
		if (!Main.cache.containsKey(uuid)) Main.cache.put(uuid, new PlayerCache(uuid));
	}

	public static Map<UUID, String> getBinds() {
		Map<UUID, String> out = new HashMap<>();
		Main.cache.forEach((key, value) -> {
			if (value.bind == null) return;
			out.put(key, value.bind);
		});
		return out;
	}

	public static void putBind(UUID uuid, String bind) {
		createCache(uuid);
		Main.cache.get(uuid).bind = bind;
	}

	public static Map<UUID, JSONObject> getCache() {
		Map<UUID, JSONObject> out = new HashMap<>();
		Main.cache.forEach((key, value) -> {
			if (value.cache == null) return;
			out.put(key, value.cache);
		});
		return out;
	}

	public static void putCache(UUID uuid, JSONObject cache) {
		createCache(uuid);
		Main.cache.get(uuid).cache = cache;
	}

	public static Map<UUID, Property> getSkin() {
		Map<UUID, Property> out = new HashMap<>();
		Main.cache.forEach((key, value) -> {
			if (value.skin == null) return;
			out.put(key, value.skin);
		});
		return out;
	}

	public static void putSkin(UUID uuid, Property skin) {
		createCache(uuid);
		Main.cache.get(uuid).skin = skin;
	}

	@NotNull
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> serialized = new HashMap<>();
		serialized.put("uuid", uuid.toString());
		serialized.put("bind", bind);
		serialized.put("cache", cache);
		serialized.put("skin", skin);
		return serialized;
	}

	public static PlayerCache deserialize(Map<String, Object> deserialized) {
		UUID uuid = UUID.fromString((String) deserialized.get("uuid"));
		String bind = (String) deserialized.get("bind");
//		JSONObject cache = API.JSONparse((String) deserialized.get("cache"));
		Property skin = (Property) deserialized.get("skin");
		return new PlayerCache(uuid, bind, null, skin);
	}
}
