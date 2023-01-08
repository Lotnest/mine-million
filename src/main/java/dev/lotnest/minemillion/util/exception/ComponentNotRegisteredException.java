package dev.lotnest.minemillion.util.exception;

import org.jetbrains.annotations.NotNull;

public class ComponentNotRegisteredException extends RuntimeException {

    public ComponentNotRegisteredException(@NotNull String message) {
        super(message);
    }
}
