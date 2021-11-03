//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package ru.maxmine.bungee.connecter;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.FixedRecvByteBufAllocator;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.net.InetSocketAddress;
import net.md_5.bungee.api.ProxyServer;
import ru.maxmine.bungee.codec.Decoder;
import ru.maxmine.bungee.codec.Encoder;

public class Core {
    private static boolean connected;
    private static boolean message;
    private static Thread connectionThread;
    private static ChannelFuture channel;

    public Core() {
        connect();
    }

    public static void reconnect() {
        connected = false;
        connect();
        if (!message) {
            ProxyServer.getInstance().broadcast("[§cMaxMine§f] §cГлавное ядро было выключено, без паники!");
            ProxyServer.getInstance().broadcast("§cВ данный момент все команды, включая §f/find§c, §f/msg§c, §f/party");
            ProxyServer.getInstance().broadcast("§cбудут недоступны. Также недоступно переключение по серверам.");
        }

        message = true;
    }

    private static void connect() {
        connectionThread = new Thread(() -> {
            NioEventLoopGroup group = new NioEventLoopGroup();

            try {
                Bootstrap client = new Bootstrap();
                client.group(group).channel(NioSocketChannel.class).remoteAddress(new InetSocketAddress("localhost", 1488)).option(ChannelOption.SO_RCVBUF, 8192).option(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(8192)).option(ChannelOption.TCP_NODELAY, true).handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel ch) {
                        ch.pipeline().addLast("decoder", new Decoder());
                        ch.pipeline().addLast("encoder", new Encoder());
                        ch.pipeline().addLast("handler", new ConnectionHandler());
                    }
                });

                try {
                    channel = client.connect();
                    if (channel.channel().isActive()) {
                        message = false;
                        connected = true;
                    }

                    channel.sync();
                    channel.channel().closeFuture().sync();
                } catch (Exception var6) {
                    ProxyServer.getInstance().getLogger().info("Ошибка соединения с Core - " + var6.getLocalizedMessage());
                }
            } finally {
                group.shutdownGracefully();
            }

        });
        connectionThread.start();
    }
}
