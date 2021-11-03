package ru.maxmine.bungee.packets.player;

import io.netty.channel.Channel;
import java.io.IOException;
import ru.maxmine.bungee.buffer.PacketBuffer;
import ru.maxmine.bungee.packets.Packet;

public class PlayerChangeServer extends Packet {
    private String player;
    private String server;

    public PlayerChangeServer(String player, String server) {
        this.player = player;
        this.server = server;
    }

    public void write(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeIntLE(100);
        packetBuffer.writeString(this.player);
        packetBuffer.writeString(this.server);
    }

    public void handle(PacketBuffer packetBuffer) throws IOException {
    }

    public void process(Channel channel) throws IOException {
    }
}
