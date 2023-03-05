package dev.lotnest.minemillion.event;

import dev.lotnest.minemillion.MineMillionPlugin;
import dev.lotnest.minemillion.event.listener.PlayerConnectionListener;
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
        registerListener(new PlayerConnectionListener(plugin));
        registerListener(new GUIHandler());
    }

    public void registerListener(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, plugin);
    }
}
