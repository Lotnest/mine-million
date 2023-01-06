package dev.lotnest.minemillion.component;

import dev.lotnest.minemillion.MineMillionPlugin;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class Component {

    protected final MineMillionPlugin plugin;

    public abstract ComponentResult initialize();

    public abstract boolean isInitialized();

    public abstract ComponentResult shutdown();
}
