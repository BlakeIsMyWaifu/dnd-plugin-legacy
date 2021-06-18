package me.blakeismywaifu.dnd.Util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NPC {

	public static List<EntityPlayer> NPC = new ArrayList<>();

	public static void createNPC(Player player, String NPCName, String skinName) {
		MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
		WorldServer world = ((CraftWorld) Bukkit.getWorld(player.getWorld().getName())).getHandle();
		GameProfile gameProfile = new GameProfile(UUID.randomUUID(), Colours.translateColorCodes(NPCName));
		EntityPlayer npc = new EntityPlayer(server, world, gameProfile, new PlayerInteractManager(world));

		Location location = player.getLocation();
		npc.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());

		Property skinProperty = PlayerSkin.getSkin(skinName);
		gameProfile.getProperties().put("textures", skinProperty);
		npc.getDataWatcher().set(new DataWatcherObject<>(16, DataWatcherRegistry.a), (byte) 127);

		NPC.add(npc);
		sendAllNPCPacket(npc);
	}

	public static void sendAllNPCPacket(EntityPlayer npc) {
		Bukkit.getOnlinePlayers().forEach(player -> addNPCPacket(npc, player));
	}

	public static void addNPCPacket(EntityPlayer npc, Player player) {
		PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
		connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
		connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
		connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc, (byte) (npc.yaw * 256 / 360)));
		connection.sendPacket(new PacketPlayOutEntityMetadata(npc.getId(), npc.getDataWatcher(), true));
	}
}
