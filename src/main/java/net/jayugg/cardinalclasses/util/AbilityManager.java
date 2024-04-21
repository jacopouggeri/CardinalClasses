package net.jayugg.cardinalclasses.util;

import net.jayugg.cardinalclasses.base.WithAttackEffect;
import net.jayugg.cardinalclasses.base.WithPassiveEffect;
import net.jayugg.cardinalclasses.core.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public class AbilityManager {
    public static void applyAttackEffectAbilities(LivingEntity target, PlayerEntity player) {
        PlayerClass playerClass = PlayerClassManager.getClass(player);
        if (playerClass == null) {
            return;
        }
        playerClass.getAbilities().stream()
                .filter(ability -> ability instanceof WithAttackEffect)
                .filter(ability -> getAbilityLevel(player, ability) > 0)
                .map(ability -> (WithAttackEffect) ability)
                .forEach(ability -> ability.activateEffect(target, player));
    }

    public static void applyStatusEffectAbilities(PlayerEntity player) {
        PlayerClass playerClass = PlayerClassManager.getClass(player);
        if (playerClass == null) {
            return;
        }
        playerClass.getAbilities().stream()
                .filter(ability -> ability instanceof WithPassiveEffect)
                .filter(ability -> getAbilityLevel(player, ability) > 0)
                .map(ability -> (WithPassiveEffect) ability)
                .forEach(ability -> ability.applyEffect(player));
    }

    public static int getAbilityLevel(PlayerEntity player, PlayerAbility ability) {
        PlayerClass playerClass = PlayerClassManager.getClass(player);
        if (playerClass == null) {
            return 0;
        }
        return switch (ability.getType()) {
            case PASSIVE, ACTIVE -> PlayerClassManager.getSkillLevel(player, (PlayerSkill) ability);
            case PERK -> PlayerClassManager.hasAscendedPerk(player, ((PlayerPerk) ability).getPerkSlot()) ? 2 : 1;
        };
    }
}
