package ru.maxmine.bungee.packets;

import io.netty.channel.Channel;
import java.io.IOException;
import ru.maxmine.bungee.buffer.PacketBuffer;

public abstract class Packet {
    public abstract void write(PacketBuffer var1) throws IOException;

    public abstract void handle(PacketBuffer var1) throws IOException;

    public abstract void process(Channel var1) throws IOException;
}
