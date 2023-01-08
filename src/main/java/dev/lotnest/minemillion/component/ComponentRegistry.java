package dev.lotnest.minemillion.component;

import dev.lotnest.minemillion.MineMillionPlugin;
import dev.lotnest.minemillion.component.impl.BootstrapComponent;
import dev.lotnest.minemillion.util.LoggerUtil;
import dev.lotnest.minemillion.util.exception.ComponentNotRegisteredException;
import org.jetbrains.annotations.NotNull;

public class ComponentRegistry {

    private final MineMillionPlugin plugin;
    private final Component[] components;

    public ComponentRegistry(@NotNull MineMillionPlugin plugin) {
        this.plugin = plugin;
        components = new Component[]{
                new BootstrapComponent(plugin)
        };

        initializeAll();
    }

    private void initializeAll() {
        for (Component component : components) {
            ComponentResult result = component.initialize();

            if (result == ComponentResult.ERROR) {
                LoggerUtil.severe("component.failedToInitialize", component.getClass().getSimpleName());
            }
        }
    }

    public void shutdownAll() {
        for (Component component : components) {
            ComponentResult result = component.shutdown();

            if (result == ComponentResult.ERROR) {
                LoggerUtil.severe("component.failedToInitialize", component.getClass().getSimpleName());
            }
        }
    }

    public void reloadAll() {
        shutdownAll();
        initializeAll();
    }

    public void reload(@NotNull Component component) {
        component.shutdown();
        component.initialize();
    }

    public Component[] getComponents() {
        return components;
    }

    public <T extends Component> T getComponent(Class<T> clazz) throws ComponentNotRegisteredException {
        for (Component component : components) {
            if (clazz.isInstance(component)) {
                return clazz.cast(component);
            }
        }

        throw new ComponentNotRegisteredException(plugin.getLanguageProvider().get("component.notRegistered", clazz.getSimpleName()));
    }
}
