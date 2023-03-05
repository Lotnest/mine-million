package dev.lotnest.minemillion.util;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.RecordMapperProvider;
import org.jooq.RecordType;

public class Mappers {

    private Mappers() {
    }

    @RequiredArgsConstructor
    public static class LongMapper implements RecordMapperProvider {

        private final @NotNull String columnName;

        @Override
        public <R extends Record, E> @NotNull RecordMapper<R, E> provide(@NotNull RecordType<R> recordType, @NotNull Class<? extends E> type) {
            if (type == Long.class) {
                return result -> (E) Long.valueOf(result.get(columnName, Long.class));
            }
            return result -> null;
        }
    }
}
