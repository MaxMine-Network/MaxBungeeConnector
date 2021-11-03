package ru.maxmine.bungee.packets.player;

import io.netty.channel.Channel;
import java.io.IOException;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import ru.maxmine.bungee.buffer.PacketBuffer;
import ru.maxmine.bungee.packets.Packet;

public class PlayerJoinPacket extends Packet {
    private String name;
    private String ip;

    public PlayerJoinPacket(ProxiedPlayer player) {
        this.name = player.getName();
        this.ip = player.getAddress().getHostName();
    }

    public void write(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeIntLE(101);
        String proxy = "Proxy-1";
        packetBuffer.writeString(proxy);
        packetBuffer.writeString(this.name);
        packetBuffer.writeString(this.ip);
    }

    public void handle(PacketBuffer packetBuffer) throws IOException {
    }

    public void process(Channel channel) throws IOException {
    }
}
