package dev.lotnest.minemillion.util.exception;

import org.jetbrains.annotations.NotNull;

public class FileFailedToLoadException extends RuntimeException {

    public FileFailedToLoadException(@NotNull String fileName, @NotNull Throwable throwable) {
        super("File " + fileName + " failed to load", throwable);
    }
}
