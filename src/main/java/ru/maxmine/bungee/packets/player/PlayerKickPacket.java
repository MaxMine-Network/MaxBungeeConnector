package ru.maxmine.bungee.packets.player;

import io.netty.channel.Channel;
import java.io.IOException;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import ru.maxmine.bungee.buffer.PacketBuffer;
import ru.maxmine.bungee.packets.Packet;

public class PlayerKickPacket extends Packet {
    private String player;
    private String reason;

    public PlayerKickPacket() {
    }

    public void write(PacketBuffer packetBuffer) throws IOException {
    }

    public void handle(PacketBuffer packetBuffer) throws IOException {
        this.player = packetBuffer.readString(16);
        this.reason = packetBuffer.readString(1024);
    }

    public void process(Channel channel) throws IOException {
        ProxiedPlayer player = ProxyServer.getInstance().getPlayer(this.player);
        if (player != null) {
            player.disconnect(this.reason);
        }

    }
}
