package dev.lotnest.minemillion;

import dev.lotnest.minemillion.command.CommandRegistry;
import dev.lotnest.minemillion.component.ComponentRegistry;
import dev.lotnest.minemillion.db.MySQLConnectionHolder;
import dev.lotnest.minemillion.event.EventManager;
import dev.lotnest.minemillion.file.ConfigHandler;
import dev.lotnest.minemillion.language.LanguageProvider;
import dev.lotnest.minemillion.player.MineMillionPlayerCache;
import dev.lotnest.minemillion.player.MineMillionPlayerDAOImpl;
import dev.lotnest.minemillion.question.QuestionManager;
import dev.lotnest.minemillion.question.QuestionProvider;
import dev.lotnest.minemillion.task.TaskManager;
import dev.lotnest.minemillion.util.LogFilter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;

@NoArgsConstructor
@Getter
public class MineMillionPlugin extends JavaPlugin {

    private static MineMillionPlugin instance;

    private ConfigHandler configHandler;
    private LanguageProvider languageProvider;
    private QuestionProvider questionProvider;

    private MySQLConnectionHolder connectionHolder;
    private MineMillionPlayerCache playerCache;

    private ComponentRegistry componentRegistry;
    private CommandRegistry commandRegistry;

    private TaskManager taskManager;
    private EventManager eventManager;
    private QuestionManager questionManager;

    protected MineMillionPlugin(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
    }

    public static MineMillionPlugin getInstance() {
        if (instance == null) {
            instance = (MineMillionPlugin) Bukkit.getPluginManager().getPlugin("MineMillion");
        }
        return instance;
    }

    @Override
    public void onEnable() {
        addLogFilter();
        disableJooqProperties();
        registerInstances();
    }

    @Override
    public void onDisable() {
        connectionHolder.disconnect();
    }

    private void disableJooqProperties() {
        System.setProperty("org.jooq.no-logo", "true");
        System.setProperty("org.jooq.no-tips", "true");
    }

    private void registerInstances() {
        configHandler = new ConfigHandler(this);
        languageProvider = new LanguageProvider(this, configHandler.getLanguage());

        connectionHolder = new MySQLConnectionHolder(this);
        connectionHolder.connect();
        playerCache = new MineMillionPlayerCache(new MineMillionPlayerDAOImpl());

        componentRegistry = new ComponentRegistry(this);

        taskManager = new TaskManager(this);
        eventManager = new EventManager(this);
        questionProvider = new QuestionProvider();
        questionManager = new QuestionManager(questionProvider);

        commandRegistry = new CommandRegistry(this);
    }

    private void addLogFilter() {
        ((Logger) LogManager.getRootLogger()).addFilter(new LogFilter());
    }
}
