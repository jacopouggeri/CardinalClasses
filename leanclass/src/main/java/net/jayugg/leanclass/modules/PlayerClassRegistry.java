package net.jayugg.leanclass.modules;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PlayerClassRegistry {
    private static final Map<String, PlayerClass> REGISTERED_CLASSES = new HashMap<>();

    public static void registerClass(PlayerClass playerClass) {
        REGISTERED_CLASSES.put(playerClass.getId(), playerClass);
    }

    public static PlayerClass getClassByName(String name) {
        return REGISTERED_CLASSES.get(name);
    }

    public static Collection<PlayerClass> getAllClasses() {
        return REGISTERED_CLASSES.values();
    }
}

