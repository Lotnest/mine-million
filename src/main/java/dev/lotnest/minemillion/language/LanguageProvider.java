package dev.lotnest.minemillion.language;

import com.google.common.collect.Maps;
import dev.lotnest.minemillion.MineMillionPlugin;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

@Getter
@Setter
public class LanguageProvider {

    private final MineMillionPlugin plugin;
    private Map<String, String> languageMap;
    private Language language;

    public LanguageProvider(MineMillionPlugin plugin, Language language) {
        this.plugin = plugin;
        this.language = language == null ? Language.ENGLISH_US : language;
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
            throw new RuntimeException("Could not load " + languageFileName, exception);
        }
    }

    public String get(String key, String... placeholders) {
        if (StringUtils.isBlank(key)) {
            return "ERROR_NO_KEY_PROVIDED";
        }

        String message = languageMap.getOrDefault(key, "ERROR_NO_MESSAGE_VALUE_FOUND");

        if (placeholders != null && placeholders.length > 0) {
            for (int i = 0; i < placeholders.length; i++) {
                message = message.replace("{" + i + "}", placeholders[i]);
            }
        }

        return message;
    }
}
