//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package ru.maxmine.bungee.listeners;

import io.netty.channel.Channel;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.*;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import ru.maxmine.bungee.CoreConnecter;
import ru.maxmine.bungee.buffer.PacketBuffer;
import ru.maxmine.bungee.packets.Packet;
import ru.maxmine.bungee.packets.PacketManager;
import ru.maxmine.bungee.packets.command.CommandExecutePacket;
import ru.maxmine.bungee.packets.player.PlayerChangeServer;

public class CoreListener implements Listener {

    private static final TextComponent header = new TextComponent("§cMaxMine\n");
    private static final String footerString = "\nТекущий онлайн: §c%d\n§cmaxmine.su";

    static {
        ProxyServer.getInstance().getScheduler().schedule(CoreConnecter.getInstance(), () -> {
            for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
                player.setTabHeader(header, new TextComponent(String.format(footerString, ProxyServer.getInstance().getOnlineCount())));
            }
        }, 1L, 5L, TimeUnit.SECONDS);
    }

    @EventHandler
    public void onJoin(PostLoginEvent e) {
        ProxiedPlayer player = e.getPlayer();

        player.setTabHeader(header, new TextComponent(String.format(footerString, ProxyServer.getInstance().getOnlineCount())));
    }

    @EventHandler
    public void onQuit(PlayerDisconnectEvent e) {
        final ProxiedPlayer player = e.getPlayer();
        Packet packet = new Packet() {
            public void write(PacketBuffer packetBuffer) throws IOException {
                packetBuffer.writeIntLE(106);
                packetBuffer.writeString(player.getName());
            }

            public void handle(PacketBuffer packetBuffer) throws IOException {
            }

            public void process(Channel channel) throws IOException {
            }
        };
        PacketManager.sendPacket(packet);
    }

    @EventHandler
    public void onRedirect(ServerConnectedEvent event) {
        String player = event.getPlayer().getName();
        String server = event.getServer().getInfo().getName();
        PlayerChangeServer packet = new PlayerChangeServer(player, server);
        PacketManager.sendPacket(packet);
    }

    @EventHandler
    public void onChat(ChatEvent e) {
        if (!e.isCancelled()) {
            if (e.getMessage().startsWith("/")) {
                String[] message = e.getMessage().split(" ");
                String command = message[0].replace("/", "");
                if (CoreConnecter.getInstance().hasCommand(command)) {
                    e.setCancelled(true);
                    String[] args = new String[message.length - 1];
                    System.arraycopy(message, 1, args, 0, message.length - 1);
                    CommandExecutePacket packet = new CommandExecutePacket(((ProxiedPlayer)e.getSender()).getName(), command, args);
                    PacketManager.sendPacket(packet);
                }
            }

        }
    }
}
