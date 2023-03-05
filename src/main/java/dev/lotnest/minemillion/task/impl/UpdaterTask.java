package dev.lotnest.minemillion.task.impl;

import dev.lotnest.minemillion.MineMillionPlugin;
import dev.lotnest.minemillion.task.ScheduledMineMillionTask;
import dev.lotnest.minemillion.util.LogUtil;
import dev.lotnest.minemillion.util.VersionUtil;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

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

    public UpdaterTask(@NotNull MineMillionPlugin plugin) {
        super(plugin, 0L, 60 * 60 * 20L, true);
        httpClient = HttpClient.newHttpClient();
    }

    @Override
    public void run() {
        try {
            String latestVersion = checkForUpdate().get(20L, TimeUnit.SECONDS);
            if (StringUtils.isBlank(latestVersion)) {
                LogUtil.warning("general.updateFailed");
                return;
            }

            if (VersionUtil.isTheSameVersion(latestVersion)) {
                LogUtil.info("general.updateNotAvailable");
                return;
            }

            if (VersionUtil.isNewerVersion(latestVersion)) {
                LogUtil.info("general.updateAvailable", latestVersion,
                        SPIGOT_RESOURCES_ENDPOINT + SPIGOT_RESOURCE_ID);
            }
        } catch (ExecutionException | TimeoutException exception) {
            LogUtil.warning("general.updateFailed", exception);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }
    }

    private CompletableFuture<String> checkForUpdate() {
        LogUtil.info("general.checkingForUpdates");

        String url = String.format(SPIGOT_API_ENDPOINT, SPIGOT_RESOURCE_ID, VersionUtil.getVersion());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body);
    }
}
