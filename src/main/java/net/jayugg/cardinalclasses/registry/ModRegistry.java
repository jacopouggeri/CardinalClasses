package net.jayugg.cardinalclasses.registry;

import java.util.HashMap;
import java.util.Map;

public class ModRegistry<T> {
    private final Map<String, T> registry = new HashMap<>();
    public void register(String id, T item) {
        registry.put(id, item);
    }
    public T get(String id) {
        return registry.get(id);
    }
    public int size() {
        return registry.size();
    }
    public Map<String, T> getRegistry() {
        return registry;
    }
}