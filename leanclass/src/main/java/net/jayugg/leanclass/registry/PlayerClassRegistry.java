package net.jayugg.leanclass.registry;

import net.jayugg.leanclass.modules.PlayerClass;

import java.util.Set;

import static net.jayugg.leanclass.LeanClass.LOGGER;

public class PlayerClassRegistry {
    public static final Registry<PlayerClass> CLASSES = new Registry<>();

    public static PlayerClass registerClass(PlayerClass playerClass) {
        if (CLASSES.get(playerClass.getId()) != null) {
            throw new IllegalArgumentException("Class with name " + playerClass.getId() + " already registered!");
        }
        CLASSES.register(playerClass.getId(), playerClass);
        LOGGER.info("Registered class: " + playerClass.getId());
        return playerClass;
    }
    public static PlayerClass getPlayerClass(String id) {
        return CLASSES.get(id);
    }

    public static Set<String> getClassIds() {
        return CLASSES.getRegistry().keySet();
    }
}

