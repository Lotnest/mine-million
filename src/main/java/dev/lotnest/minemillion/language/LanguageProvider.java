package dev.lotnest.minemillion.language;

import com.google.common.collect.Maps;
import dev.lotnest.minemillion.MineMillionPlugin;
import dev.lotnest.minemillion.util.ColorConstants;
import dev.lotnest.minemillion.util.exception.FileFailedToLoadException;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

@Getter
@Setter
public class LanguageProvider {

    private final MineMillionPlugin plugin;
    private Map<String, String> languageMap;
    private Language language;

    public LanguageProvider(@NotNull MineMillionPlugin plugin, @NotNull Language language) {
        this.plugin = plugin;
        this.language = language;

        loadLanguageMapFromCurrentLanguageFile();
    }

    public void loadLanguageMapFromCurrentLanguageFile() {
        Properties properties = new Properties();
        String languageFileName = "messages_" + language.getCode() + ".properties";

        try {
            properties.load(plugin.getResource(languageFileName));
            languageMap = Maps.newHashMap();
            properties.forEach((key, value) -> languageMap.put(key.toString(), value.toString()));
        } catch (IOException exception) {
            throw new FileFailedToLoadException(languageFileName, exception);
        }
    }

    public String get(String key, String... placeholders) {
        if (StringUtils.isBlank(key)) {
            return "ERROR_NO_KEY_PROVIDED";
        }

        String message = languageMap.getOrDefault(key, ColorConstants.RED + "ERROR_NO_MESSAGE_VALUE_FOUND: " + key);

        if (placeholders != null && placeholders.length > 0) {
            for (int i = 0; i < placeholders.length; i++) {
                message = message.replace("{" + i + "}", placeholders[i]);
            }
        }

        return message;
    }

    public void setLanguage(@NotNull Language language) {
        this.language = language;
        plugin.getConfigHandler().setLanguage(language);
        loadLanguageMapFromCurrentLanguageFile();
        plugin.getCommandRegistry().reloadCommandReplacements();
    }
}
