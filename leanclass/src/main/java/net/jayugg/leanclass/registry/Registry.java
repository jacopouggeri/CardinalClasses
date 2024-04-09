package net.jayugg.leanclass.registry;

import java.util.HashMap;
import java.util.Map;

public class Registry<T> {
    private final Map<String, T> registry = new HashMap<>();

    public void register(String id, T item) {
        registry.put(id, item);
    }

    public T get(String id) {
        return registry.get(id);
    }
}