package net.jayugg.leanclass.util;

import net.jayugg.leanclass.advancement.ModCriteria;
import net.jayugg.leanclass.base.*;
import net.jayugg.leanclass.component.ModComponents;
import net.jayugg.leanclass.component.PlayerClassComponent;
import net.jayugg.leanclass.item.ModItems;
import net.jayugg.leanclass.registry.PlayerClassRegistry;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.ServerAdvancementLoader;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.Optional;

import static net.jayugg.leanclass.LeanClass.LOGGER;
import static net.jayugg.leanclass.LeanClass.MOD_ID;


public class PlayerClassManager {
    public static boolean setPlayerClass(PlayerEntity player, String classId) {
        if (!(player instanceof ServerPlayerEntity)) {
            return false;
        }
        // Check if the class is registered
        PlayerClass playerClass = PlayerClassRegistry.getPlayerClass(classId);
        if (playerClass == null) {
            // The class is not registered, handle accordingly
            throw new IllegalArgumentException("Class with name " + classId + " is not registered!");
        }
        revokeClassAdvancement((ServerPlayerEntity) player, PlayerClassManager.getClass(player).getId());
        resetSkillPoints(player);
        applyClassToPlayer(player, playerClass);
        return true;
    }

    private static void revokeAdvancementAndChildren(ServerPlayerEntity player, Advancement advancement) {
        PlayerAdvancementTracker advancementTracker = player.getAdvancementTracker();
        advancement.getCriteria().forEach((id, criterion) -> {
            advancementTracker.revokeCriterion(advancement, id);
            advancement.getChildren().forEach((child) -> revokeAdvancementAndChildren(player, child));
        });
    }

    private static void revokeClassAdvancement(ServerPlayerEntity player, String classId) {
        if (player.getServer().getAdvancementLoader() == null) {
            return;
        }
        ServerAdvancementLoader advancementLoader = player.getServer().getAdvancementLoader();
        String criterionId = "class_" + classId;
        advancementLoader.getAdvancements().forEach((advancement) -> {
            if (advancement.getId().toString().startsWith("minecraft:" + MOD_ID)) {
                if (advancement.getCriteria().containsKey(criterionId)) {
                    revokeAdvancementAndChildren(player, advancement);
                    LOGGER.info("Revoked advancement " + advancement.getId());
                }
            }
        });
    }

    private static void applyClassToPlayer(PlayerEntity player, PlayerClass playerClass) {
        if (player.getWorld().isClient) {
            return;
        }
        PlayerClassComponent playerClassComponent = ModComponents.CLASS_COMPONENT.get(player);
        playerClassComponent.setClass(playerClass.getId());
        player.sendMessage(Text.literal("Your class has been set to " + playerClass.getId()), false);
        ModCriteria.OBTAIN_CLASS.trigger((ServerPlayerEntity) player, playerClass);
        ModComponents.CLASS_COMPONENT.sync(player);
    }

    public static boolean skillUp(PlayerEntity player, AbilityType type, SkillSlot skillSlot) {
        PlayerClassComponent playerClassComponent = ModComponents.CLASS_COMPONENT.get(player);
        boolean success = playerClassComponent.skillUp(type, skillSlot);
        if (success) {
            PlayerClass playerClass = getClass(player);
            ModCriteria.OBTAIN_SKILL.trigger((ServerPlayerEntity) player, playerClass, playerClass.getSkills(type).get(skillSlot), getSkillLevel(player, type, skillSlot));
            ModComponents.CLASS_COMPONENT.sync(player);
        }
        return success;
    }

    public static boolean skillDown(PlayerEntity player, AbilityType type, SkillSlot skillSlot) {
        PlayerClassComponent playerClassComponent = ModComponents.CLASS_COMPONENT.get(player);
        ModComponents.CLASS_COMPONENT.sync(player);
        return playerClassComponent.skillDown(type, skillSlot);
    }

    public static int getSkillLevel(PlayerEntity player, AbilityType type, SkillSlot skillSlot) {
        PlayerClassComponent playerClassComponent = ModComponents.CLASS_COMPONENT.get(player);
        return playerClassComponent.getSkillLevel(type, skillSlot);
    }

    public static int getSkillLevel(PlayerEntity player, PlayerSkill skill) {
        AbilityType type = skill.getType();
        for (SkillSlot skillSlot : SkillSlot.values()) {
            if (getClass(player).getSkills(type).get(skillSlot).equals(skill)) {
                return getSkillLevel(player, type, skillSlot);
            }
        }
        throw new IllegalArgumentException("Skill " + skill.getId() + " not found in player's class");
    }

    public static boolean hasSkill(PlayerEntity player, PlayerSkill skill) {
        int skillLevel = PlayerClassManager.getSkillLevel(player, skill);
        return skillLevel > 0;
    }

    public static boolean ascendPerk(PlayerEntity player, PerkSlot slot) {
        PlayerClassComponent playerClassComponent = ModComponents.CLASS_COMPONENT.get(player);
        ModCriteria.ASCEND_PERK.trigger((ServerPlayerEntity) player, PlayerClassManager.getClass(player).getPerks().get(slot), true);
        ModComponents.CLASS_COMPONENT.sync(player);
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

    public static PlayerSkill getSkillInSlot(PlayerEntity player, SkillSlot skillSlot, AbilityType abilityType) {
        return getClass(player).getSkills(abilityType).get(skillSlot);
    }

    public static int getTotalSkillPoints(PlayerEntity player) {
        PlayerClassComponent playerClassComponent = ModComponents.CLASS_COMPONENT.get(player);
        int activeLevels = playerClassComponent.getSkillLevels(AbilityType.ACTIVE).values().stream().mapToInt(Integer::intValue).sum();
        int passiveLevels = playerClassComponent.getSkillLevels(AbilityType.PASSIVE).values().stream().mapToInt(Integer::intValue).sum();
        return activeLevels + passiveLevels;
    }

    public static int resetSkillPoints(PlayerEntity player) {
        PlayerClassComponent playerClassComponent = ModComponents.CLASS_COMPONENT.get(player);
        int totalSkillPoints = getTotalSkillPoints(player);
        playerClassComponent.resetSkills();
        refundSkillShards(player, totalSkillPoints);
        ModComponents.CLASS_COMPONENT.sync(player);
        return totalSkillPoints;
    }

    public static void refundSkillShards(PlayerEntity player, int points) {
        if (!(player instanceof ServerPlayerEntity)) {
            return;
        }
        ItemStack itemStack = new ItemStack(ModItems.SKILL_SHARD, points);
        if (!player.getInventory().insertStack(itemStack)) {
            player.dropItem(itemStack, false);
        }
    }

    public static void useActiveSkill(PlayerEntity player, SkillSlot skillSlot) {
        ActiveSkill playerSkill = (ActiveSkill) PlayerClassManager.getClass(player).getActiveSkills().get(skillSlot);
        playerSkill.use(player);
        ModComponents.ACTIVE_SKILLS_COMPONENT.sync(player);
    }
}
