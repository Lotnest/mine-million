package dev.lotnest.minemillion.event;

import dev.lotnest.minemillion.MineMillionPlugin;
import dev.lotnest.minemillion.event.listener.ChatListener;
import dev.lotnest.minemillion.event.listener.ConnectionListener;
import dev.lotnest.minemillion.gui.GUIHandler;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class EventManager {

    private final MineMillionPlugin plugin;

    public EventManager(@NotNull MineMillionPlugin plugin) {
        this.plugin = plugin;
        registerListeners();
    }

    private void registerListeners() {
        registerListener(new ConnectionListener(plugin));
        registerListener(new GUIHandler());
        registerListener(new ChatListener(plugin));
    }

    public void registerListener(@NotNull Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, plugin);
    }
}
