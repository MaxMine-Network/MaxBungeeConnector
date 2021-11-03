package ru.maxmine.bungee.packets;

import java.util.HashMap;
import java.util.Map;
import ru.maxmine.bungee.connecter.ConnectionHandler;
import ru.maxmine.bungee.packets.command.RegisterCommandPacket;
import ru.maxmine.bungee.packets.player.PlayerChangeServer;
import ru.maxmine.bungee.packets.player.PlayerJoinPacket;
import ru.maxmine.bungee.packets.player.PlayerKickPacket;
import ru.maxmine.bungee.packets.player.PlayerMessageRecieve;
import ru.maxmine.bungee.packets.player.PlayerRedirectPacket;
import ru.maxmine.bungee.packets.server.ServerConnectPacket;

public class PacketManager {
    private static Map<Integer, Class<? extends Packet>> packets = new HashMap<>();

    public static boolean hasPacket(int id) {
        return packets.containsKey(id);
    }

    public static Packet getPacket(int id) {
        try {
            return (Packet)((Class)packets.get(id)).newInstance();
        } catch (Exception var2) {
            return null;
        }
    }

    public static void registerPacket(int id, Class<? extends Packet> clazz) {
        packets.put(id, clazz);
    }

    public static void sendPacket(Packet packet) {
        ConnectionHandler.channel.writeAndFlush(packet);
    }

    static {
        packets.put(51, ServerConnectPacket.class);
        packets.put(100, PlayerChangeServer.class);
        packets.put(101, PlayerJoinPacket.class);
        packets.put(104, PlayerMessageRecieve.class);
        packets.put(105, PlayerRedirectPacket.class);
        packets.put(107, PlayerKickPacket.class);
        packets.put(600, RegisterCommandPacket.class);
    }
}
