package dev.lotnest.minemillion.db;

import dev.lotnest.minemillion.MineMillionPlugin;

public interface BaseDAO {

    default MySQLConnectionHolder getConnectionHolder() {
        return MineMillionPlugin.getInstance().getConnectionHolder();
    }
}
