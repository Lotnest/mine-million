package dev.lotnest.minemillion;

import dev.lotnest.minemillion.language.Language;
import dev.lotnest.minemillion.language.LanguageProvider;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;

@Getter
public class MineMillionPlugin extends JavaPlugin {

    private LanguageProvider languageProvider;

    protected MineMillionPlugin(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
    }

    @Override
    public void onEnable() {
        languageProvider = new LanguageProvider(this, Language.ENGLISH_US); // change this to value from config
    }

    public static MineMillionPlugin getInstance() {
        return (MineMillionPlugin) Bukkit.getPluginManager().getPlugin("MineMillion");
    }
}
