package net.jayugg.leanclass.registry;

import net.jayugg.leanclass.modules.PlayerClass;

import static net.jayugg.leanclass.LeanClass.LOGGER;

public class PlayerClassRegistry {
    public static final Registry<PlayerClass> CLASSES = new Registry<>();

    public static PlayerClass registerClass(PlayerClass playerClass) {
        CLASSES.register(playerClass.getId(), playerClass);
        LOGGER.info("Registered class: " + playerClass.getId());
        return playerClass;
    }
    public static PlayerClass getPlayerClass(String id) {
        return CLASSES.get(id);
    }
}

