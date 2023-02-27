package dev.lotnest.minemillion;

import co.aikar.commands.CommandHelp;
import co.aikar.commands.CommandHelpFormatter;
import co.aikar.commands.CommandIssuer;
import co.aikar.commands.MessageKeys;
import co.aikar.commands.MessageType;
import co.aikar.commands.PaperCommandManager;
import dev.lotnest.minemillion.command.MineMillionCommand;
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
import dev.lotnest.minemillion.util.LoggerUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.jooq.tools.StringUtils;

import java.io.File;

@NoArgsConstructor
@Getter
public class MineMillionPlugin extends JavaPlugin {

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
        questionProvider = new QuestionProvider();
        questionManager = new QuestionManager(questionProvider);

        registerCommands();
    }

    @Override
    public void onDisable() {
        connectionHolder.disconnect();
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
            LoggerUtil.unknownError(throwable);
            sender.sendMessage(MessageType.ERROR, MessageKeys.ERROR_GENERIC_LOGGED);
            return true;
        });

        commandManager.registerCommand(new MineMillionCommand(this));

        reloadCommandReplacements();

        commandManager.getCommandCompletions().registerAsyncCompletion("languages", context -> Language.getLanguagesAsStrings());
    }

    public void reloadCommandReplacements() {
        commandManager.getCommandReplacements().addReplacement("command.help.description", languageProvider.get("command.help.description"));
        commandManager.getCommandReplacements().addReplacement("command.help.syntax", languageProvider.get("command.help.syntax"));

        commandManager.getCommandReplacements().addReplacement("command.language.description", languageProvider.get("command.language.description"));
        commandManager.getCommandReplacements().addReplacement("command.language.syntax", languageProvider.get("command.language.syntax"));
    }
}
