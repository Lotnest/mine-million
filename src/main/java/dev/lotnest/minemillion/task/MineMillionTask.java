package dev.lotnest.minemillion.task;

import dev.lotnest.minemillion.MineMillionPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class MineMillionTask extends BukkitRunnable {

    protected final MineMillionPlugin plugin;

    protected MineMillionTask(MineMillionPlugin plugin) {
        this.plugin = plugin;
    }
}
