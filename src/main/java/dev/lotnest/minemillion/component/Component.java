package dev.lotnest.minemillion.component;

import dev.lotnest.minemillion.MineMillionPlugin;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public abstract class Component {

    protected final @NotNull MineMillionPlugin plugin;

    public abstract @NotNull ComponentResult initialize();

    public abstract boolean isInitialized();

    public abstract @NotNull ComponentResult shutdown();
}
