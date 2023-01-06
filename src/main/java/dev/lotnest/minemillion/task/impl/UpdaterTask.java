package dev.lotnest.minemillion.task.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.lotnest.minemillion.MineMillionPlugin;
import dev.lotnest.minemillion.language.LanguageProvider;
import dev.lotnest.minemillion.task.ScheduledMineMillionTask;
import org.apache.commons.lang3.StringUtils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class UpdaterTask extends ScheduledMineMillionTask {

    private static final String SPIGOT_API_ENDPOINT = "https://api.spigotmc.org/legacy/update.php?resource=%s&version=%s";
    private static final String SPIGOT_RESOURCES_ENDPOINT = "https://www.spigotmc.org/resources/";
    private static final String SPIGOT_RESOURCE_ID = "";

    private final String currentVersion;
    private final HttpClient httpClient;
    private final LanguageProvider languageProvider;

    public UpdaterTask(MineMillionPlugin plugin) {
        super(plugin, 60 * 60 * 20L, 0L, true);
        currentVersion = plugin.getDescription().getVersion();
        httpClient = HttpClient.newHttpClient();
        languageProvider = plugin.getLanguageProvider();
    }

    @Override
    public void run() {
        checkForUpdate().thenAccept(versionJsonResponse -> {
            if (versionJsonResponse == null) {
                return;
            }

            String latestVersion = versionJsonResponse.get("version").getAsString();
            if (StringUtils.equals(currentVersion, latestVersion)) {
                return;
            }

            if (latestVersion != null && isNewerVersion(latestVersion)) {
                plugin.getLogger().info(
                        languageProvider.get("general.updateAvailable", latestVersion,
                                SPIGOT_RESOURCES_ENDPOINT + SPIGOT_RESOURCE_ID + "/" + latestVersion)
                );
            }
        });
    }

    private CompletableFuture<JsonObject> checkForUpdate() {
        String url = String.format(SPIGOT_API_ENDPOINT, SPIGOT_RESOURCE_ID, currentVersion);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    String json = response.body();
                    return JsonParser.parseString(json).getAsJsonObject();
                });
    }

    private boolean isNewerVersion(String versionToCheck) {
        if (StringUtils.isBlank(versionToCheck)) {
            return false;
        }

        String[] versionParts1 = currentVersion.split("\\.");
        String[] versionParts2 = versionToCheck.split("\\.");

        for (int i = 0; i < versionParts1.length; i++) {
            int versionPart1 = Integer.parseInt(versionParts1[i]);
            int versionPart2 = Integer.parseInt(versionParts2[i]);

            if (versionPart1 > versionPart2) {
                return true;
            } else if (versionPart1 < versionPart2) {
                return false;
            }
        }

        return false;
    }
}
