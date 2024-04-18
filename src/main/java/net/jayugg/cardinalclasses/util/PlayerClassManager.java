package net.jayugg.cardinalclasses.util;

import net.jayugg.cardinalclasses.advancement.ModCriteria;
import net.jayugg.cardinalclasses.core.*;
import net.jayugg.cardinalclasses.component.ModComponents;
import net.jayugg.cardinalclasses.component.PlayerClassComponent;
import net.jayugg.cardinalclasses.item.ModItems;
import net.jayugg.cardinalclasses.registry.PlayerClassRegistry;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.ServerAdvancementLoader;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import javax.annotation.Nullable;
import java.util.Optional;

import static net.jayugg.cardinalclasses.CardinalClasses.LOGGER;
import static net.jayugg.cardinalclasses.CardinalClasses.MOD_ID;


public class PlayerClassManager {
    public static void setPlayerClass(PlayerEntity player, String classId) {
        if (!(player instanceof ServerPlayerEntity)) {
            return;
        }
        // Check if the class is registered
        PlayerClass playerClass = PlayerClassRegistry.getPlayerClass(classId);
        if (playerClass == null) {
            // The class is not registered, handle accordingly
            throw new IllegalArgumentException("Class with name " + classId + " is not registered!");
        }
        resetPlayerClass(player);
        applyClassToPlayer(player, playerClass);
    }

    public static void resetPlayerClass(PlayerEntity player) {
        PlayerClass playerClass = getClass(player);
        if (playerClass == null) {
            return;
        }
        revokeClassAdvancement((ServerPlayerEntity) player, playerClass.getId());
        resetSkillPoints(player);
        resetPerkSlot(player);
        PlayerClassComponent playerClassComponent = ModComponents.CLASS_COMPONENT.get(player);
        playerClassComponent.setClass(null);
    }

    private static void revokeAdvancementAndChildren(ServerPlayerEntity player, Advancement advancement) {
        PlayerAdvancementTracker advancementTracker = player.getAdvancementTracker();
        advancement.getCriteria().forEach((id, criterion) -> {
            advancementTracker.revokeCriterion(advancement, id);
            advancement.getChildren().forEach((child) -> revokeAdvancementAndChildren(player, child));
        });
    }

    private static void revokeClassAdvancement(ServerPlayerEntity player, String classId) {
        if (player.getServer() == null || player.getServer().getAdvancementLoader() == null) {
            return;
        }
        ServerAdvancementLoader advancementLoader = player.getServer().getAdvancementLoader();
        String criterionId = "class_" + classId;
        advancementLoader.getAdvancements().forEach((advancement) -> {
            if (advancement.getId().toString().startsWith("minecraft:" + MOD_ID)) {
                if (advancement.getCriteria().containsKey(criterionId)) {
                    revokeAdvancementAndChildren(player, advancement);
                    LOGGER.info("Revoked advancement {}", advancement.getId());
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
            if (playerClass == null) {
                return false;
            }
            ModCriteria.OBTAIN_SKILL.trigger((ServerPlayerEntity) player, playerClass, playerClass.getSkills(type).get(skillSlot), getSkillLevel(player, type, skillSlot));
            ModComponents.CLASS_COMPONENT.sync(player);
        }
        return success;
    }

    public static int getSkillLevel(PlayerEntity player, AbilityType type, SkillSlot skillSlot) {
        PlayerClassComponent playerClassComponent = ModComponents.CLASS_COMPONENT.get(player);
        return playerClassComponent.getSkillLevel(type, skillSlot);
    }

    public static int getSkillLevel(PlayerEntity player, PlayerSkill skill) {
        PlayerClass playerClass = getClass(player);
        if (playerClass != null) {
            AbilityType type = skill.getType();
            for (SkillSlot skillSlot : SkillSlot.values()) {
                if (playerClass.getSkills(type).get(skillSlot).equals(skill)) {
                    return getSkillLevel(player, type, skillSlot);
                }
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
        boolean success = playerClassComponent.setAscendedPerk(slot);
        if (success) {
            PlayerClass playerClass = getClass(player);
            if (playerClass == null) {
                return false;
            }
            ModCriteria.ASCEND_PERK.trigger((ServerPlayerEntity) player, playerClass, playerClass.getPerks().get(slot), true);
            ModComponents.CLASS_COMPONENT.sync(player);
        }
        return success;
    }

    public static boolean hasAscendedPerk(PlayerEntity player, PerkSlot slot) {
        PlayerClassComponent playerClassComponent = ModComponents.CLASS_COMPONENT.get(player);
        Optional<PerkSlot> ascendedPerk = playerClassComponent.getAscendedPerk();
        return ascendedPerk.isPresent() && ascendedPerk.get().equals(slot);
    }

    @Nullable
    public static PlayerClass getClass(PlayerEntity player) {
        PlayerClassComponent playerClassComponent = ModComponents.CLASS_COMPONENT.get(player);
        return PlayerClassRegistry.getPlayerClass(playerClassComponent.getId());
    }

    public static boolean hasClass(PlayerEntity player, PlayerClass playerClass) {
        PlayerClass playerClass2 = getClass(player);
        if (playerClass2 == null) {
            return false;
        }
        return playerClass2.getId().equals(playerClass.getId());
    }

    @Nullable
    public static PlayerSkill getSkillInSlot(PlayerEntity player, SkillSlot skillSlot, AbilityType abilityType) {
        PlayerClass playerClass = getClass(player);
        if (playerClass == null) {
            return null;
        }
        return playerClass.getSkills(abilityType).get(skillSlot);
    }

    public static int getTotalSkillPoints(PlayerEntity player) {
        PlayerClassComponent playerClassComponent = ModComponents.CLASS_COMPONENT.get(player);
        int activeLevels = playerClassComponent.getSkillLevels(AbilityType.ACTIVE).values().stream().mapToInt(Integer::intValue).sum();
        int passiveLevels = playerClassComponent.getSkillLevels(AbilityType.PASSIVE).values().stream().mapToInt(Integer::intValue).sum();
        return activeLevels + passiveLevels;
    }

    private static void resetPerkSlot(PlayerEntity player) {
        PlayerClassComponent playerClassComponent = ModComponents.CLASS_COMPONENT.get(player);
        playerClassComponent.resetAscendedPerk();
        ModComponents.CLASS_COMPONENT.sync(player);
    }

    private static void resetSkillPoints(PlayerEntity player) {
        PlayerClassComponent playerClassComponent = ModComponents.CLASS_COMPONENT.get(player);
        int totalSkillPoints = getTotalSkillPoints(player);
        playerClassComponent.resetSkills();
        refundSkillShards(player, totalSkillPoints);
        ModComponents.CLASS_COMPONENT.sync(player);
    }

    private static void refundSkillShards(PlayerEntity player, int points) {
        if (!(player instanceof ServerPlayerEntity)) {
            return;
        }
        ItemStack itemStack = new ItemStack(ModItems.SKILL_SHARD, points);
        if (!player.getInventory().insertStack(itemStack)) {
            player.dropItem(itemStack, false);
        }
    }

    public static void useActiveSkill(PlayerEntity player, SkillSlot skillSlot) {
        PlayerClass playerClass = getClass(player);
        if (playerClass == null) {
            return;
        }
        ActiveSkill playerSkill = playerClass.getActiveSkills().get(skillSlot);
        playerSkill.use(player);
        ModComponents.ACTIVE_SKILLS_COMPONENT.sync(player);
    }
}
