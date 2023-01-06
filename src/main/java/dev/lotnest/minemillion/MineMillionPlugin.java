package dev.lotnest.minemillion;

import dev.lotnest.minemillion.language.Language;
import dev.lotnest.minemillion.language.LanguageProvider;
import lombok.Getter;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;

@Getter
public class MineMillionPlugin extends JavaPlugin {

    private static MineMillionPlugin instance;

    private LanguageProvider languageProvider;

    protected MineMillionPlugin(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
    }

    @Override
    public void onEnable() {
        instance = this;
        languageProvider = new LanguageProvider(this, Language.ENGLISH_US); // change this to value from config
    }

    public static MineMillionPlugin getInstance() {
        return instance;
    }
}
