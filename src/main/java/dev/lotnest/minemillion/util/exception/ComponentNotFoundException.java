package dev.lotnest.minemillion.util.exception;

public class ComponentNotFoundException extends RuntimeException {

    public ComponentNotFoundException(String componentName) {
        super("Component not found: " + componentName);
    }
}
