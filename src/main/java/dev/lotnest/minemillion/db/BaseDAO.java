package dev.lotnest.minemillion.db;

import dev.lotnest.minemillion.MineMillionPlugin;
import dev.lotnest.minemillion.language.LanguageProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.logging.Level;

public interface BaseDAO {

    MineMillionPlugin plugin = MineMillionPlugin.getInstance();
    LanguageProvider languageProvider = plugin.getLanguageProvider();
    ExecutorService executorService = Executors.newFixedThreadPool(2);
    MySQLConnectionHolder connectionHolder = plugin.getConnectionHolder();

    default void executeQuery(String query, Object... args) {
        if (query == null || query.isEmpty()) {
            plugin.getLogger().warning(languageProvider.get("database.sqlQueryEmpty"));
            return;
        }

        executorService.submit(() -> {
            try (Connection connection = connectionHolder.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                for (int i = 0; i < args.length; i++) {
                    statement.setObject(i + 1, args[i]);
                }

                statement.execute();
            } catch (SQLException exception) {
                plugin.getLogger().log(Level.SEVERE, languageProvider.get("database.sqlQueryFailed", query), exception);
            }
        });
    }

    default void selectQuery(String query, Consumer<ResultSet> callback, Object... args) {
        if (query == null || query.isEmpty()) {
            plugin.getLogger().warning(languageProvider.get("database.sqlQueryEmpty"));
            return;
        }

        executorService.submit(() -> {
            try (Connection connection = connectionHolder.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                for (int i = 0; i < args.length; i++) {
                    statement.setObject(i + 1, args[i]);
                }

                callback.accept(statement.executeQuery());
            } catch (SQLException exception) {
                plugin.getLogger().log(Level.SEVERE, languageProvider.get("database.sqlQueryFailed", query), exception);
            }
        });
    }
}
