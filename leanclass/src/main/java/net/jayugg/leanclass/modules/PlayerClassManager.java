package net.jayugg.leanclass.modules;

import net.jayugg.leanclass.component.ModComponents;
import net.jayugg.leanclass.component.PlayerClassComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class PlayerClassManager {
    public static boolean setPlayerClass(PlayerEntity player, String className) {
        // Check if the class is registered
        PlayerClass playerClass = PlayerClassRegistry.getClassByName(className);
        if (playerClass == null) {
            // The class is not registered, handle accordingly
            System.out.println("Class " + className + " is not registered.");
            return false;
        }

        applyClassToPlayer(player, playerClass);

        return true;
    }

    private static void applyClassToPlayer(PlayerEntity player, PlayerClass playerClass) {
        PlayerClassComponent classComponent = ModComponents.CLASS_COMPONENT.get(player);
        classComponent.setClass(playerClass.getId());
        player.sendMessage(Text.literal("Your class has been set to " + playerClass.getId()), false);
    }

    public static boolean skillUp(PlayerEntity player, PlayerSkill playerSkill) {
        PlayerClassComponent playerClassComponent = ModComponents.CLASS_COMPONENT.get(player);
        return playerClassComponent.skillUp(playerSkill.getId());
    }

    public static boolean skillDown(PlayerEntity player, PlayerSkill playerSkill) {
        PlayerClassComponent playerClassComponent = ModComponents.CLASS_COMPONENT.get(player);
        return playerClassComponent.skillDown(playerSkill.getId());
    }

    public static int getSkillLevel(PlayerEntity player, PlayerSkill playerSkill) {
        PlayerClassComponent playerClassComponent = ModComponents.CLASS_COMPONENT.get(player);
        return playerClassComponent.getSkillLevel(playerSkill.getId());
    }

    public static boolean ascendPerk(PlayerEntity player, PerkSlot slot) {
        PlayerClassComponent playerClassComponent = ModComponents.CLASS_COMPONENT.get(player);
        return playerClassComponent.setAscendedPerk(slot);
    }

    public static boolean hasAscendedPerk(PlayerEntity player, PerkSlot slot) {
        PlayerClassComponent playerClassComponent = ModComponents.CLASS_COMPONENT.get(player);
        return slot.getValue() == playerClassComponent.getAscendedPerk();
    }
}
