package dev.lotnest.minemillion;

import dev.lotnest.minemillion.component.ComponentRegistry;
import dev.lotnest.minemillion.db.MySQLConnectionHolder;
import dev.lotnest.minemillion.event.EventManager;
import dev.lotnest.minemillion.file.ConfigHandler;
import dev.lotnest.minemillion.language.LanguageProvider;
import dev.lotnest.minemillion.player.MineMillionPlayerCache;
import dev.lotnest.minemillion.player.MineMillionPlayerDAOImpl;
import dev.lotnest.minemillion.task.TaskManager;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;

@NoArgsConstructor
@Getter
public class MineMillionPlugin extends JavaPlugin {

    private ConfigHandler configHandler;
    private LanguageProvider languageProvider;

    private MySQLConnectionHolder connectionHolder;
    private MineMillionPlayerCache playerCache;

    private ComponentRegistry componentRegistry;

    private TaskManager taskManager;
    private EventManager eventManager;

    protected MineMillionPlugin(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
    }

    public static MineMillionPlugin getInstance() {
        return (MineMillionPlugin) Bukkit.getPluginManager().getPlugin("MineMillion");
    }

    @Override
    public void onEnable() {
        System.setProperty("org.jooq.no-logo", "true");
        System.setProperty("org.jooq.no-tips", "true");

        configHandler = new ConfigHandler(this);
        languageProvider = new LanguageProvider(this, configHandler.getLanguage());

        connectionHolder = new MySQLConnectionHolder(this);
        connectionHolder.connect();
        playerCache = new MineMillionPlayerCache(new MineMillionPlayerDAOImpl());

        componentRegistry = new ComponentRegistry(this);

        taskManager = new TaskManager(this);
        eventManager = new EventManager(this);
    }

    @Override
    public void onDisable() {
        connectionHolder.disconnect();
    }
}
