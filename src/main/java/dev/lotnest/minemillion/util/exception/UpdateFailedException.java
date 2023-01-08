package dev.lotnest.minemillion.util.exception;

import org.jetbrains.annotations.NotNull;

public class UpdateFailedException extends RuntimeException {

    public UpdateFailedException(@NotNull String message, @NotNull Throwable cause) {
        super(message, cause);
    }
}
