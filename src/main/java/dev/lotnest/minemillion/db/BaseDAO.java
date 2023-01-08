package dev.lotnest.minemillion.db;

import dev.lotnest.minemillion.MineMillionPlugin;
import dev.lotnest.minemillion.util.LoggerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public interface BaseDAO {

    MineMillionPlugin plugin = MineMillionPlugin.getInstance();
    ExecutorService executorService = Executors.newFixedThreadPool(2);
    MySQLConnectionHolder connectionHolder = plugin.getConnectionHolder();

    default void executeQuery(@NotNull String query, @Nullable Object... args) {
        executorService.submit(() -> {
            try (Connection connection = connectionHolder.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                for (int i = 0; i < args.length; i++) {
                    statement.setObject(i + 1, args[i]);
                }

                statement.execute();
            } catch (SQLException exception) {
                LoggerUtil.severe("database.sqlQueryFailed", exception, query);
            }
        });
    }

    default void selectQuery(@NotNull String query, @NotNull Consumer<ResultSet> callback, @Nullable Object... args) {
        executorService.submit(() -> {
            try (Connection connection = connectionHolder.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                for (int i = 0; i < args.length; i++) {
                    statement.setObject(i + 1, args[i]);
                }

                callback.accept(statement.executeQuery());
            } catch (SQLException exception) {
                LoggerUtil.severe("database.sqlQueryFailed", exception, query);
            }
        });
    }
}
