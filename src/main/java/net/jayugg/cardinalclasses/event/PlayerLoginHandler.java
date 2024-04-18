package net.jayugg.cardinalclasses.event;

import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.jayugg.cardinalclasses.advancement.ModCriteria;
import net.jayugg.cardinalclasses.component.ModComponents;
import net.jayugg.cardinalclasses.core.AbilityType;
import net.jayugg.cardinalclasses.core.PerkSlot;
import net.jayugg.cardinalclasses.core.PlayerClass;
import net.jayugg.cardinalclasses.registry.PlayerClassRegistry;
import net.jayugg.cardinalclasses.util.PlayerClassManager;
import net.jayugg.cardinalclasses.core.SkillSlot;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.server.ServerAdvancementLoader;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import static net.jayugg.cardinalclasses.CardinalClasses.LOGGER;
import static net.jayugg.cardinalclasses.CardinalClasses.MOD_ID;

public class PlayerLoginHandler {
    public static void grantMissingAdvancements(ServerPlayerEntity player) {
        if (player.getServer() == null || player.getServer().getAdvancementLoader() == null) {
            return;
        }
        PlayerClass playerClass = PlayerClassManager.getClass(player);
        ServerAdvancementLoader advancementLoader = player.getServer().getAdvancementLoader();
        PlayerAdvancementTracker advancementTracker = player.getAdvancementTracker();

        Advancement rootAdvancement = advancementLoader.get( new Identifier("minecraft", MOD_ID + "/root"));

        if (rootAdvancement == null || playerClass == null) {
            return;
        }

        LOGGER.warn("Player class: {}", playerClass.getId());

        if (!advancementTracker.getProgress(rootAdvancement).isDone()) {
            ModCriteria.OBTAIN_CLASS.trigger(player, playerClass);
            for (AbilityType type : AbilityType.values()) {
                if (type == AbilityType.PERK) {
                    if (PlayerClassManager.getAscendedPerk(player).isEmpty()) { continue; }
                    PerkSlot ascendedPerk = PlayerClassManager.getAscendedPerk(player).get();
                    ModCriteria.ASCEND_PERK.trigger(player, playerClass, playerClass.getPerks().get(ascendedPerk), true);
                } else {
                    for (SkillSlot skillSlot : playerClass.getSkills(type).keySet()) {
                        int skillLevel = PlayerClassManager.getSkillLevel(player, type, skillSlot);
                        for (int i = 0; i < skillLevel; i++) {
                            ModCriteria.OBTAIN_SKILL.trigger(player, playerClass, playerClass.getSkills(type).get(skillSlot), i + 1);
                        }
                    }
                }
            }
            LOGGER.warn("Granting missing class advancements to {}", player.getName().getString());
        }

    }
    public static void handleMissingClassId(ServerPlayerEntity player) {
        String playerClassId = ModComponents.CLASS_COMPONENT.get(player).getId();
        if (PlayerClassManager.getClass(player) == null) {
            return;
        }
        if (PlayerClassRegistry.getPlayerClass(playerClassId) == null) {
            LOGGER.warn("Player {} has an invalid class id: {} - Resetting Class", player.getName().getString(), playerClassId);
            PlayerClassManager.resetPlayerClass(player);
        }
    }
    public static void register() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity player = handler.player;
            handleMissingClassId(player);
            grantMissingAdvancements(player);
        });
    }
}