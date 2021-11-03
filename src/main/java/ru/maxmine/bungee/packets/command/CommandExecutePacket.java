package ru.maxmine.bungee.packets.command;

import io.netty.channel.Channel;
import java.io.IOException;
import ru.maxmine.bungee.buffer.PacketBuffer;
import ru.maxmine.bungee.packets.Packet;

public class CommandExecutePacket extends Packet {
    private String player;
    private String command;
    private String[] args;

    public CommandExecutePacket(String player, String command, String[] args) {
        this.player = player;
        this.command = command;
        this.args = args;
    }

    public void write(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeIntLE(601);
        packetBuffer.writeString(this.player);
        packetBuffer.writeString(this.command);
        packetBuffer.writeIntLE(this.args.length);
        String[] var2 = this.args;
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String s = var2[var4];
            packetBuffer.writeString(s);
        }

    }

    public void handle(PacketBuffer packetBuffer) throws IOException {
    }

    public void process(Channel channel) throws IOException {
    }
}
