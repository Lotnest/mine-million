package dev.lotnest.minemillion.component;

import dev.lotnest.minemillion.MineMillionPlugin;
import dev.lotnest.minemillion.component.impl.BootstrapComponent;
import dev.lotnest.minemillion.util.exception.ComponentNotFoundException;

public class ComponentRegistry {

    private final MineMillionPlugin plugin;
    private final Component[] components;

    public ComponentRegistry(MineMillionPlugin plugin) {
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

    public void reloadAll() {
        shutdownAll();
        initializeAll();
    }

    public void reload(Component component) {
        if (component == null) {
            return;
        }

        component.shutdown();
        component.initialize();
    }

    public Component[] getComponents() {
        return components;
    }

    public <T extends Component> T getComponent(Class<T> clazz) throws ComponentNotFoundException {
        for (Component component : components) {
            if (clazz.isInstance(component)) {
                return clazz.cast(component);
            }
        }

        throw new ComponentNotFoundException(clazz.getSimpleName());
    }
}
