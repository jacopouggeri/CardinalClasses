package net.jayugg.leanclass.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.jayugg.leanclass.item.ModItemGroup;
import net.jayugg.leanclass.item.ModItems;
import net.jayugg.leanclass.item.custom.ClassPotionItem;
import net.jayugg.leanclass.base.PlayerClass;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.Set;

import static net.jayugg.leanclass.LeanClass.LOGGER;
import static net.jayugg.leanclass.LeanClass.MOD_ID;

public class PlayerClassRegistry {
    public static final ModRegistry<PlayerClass> CLASSES = new ModRegistry<>();
    public static final ModRegistry<Item> CLASS_POTIONS = new ModRegistry<>();

    public static PlayerClass registerClass(PlayerClass playerClass) {
        if (CLASSES.get(playerClass.getId()) != null) {
            throw new IllegalArgumentException("Class with name " + playerClass.getId() + " already registered!");
        }
        CLASSES.register(playerClass.getId(), playerClass);
        ModItems.addToItemGroup(ModItemGroup.LEAN_GROUP, registerClassPotion(playerClass));
        LOGGER.info("Registered class: " + playerClass.getId());
        return playerClass;
    }

    private static Item registerClassPotion(PlayerClass playerClass) {
        Item potionItem = Registry.register(Registries.ITEM, new Identifier(MOD_ID, playerClass.getId() + "_potion"), new ClassPotionItem(new FabricItemSettings(), playerClass));
        CLASS_POTIONS.register(playerClass.getId(), potionItem);
        return potionItem;
    }

    public static PlayerClass getPlayerClass(String id) {
        return CLASSES.get(id);
    }

    public static Set<String> getClassIds() {
        return CLASSES.getRegistry().keySet();
    }
}

