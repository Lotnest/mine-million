package dev.lotnest.minemillion.file;

import dev.lotnest.minemillion.MineMillionPlugin;
import dev.lotnest.minemillion.language.Language;
import lombok.Getter;
import org.apache.commons.lang3.Validate;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

@Getter
public class ConfigHandler {

    private final MineMillionPlugin plugin;
    private final File file;
    private final YamlConfiguration yaml;

    public ConfigHandler(MineMillionPlugin plugin) {
        this.plugin = Validate.notNull(plugin, "Plugin can't be null");

        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        this.file = new File(plugin.getDataFolder(), "config.yml");
        if (!file.exists()) {
            plugin.saveResource("config.yml", false);
        }

        yaml = YamlConfiguration.loadConfiguration(file);
    }

    public Language getLanguage() {
        return Language.fromCode(yaml.getString("language", "en_US"));
    }
}
