package ru.maxmine.bungee;

import java.util.ArrayList;
import java.util.List;
import net.md_5.bungee.api.plugin.Plugin;
import ru.maxmine.bungee.connecter.Core;
import ru.maxmine.bungee.listeners.CoreListener;
import ru.maxmine.bungee.packets.PacketManager;

public class CoreConnecter extends Plugin {

    private static CoreConnecter instance;
    private List<String> commands = new ArrayList<>();

    public static CoreConnecter getInstance() {
        return instance;
    }

    public void onEnable() {
        instance = this;
        new PacketManager();
        this.getProxy().getPluginManager().registerListener(this, new CoreListener());
        new Core();
    }

    public void registerCommand(String command) {
        this.commands.add(command.toLowerCase());
    }

    public boolean hasCommand(String command) {
        return this.commands.contains(command.toLowerCase());
    }
}
