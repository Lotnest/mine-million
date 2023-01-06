package dev.lotnest.minemillion.component;

import dev.lotnest.minemillion.MineMillionPlugin;
import dev.lotnest.minemillion.component.impl.BootstrapComponent;

public class ComponentRegistry {

    private final MineMillionPlugin plugin;
    private final Component[] components;

    public ComponentRegistry(MineMillionPlugin plugin) {
        this.plugin = plugin;
        components = new Component[]{
                new BootstrapComponent(plugin),
//            new LanguageComponent(),
//            new CommandComponent(),
//            new ListenerComponent(),
//            new DatabaseComponent()
        };
    }

    public void initializeAll() {
        for (Component component : components) {
            ComponentResult result = component.initialize();

            if (result == ComponentResult.ERROR) {
                plugin.getLogger().severe("Error while initializing component " + component.getClass().getSimpleName());
            }
        }
    }

    public void shutdownAll() {
        for (Component component : components) {
            ComponentResult result = component.shutdown();

            if (result == ComponentResult.ERROR) {
                plugin.getLogger().severe("Error while shutting down component " + component.getClass().getSimpleName());
            }
        }
    }
}
