package net.jayugg.leanclass.modules;

import net.jayugg.leanclass.advancement.ModCriteria;
import net.jayugg.leanclass.component.ModComponents;
import net.jayugg.leanclass.component.PlayerClassComponent;
import net.jayugg.leanclass.registry.PlayerClassRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.Optional;
import java.util.OptionalInt;

public class PlayerClassManager {
    public static boolean setPlayerClass(PlayerEntity player, String classId) {
        // Check if the class is registered
        PlayerClass playerClass = PlayerClassRegistry.getPlayerClass(classId);
        if (playerClass == null) {
            // The class is not registered, handle accordingly
            throw new IllegalArgumentException("Class with name " + classId + " is not registered!");
        }
        applyClassToPlayer(player, playerClass);
        return true;
    }

    private static void applyClassToPlayer(PlayerEntity player, PlayerClass playerClass) {
        if (player.getWorld().isClient) {
            return;
        }
        PlayerClassComponent playerClassComponent = ModComponents.CLASS_COMPONENT.get(player);
        playerClassComponent.setClass(playerClass.getId());
        player.sendMessage(Text.literal("Your class has been set to " + playerClass.getId()), false);
        ModCriteria.OBTAIN_CLASS.trigger((ServerPlayerEntity) player, playerClass);
    }

    public static boolean skillUp(PlayerEntity player, SkillSlot skillSlot) {
        PlayerClassComponent playerClassComponent = ModComponents.CLASS_COMPONENT.get(player);
        return playerClassComponent.skillUp(skillSlot);
    }

    public static boolean skillDown(PlayerEntity player, SkillSlot skillSlot) {
        PlayerClassComponent playerClassComponent = ModComponents.CLASS_COMPONENT.get(player);
        return playerClassComponent.skillDown(skillSlot);
    }

    public static int getSkillLevel(PlayerEntity player, SkillSlot skillSlot) {
        PlayerClassComponent playerClassComponent = ModComponents.CLASS_COMPONENT.get(player);
        return playerClassComponent.getSkillLevel(skillSlot);
    }

    public static OptionalInt getSkillLevel(PlayerEntity player, PlayerSkill skill) {
        for (SkillSlot skillSlot : SkillSlot.values()) {
            if (getClass(player).getSkills().get(skillSlot).equals(skill)) {
                return OptionalInt.of(getSkillLevel(player, skillSlot));
            }
        }
        return OptionalInt.empty();
    }

    public static boolean hasSkill(PlayerEntity player, PlayerSkill skill) {
        OptionalInt skillLevel = PlayerClassManager.getSkillLevel(player, skill);
        return (skillLevel.isPresent() && skillLevel.getAsInt() > 0);
    }

    public static boolean ascendPerk(PlayerEntity player, PerkSlot slot) {
        PlayerClassComponent playerClassComponent = ModComponents.CLASS_COMPONENT.get(player);
        return playerClassComponent.setAscendedPerk(slot);
    }

    public static boolean hasAscendedPerk(PlayerEntity player, PerkSlot slot) {
        PlayerClassComponent playerClassComponent = ModComponents.CLASS_COMPONENT.get(player);
        Optional<PerkSlot> ascendedPerk = playerClassComponent.getAscendedPerk();
        return ascendedPerk.isPresent() && ascendedPerk.get().equals(slot);
    }

    public static Optional<PerkSlot> getAscendedPerk(PlayerEntity player) {
        PlayerClassComponent playerClassComponent = ModComponents.CLASS_COMPONENT.get(player);
        return playerClassComponent.getAscendedPerk();
    }

    public static PlayerClass getClass(PlayerEntity player) {
        PlayerClassComponent playerClassComponent = ModComponents.CLASS_COMPONENT.get(player);
        return PlayerClassRegistry.getPlayerClass(playerClassComponent.getId());
    }

    public static boolean hasClass(PlayerEntity player, PlayerClass playerClass) {
        PlayerClassComponent playerClassComponent = ModComponents.CLASS_COMPONENT.get(player);
        return playerClassComponent.getId().equals(playerClass.getId());
    }

    public static PlayerSkill getSkillInSlot(PlayerEntity player, SkillSlot skillSlot) {
        return getClass(player).getSkills().get(skillSlot);
    }
}
