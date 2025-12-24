package net.luxmcje.loader.api.event;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class LuxEvent<T> {
    private final List<T> handlers = new ArrayList<>();

    public void register(T handler) {
        handlers.add(handler);
    }

    public List<T> getHandlers() {
        return handlers;
    }
}
