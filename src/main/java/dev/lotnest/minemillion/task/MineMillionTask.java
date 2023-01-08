package dev.lotnest.minemillion.task;

import dev.lotnest.minemillion.MineMillionPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public abstract class MineMillionTask extends BukkitRunnable {

    protected final MineMillionPlugin plugin;

    protected MineMillionTask(@NotNull MineMillionPlugin plugin) {
        this.plugin = plugin;
    }
}
