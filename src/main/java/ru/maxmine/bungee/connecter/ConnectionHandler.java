//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package ru.maxmine.bungee.connecter;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.io.IOException;
import java.util.Iterator;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import ru.maxmine.bungee.buffer.PacketBuffer;
import ru.maxmine.bungee.packets.Packet;
import ru.maxmine.bungee.packets.PacketManager;
import ru.maxmine.bungee.packets.player.PlayerChangeServer;
import ru.maxmine.bungee.packets.player.PlayerJoinPacket;

public class ConnectionHandler extends SimpleChannelInboundHandler<Packet> {
    public static Thread reconnectThread;
    public static ChannelHandlerContext ctx;
    public static Channel channel;

    public ConnectionHandler() {
    }

    public void channelActive(ChannelHandlerContext ctx) {
        ConnectionHandler.ctx = ctx;
        channel = ctx.channel();
        PacketManager.sendPacket(this.getBungeePacket());
        if (ProxyServer.getInstance().getPlayers().size() > 0) {
            (new Thread(() -> {
                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException var4) {
                    var4.printStackTrace();
                }

                Iterator var0 = ProxyServer.getInstance().getPlayers().iterator();

                while(var0.hasNext()) {
                    ProxiedPlayer player = (ProxiedPlayer)var0.next();
                    PlayerJoinPacket packet = new PlayerJoinPacket(player);
                    PlayerChangeServer playerChangeServer = new PlayerChangeServer(player.getName(), player.getServer().getInfo().getName());
                    PacketManager.sendPacket(packet);
                    PacketManager.sendPacket(playerChangeServer);
                }

            })).start();
        }

        if (reconnectThread == null) {
            reconnectThread = new Thread(() -> {
                while(true) {
                    try {
                        Thread.sleep(2000L);
                        if (!channel.isActive()) {
                            Core.reconnect();
                        }
                    } catch (InterruptedException var1) {
                        var1.printStackTrace();
                    }
                }
            });
            reconnectThread.start();
        }

    }

    public void channelRead(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
        Packet packet = (Packet)msg;
        packet.process(channelHandlerContext.channel());
    }

    private Packet getBungeePacket() {
        return new Packet() {
            public void write(PacketBuffer packetBuffer) throws IOException {
                packetBuffer.writeIntLE(0);
                packetBuffer.writeIntLE(0);
                packetBuffer.writeString("Proxy-1");
            }

            public void handle(PacketBuffer packetBuffer) throws IOException {
            }

            public void process(Channel channel1) throws IOException {
            }
        };
    }

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Packet packet) throws Exception {
    }
}
