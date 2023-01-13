package dev.lotnest.minemillion.file;

import dev.lotnest.minemillion.MineMillionPlugin;
import dev.lotnest.minemillion.language.Language;
import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;

@Getter
public class ConfigHandler {

    private final MineMillionPlugin plugin;
    private final File file;
    private final YamlConfiguration yaml;

    public ConfigHandler(@NotNull MineMillionPlugin plugin) {
        this.plugin = plugin;

        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        this.file = new File(plugin.getDataFolder(), "config.yml");
        if (!file.exists()) {
            plugin.saveResource("config.yml", false);
        }

        yaml = YamlConfiguration.loadConfiguration(file);
    }

    private String getString(@NotNull String path) {
        return yaml.getString(path);
    }

    public Language getLanguage() {
        return Language.fromCode(yaml.getString("language", "en_US"));
    }

    public String getMySQLHost() {
        return getString("mysql.host");
    }

    public String getMySQLDatabase() {
        return getString("mysql.database");
    }

    public String getMySQLUsername() {
        return getString("mysql.username");
    }

    public String getMySQLPassword() {
        return yaml.getString("mysql.password", "");
    }
}
