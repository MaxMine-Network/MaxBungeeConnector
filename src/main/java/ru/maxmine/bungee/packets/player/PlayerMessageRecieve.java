package ru.maxmine.bungee.packets.player;

import io.netty.channel.Channel;
import java.io.IOException;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import ru.maxmine.bungee.buffer.PacketBuffer;
import ru.maxmine.bungee.packets.Packet;

public class PlayerMessageRecieve extends Packet {
    private String player;
    private String message;

    public PlayerMessageRecieve() {
    }

    public void write(PacketBuffer packetBuffer) throws IOException {
    }

    public void handle(PacketBuffer packetBuffer) throws IOException {
        this.player = packetBuffer.readString(16);
        this.message = packetBuffer.readString(15000);
    }

    public void process(Channel channel) throws IOException {
        ProxiedPlayer player = ProxyServer.getInstance().getPlayer(this.player);
        if (player != null) {
            player.sendMessage(this.message);
        }

    }
}
