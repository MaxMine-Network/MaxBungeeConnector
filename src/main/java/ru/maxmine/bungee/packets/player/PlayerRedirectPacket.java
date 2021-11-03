package ru.maxmine.bungee.packets.player;

import io.netty.channel.Channel;
import java.io.IOException;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import ru.maxmine.bungee.buffer.PacketBuffer;
import ru.maxmine.bungee.packets.Packet;

public class PlayerRedirectPacket extends Packet {
    private String name;
    private String server;

    public PlayerRedirectPacket() {
    }

    public void write(PacketBuffer packetBuffer) throws IOException {
    }

    public void handle(PacketBuffer packetBuffer) throws IOException {
        this.name = packetBuffer.readString(16);
        this.server = packetBuffer.readString(48);
    }

    public void process(Channel channel) throws IOException {
        ProxiedPlayer player = ProxyServer.getInstance().getPlayer(this.name);
        ServerInfo serverInfo = ProxyServer.getInstance().getServerInfo(this.server);
        if (player != null && serverInfo != null) {
            player.connect(serverInfo);
        }

    }
}
