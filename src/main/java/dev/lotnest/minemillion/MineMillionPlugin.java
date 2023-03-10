package dev.lotnest.minemillion;

import co.aikar.commands.CommandHelp;
import co.aikar.commands.CommandHelpFormatter;
import co.aikar.commands.CommandIssuer;
import co.aikar.commands.PaperCommandManager;
import dev.lotnest.minemillion.command.HelpSubCommand;
import dev.lotnest.minemillion.command.LanguageSubCommand;
import dev.lotnest.minemillion.command.QuestionSubCommand;
import dev.lotnest.minemillion.component.ComponentRegistry;
import dev.lotnest.minemillion.db.MySQLConnectionHolder;
import dev.lotnest.minemillion.event.EventManager;
import dev.lotnest.minemillion.file.ConfigHandler;
import dev.lotnest.minemillion.language.Language;
import dev.lotnest.minemillion.language.LanguageProvider;
import dev.lotnest.minemillion.player.MineMillionPlayerCache;
import dev.lotnest.minemillion.player.MineMillionPlayerDAOImpl;
import dev.lotnest.minemillion.question.QuestionManager;
import dev.lotnest.minemillion.question.QuestionProvider;
import dev.lotnest.minemillion.task.TaskManager;
import dev.lotnest.minemillion.util.ColorConstants;
import dev.lotnest.minemillion.util.LogFilter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.jooq.tools.StringUtils;

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

    private TaskManager taskManager;
    private EventManager eventManager;
    private PaperCommandManager commandManager;
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
        disableJooqProperties();
        registerInstances();
        addLogFilter();
        registerCommands();
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
    }

    private void registerCommands() {
        commandManager = new PaperCommandManager(this);

        commandManager.enableUnstableAPI("brigadier");
        commandManager.enableUnstableAPI("help");

        commandManager.setHelpFormatter(new CommandHelpFormatter(commandManager) {
            @Override
            public void printHelpHeader(CommandHelp help, CommandIssuer issuer) {
                issuer.sendMessage(StringUtils.EMPTY);
                issuer.sendMessage(ColorConstants.BLUE_STRING + "=== " + ColorConstants.GOLD_STRING + languageProvider.get("general.help") + ColorConstants.BLUE_STRING + " ===");
                issuer.sendMessage(StringUtils.EMPTY);
            }
        });

        commandManager.setDefaultExceptionHandler((command, registeredCommand, sender, args, throwable) -> {
            sender.sendMessage(ChatColor.RED + languageProvider.get("error.command"));
            return true;
        });

        commandManager.registerCommand(new HelpSubCommand());
        commandManager.registerCommand(new LanguageSubCommand(languageProvider));
        commandManager.registerCommand(new QuestionSubCommand(questionManager, languageProvider, playerCache));

        reloadCommandReplacements();

        commandManager.getCommandCompletions().registerAsyncCompletion("languages", context -> Language.getLanguagesAsStrings());
    }

    private void addLogFilter() {
        ((Logger) LogManager.getRootLogger()).addFilter(new LogFilter());
    }

    public void reloadCommandReplacements() {
        commandManager.getCommandReplacements().addReplacement("command.help.description", languageProvider.get("command.help.description"));
        commandManager.getCommandReplacements().addReplacement("command.help.syntax", languageProvider.get("command.help.syntax"));

        commandManager.getCommandReplacements().addReplacement("command.language.description", languageProvider.get("command.language.description"));
        commandManager.getCommandReplacements().addReplacement("command.language.syntax", languageProvider.get("command.language.syntax"));
        commandManager.getCommandReplacements().addReplacement("command.language.get.description", languageProvider.get("command.language.get.description"));
        commandManager.getCommandReplacements().addReplacement("command.language.get.syntax", languageProvider.get("command.language.get.syntax"));
        commandManager.getCommandReplacements().addReplacement("command.language.set.description", languageProvider.get("command.language.set.description"));
        commandManager.getCommandReplacements().addReplacement("command.language.set.syntax", languageProvider.get("command.language.set.syntax"));

        commandManager.getCommandReplacements().addReplacement("command.question.description", languageProvider.get("command.question.description"));
        commandManager.getCommandReplacements().addReplacement("command.question.syntax", languageProvider.get("command.question.syntax"));
        commandManager.getCommandReplacements().addReplacement("command.question.add.description", languageProvider.get("command.question.add.description"));
        commandManager.getCommandReplacements().addReplacement("command.question.add.syntax", languageProvider.get("command.question.add.syntax"));
        commandManager.getCommandReplacements().addReplacement("command.question.remove.description", languageProvider.get("command.question.remove.description"));
        commandManager.getCommandReplacements().addReplacement("command.question.remove.syntax", languageProvider.get("command.question.remove.syntax"));
        commandManager.getCommandReplacements().addReplacement("command.question.edit.description", languageProvider.get("command.question.edit.description"));
        commandManager.getCommandReplacements().addReplacement("command.question.edit.syntax", languageProvider.get("command.question.edit.syntax"));
        commandManager.getCommandReplacements().addReplacement("command.question.list.description", languageProvider.get("command.question.list.description"));
        commandManager.getCommandReplacements().addReplacement("command.question.list.syntax", languageProvider.get("command.question.list.syntax"));
        commandManager.getCommandReplacements().addReplacement("command.question.reload.description", languageProvider.get("command.question.reload.description"));
        commandManager.getCommandReplacements().addReplacement("command.question.reload.syntax", languageProvider.get("command.question.reload.syntax"));
    }
}
