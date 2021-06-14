package me.blakeismywaifu.dnd.Util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.blakeismywaifu.dnd.Main;
import net.minecraft.server.v1_16_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_16_R3.PlayerConnection;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PlayerSkin {

	public static void changeSkin(Player player, Property skin) {

		GameProfile profile = ((CraftPlayer) player).getHandle().getProfile();
		PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;

		connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, ((CraftPlayer) player).getHandle()));

//		Collection<Property> s = profile.getProperties().get("textures");
//		Main.log.info(s.toString());

		profile.getProperties().removeAll("textures");
		profile.getProperties().put("textures", skin);

		connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, ((CraftPlayer) player).getHandle()));
	}

	public static Map<String, Property> skins;
	static {
		skins = new HashMap<>();
		skins.put("gura", new Property("textures", "ewogICJ0aW1lc3RhbXAiIDogMTYxMzYzNzE5NDIxMiwKICAicHJvZmlsZUlkIiA6ICJjMDI0Y2M0YTQwMzc0YWFjYTk2ZTE2Y2MwODM1ZDE4MCIsCiAgInByb2ZpbGVOYW1lIiA6ICJHZW9yZ2VOb0ZvdW5kIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzk1MGRmZDE1MzdjYjM1NzljMTg5MWVmNGI2MzhkZGVkOWExMTNhYTJjNGIyZjc0NzBkMThlNGNlNDJhYjgwMDIiLAogICAgICAibWV0YWRhdGEiIDogewogICAgICAgICJtb2RlbCIgOiAic2xpbSIKICAgICAgfQogICAgfQogIH0KfQ==", "rdDu+lRdpvO3S2HzoTiynkRW2Afzu+Ou02rDFmArcmCMcCtq7vRWrgiGy95mEvkF4BUdqzAxwP70mLQkA1KzNyiiwmRMLQbxnh5GucylqtID6RKwIsx7RZCkOK0f/1p55gkAebPZM1vVp5hp7hDuTNE8FSbfgr3eYMmRDXWo+1K33av1UMPtvFPuANLROMfIUkXs/q2s1aNu96aQRFGIkaHoAaHS6zxEwnt+nUE/XCrgV3QxTxrKX6sdfGl12tuc9kkF1txGzdR5AMNmykxMF7qAbs6iglLGS3BWn4m79inqTPWYBBlr0dtHSACa3Z7To5xNns5BdPbzlEpj1JsZ7nMVrSKw4XWZbYo833P0+q+Anhbbd8nGuXEBPghEqEh/2RF11ws2PB+UwNQveSCDvo5c5wgLxJPvdHn+r70NJjCshrR4Jzlup9a1jvPH+WNKyrpOXCbsFn6fc4bk/wSDDUgv5TC4HwgbPoNoDTTm3pI1+iQNVI53B79nImrpvlk8IwNUM7/u6LF6lXOgrnnzx5qsKpkzO5gdevaqZ1KEjffgbn0V2CfMf451tJJ9BM/W0fZCCrILCnPNmN4/x8aDAp/yXJsdwvHg/IpP8KOZliVwz405YlbbkUZjKfvEEdZAhrVlBIWG8jgjxcgj5nOl088is9XN8J7aqf1Ex9esym8="));
	}
}
