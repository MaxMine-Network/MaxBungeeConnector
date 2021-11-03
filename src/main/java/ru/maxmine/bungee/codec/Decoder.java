package ru.maxmine.bungee.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;
import net.md_5.bungee.api.ProxyServer;
import ru.maxmine.bungee.buffer.PacketBuffer;
import ru.maxmine.bungee.packets.Packet;
import ru.maxmine.bungee.packets.PacketManager;

public class Decoder extends ByteToMessageDecoder {
    public Decoder() {
    }

    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() != 0) {
            PacketBuffer packetBuffer = new PacketBuffer(byteBuf);
            int packetID = packetBuffer.readIntLE();
            if (PacketManager.hasPacket(packetID)) {
                Packet packet = PacketManager.getPacket(packetID);
                packet.handle(packetBuffer);
                list.add(packet);
            } else {
                ProxyServer.getInstance().getLogger().info("Packet " + packetID + " not found");
            }
        }

    }
}
