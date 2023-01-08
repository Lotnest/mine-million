package dev.lotnest.minemillion.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import dev.lotnest.minemillion.MineMillionPlugin;
import dev.lotnest.minemillion.file.ConfigHandler;
import dev.lotnest.minemillion.language.LanguageProvider;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;

@RequiredArgsConstructor
public class MySQLConnectionHolder implements BaseDAO {

    private final MineMillionPlugin plugin;
    private HikariDataSource hikariDataSource;

    public void connect() {
        LanguageProvider languageProvider = plugin.getLanguageProvider();

        try {
            HikariConfig hikariConfig = new HikariConfig();
            ConfigHandler configHandler = plugin.getConfigHandler();

            plugin.getLogger().info(languageProvider.get("database.connecting"));

            hikariConfig.setDriverClassName("com.mysql.jdbc.Driver");
            hikariConfig.setJdbcUrl("jdbc:mysql://" + configHandler.getMySQLHost() + "/" + configHandler.getMySQLDatabase());
            hikariConfig.setUsername(configHandler.getMySQLUsername());
            hikariConfig.setPassword(configHandler.getMySQLPassword());
            hikariConfig.setMinimumIdle(5);
            hikariConfig.setMaximumPoolSize(50);
            hikariConfig.setMaxLifetime(1800000);
            hikariConfig.setConnectionTimeout(30000);
            hikariConfig.setIdleTimeout(600000);
            hikariConfig.setLeakDetectionThreshold(60000);
            hikariConfig.setAutoCommit(true);
            hikariConfig.setPoolName("MineMillion-Pool");

            hikariDataSource = new HikariDataSource(hikariConfig);

            plugin.getLogger().info(languageProvider.get("database.connected"));
        } catch (Exception exception) {
            plugin.getLogger().log(Level.SEVERE, languageProvider.get("database.connectionFailed"), exception);
            plugin.getPluginLoader().disablePlugin(plugin);
        }
    }

    public boolean isInitialized() {
        return hikariDataSource != null;
    }

    public void disconnect() {
        if (isInitialized()) {
            hikariDataSource.close();
        }
    }

    public Connection getConnection() {
        try {
            return hikariDataSource.getConnection();
        } catch (SQLException exception) {
            plugin.getLogger().log(Level.SEVERE, plugin.getLanguageProvider().get("database.connectionFailed"), exception);
            return null;
        }
    }
}
