package net.jayugg.leanclass.event;

import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.jayugg.leanclass.advancement.ModCriteria;
import net.jayugg.leanclass.base.AbilityType;
import net.jayugg.leanclass.base.PlayerClass;
import net.jayugg.leanclass.util.PlayerClassManager;
import net.jayugg.leanclass.base.SkillSlot;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.server.ServerAdvancementLoader;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import static net.jayugg.leanclass.LeanClass.LOGGER;
import static net.jayugg.leanclass.LeanClass.MOD_ID;

public class PlayerLoginHandler {
    public static void grantMissingAdvancements(ServerPlayerEntity player) {
        if (player.getServer().getAdvancementLoader() == null) {
            return;
        }
        PlayerClass playerClass = PlayerClassManager.getClass(player);
        ServerAdvancementLoader advancementLoader = player.getServer().getAdvancementLoader();
        PlayerAdvancementTracker advancementTracker = player.getAdvancementTracker();

        Advancement rootAdvancement = advancementLoader.get( new Identifier("minecraft", MOD_ID + "/root"));

        if (rootAdvancement == null) {
            return;
        }

        if (!advancementTracker.getProgress(rootAdvancement).isDone()) {
            ModCriteria.OBTAIN_CLASS.trigger(player, PlayerClassManager.getClass(player));
            AbilityType[] types = {AbilityType.PASSIVE, AbilityType.ACTIVE};
            for (AbilityType type : types) { // TODO INCLUDE PERKS
                for (SkillSlot skillSlot : playerClass.getSkills(type).keySet()) {
                    int skillLevel = PlayerClassManager.getSkillLevel(player, skillSlot);
                    for (int i = 0; i < skillLevel; i++) {
                        ModCriteria.OBTAIN_SKILL.trigger(player, playerClass.getSkills(type).get(skillSlot), i + 1);
                    }
                }
            }
            LOGGER.warn("Granting missing class advancements to " + player.getName().getString());
        }

    }
    public static void register() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity player = handler.player;
            grantMissingAdvancements(player);
        });
    }
}