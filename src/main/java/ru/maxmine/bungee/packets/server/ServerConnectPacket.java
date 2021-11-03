package ru.maxmine.bungee.packets.server;

import io.netty.channel.Channel;
import java.io.IOException;
import java.net.InetSocketAddress;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import ru.maxmine.bungee.buffer.PacketBuffer;
import ru.maxmine.bungee.packets.Packet;

public class ServerConnectPacket extends Packet {
    private String name;
    private String ip;
    private int port;

    public ServerConnectPacket() {
    }

    public void write(PacketBuffer packetBuffer) throws IOException {
    }

    public void handle(PacketBuffer packetBuffer) throws IOException {
        this.name = packetBuffer.readString(48);
        this.ip = packetBuffer.readString(256);
        this.port = packetBuffer.readIntLE();
    }

    public void process(Channel channel) throws IOException {
        ServerInfo serverInfo = ProxyServer.getInstance().constructServerInfo(this.name, new InetSocketAddress(this.ip, this.port), "", false);
        ProxyServer.getInstance().getServers().put(this.name, serverInfo);
        ProxyServer.getInstance().getLogger().info("Server " + this.name + " connected to Proxy");
    }
}
