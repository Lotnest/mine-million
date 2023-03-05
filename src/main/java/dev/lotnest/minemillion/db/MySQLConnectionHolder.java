package dev.lotnest.minemillion.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import dev.lotnest.minemillion.MineMillionPlugin;
import dev.lotnest.minemillion.file.ConfigHandler;
import dev.lotnest.minemillion.util.LogUtil;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.SQLException;

@RequiredArgsConstructor
public class MySQLConnectionHolder {

    private final MineMillionPlugin plugin;
    private HikariDataSource hikariDataSource;
    private DSLContext dslContext;

    public void connect() {
        try {
            LogUtil.info("database.connecting");

            HikariConfig hikariConfig = new HikariConfig();
            ConfigHandler configHandler = plugin.getConfigHandler();

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

            dslContext = DSL.using(hikariDataSource, SQLDialect.MYSQL);

            LogUtil.info("database.connected");
        } catch (Exception exception) {
            LogUtil.severe("database.connectionFailed", exception);
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
            LogUtil.severe("database.connectionFailed", exception);
            return null;
        }
    }

    public DSLContext getDSLContext() {
        return dslContext;
    }
}
