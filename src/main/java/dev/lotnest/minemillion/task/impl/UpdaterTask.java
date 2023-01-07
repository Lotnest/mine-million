package dev.lotnest.minemillion.task.impl;

import dev.lotnest.minemillion.MineMillionPlugin;
import dev.lotnest.minemillion.language.LanguageProvider;
import dev.lotnest.minemillion.task.ScheduledMineMillionTask;
import dev.lotnest.minemillion.util.VersionUtil;
import dev.lotnest.minemillion.util.exception.UpdateFailedException;
import org.apache.commons.lang3.StringUtils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class UpdaterTask extends ScheduledMineMillionTask {

    private static final String SPIGOT_API_ENDPOINT = "https://api.spigotmc.org/legacy/update.php?resource=%s&version=%s";
    private static final String SPIGOT_RESOURCES_ENDPOINT = "https://www.spigotmc.org/resources/";
    private static final String SPIGOT_RESOURCE_ID = "19254"; //TODO REPLACE WITH ACTUAL SPIGOT RESOURCE ID

    private final HttpClient httpClient;
    private final LanguageProvider languageProvider;

    public UpdaterTask(MineMillionPlugin plugin) {
        super(plugin, 0L, 60 * 60 * 20L, true);
        httpClient = HttpClient.newHttpClient();
        languageProvider = plugin.getLanguageProvider();
    }

    @Override
    public void run() {
        try {
            String latestVersion = checkForUpdate().get(20L, TimeUnit.SECONDS);
            if (StringUtils.isBlank(latestVersion)) {
                plugin.getLogger().info(languageProvider.get("general.updateFailed"));
                return;
            }

            if (VersionUtil.isTheSameVersion(latestVersion)) {
                plugin.getLogger().info(languageProvider.get("general.updateNotAvailable"));
                return;
            }

            if (VersionUtil.isNewerVersion(latestVersion)) {
                plugin.getLogger().info(
                        languageProvider.get("general.updateAvailable", latestVersion,
                                SPIGOT_RESOURCES_ENDPOINT + SPIGOT_RESOURCE_ID)
                );
            }
        } catch (ExecutionException | TimeoutException exception) {
            throw new UpdateFailedException(languageProvider.get("general.updateFailed"), exception);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }
    }

    private CompletableFuture<String> checkForUpdate() {
        plugin.getLogger().info(languageProvider.get("general.checkingForUpdates"));

        String url = String.format(SPIGOT_API_ENDPOINT, SPIGOT_RESOURCE_ID, VersionUtil.getVersion());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body);
    }
}
