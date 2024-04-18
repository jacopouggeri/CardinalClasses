package net.jayugg.cardinalclasses.registry;

import net.jayugg.cardinalclasses.effect.ClassGrantEffect;
import net.jayugg.cardinalclasses.core.PlayerClass;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import javax.annotation.Nullable;
import java.util.Set;

import static net.jayugg.cardinalclasses.CardinalClasses.LOGGER;
import static net.jayugg.cardinalclasses.CardinalClasses.MOD_ID;

public class PlayerClassRegistry {
    public static final ModRegistry<PlayerClass> CLASSES = new ModRegistry<>();
    public static final ModRegistry<ClassGrantEffect> CLASS_EFFECTS = new ModRegistry<>();
    public static final ModRegistry<Potion> CLASS_POTIONS = new ModRegistry<>();

    public static PlayerClass registerClass(PlayerClass playerClass) {
        if (CLASSES.get(playerClass.getId()) != null) {
            throw new IllegalArgumentException("Class with name " + playerClass.getId() + " already registered!");
        }
        CLASSES.register(playerClass.getId(), playerClass);
        ClassGrantEffect classEffect = registerClassGrantEffect(playerClass);
        registerClassPotion(playerClass, classEffect);
        LOGGER.info("Registered class: {}", playerClass.getId());
        return playerClass;
    }

    private static ClassGrantEffect registerClassGrantEffect(PlayerClass playerClass) {
        ClassGrantEffect classEffect = Registry.register(Registries.STATUS_EFFECT, new Identifier(MOD_ID, "grant_" + playerClass.getId()), new ClassGrantEffect(playerClass));
        CLASS_EFFECTS.register(playerClass.getId(), classEffect);
        return classEffect;
    }

    private static void registerClassPotion(PlayerClass playerClass, ClassGrantEffect classEffect) {
        Potion classPotion = new Potion(new StatusEffectInstance(classEffect, 3600));
        Registry.register(Registries.POTION, new Identifier(MOD_ID, "grant_" + playerClass.getId()), classPotion);
        CLASS_POTIONS.register(playerClass.getId(), classPotion);
    }

    @Nullable
    public static PlayerClass getPlayerClass(String id) {
        return CLASSES.get(id);
    }

    public static Set<String> getClassIds() {
        return CLASSES.getRegistry().keySet();
    }
}

