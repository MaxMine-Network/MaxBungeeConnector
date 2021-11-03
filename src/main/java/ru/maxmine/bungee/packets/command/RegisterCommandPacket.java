package ru.maxmine.bungee.packets.command;

import io.netty.channel.Channel;
import java.io.IOException;
import net.md_5.bungee.api.ProxyServer;
import ru.maxmine.bungee.CoreConnecter;
import ru.maxmine.bungee.buffer.PacketBuffer;
import ru.maxmine.bungee.packets.Packet;

public class RegisterCommandPacket extends Packet {
    private String command;

    public RegisterCommandPacket() {
    }

    public void write(PacketBuffer packetBuffer) throws IOException {
    }

    public void handle(PacketBuffer packetBuffer) throws IOException {
        this.command = packetBuffer.readString(256);
    }

    public void process(Channel channel) throws IOException {
        CoreConnecter.getInstance().registerCommand(this.command);
        ProxyServer.getInstance().getLogger().info("Registered command - " + this.command);
    }
}
