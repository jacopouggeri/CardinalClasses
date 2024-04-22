package net.jayugg.cardinalclasses.util;

import net.jayugg.cardinalclasses.base.WithAttackEffect;
import net.jayugg.cardinalclasses.base.WithPassiveEffect;
import net.jayugg.cardinalclasses.base.WithPlayerDamagedEffect;
import net.jayugg.cardinalclasses.core.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class AbilityManager {
    public static void applyAttackEffectAbilities(LivingEntity target, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (!(source.getAttacker() instanceof PlayerEntity player)) { return; }
        PlayerClass playerClass = PlayerClassManager.getClass(player);
        if (playerClass == null) { return; }
        playerClass.getAbilities().stream()
                .filter(ability -> ability instanceof WithAttackEffect)
                .filter(ability -> getAbilityLevel(player, ability) > 0)
                .map(ability -> (WithAttackEffect) ability)
                .forEach(ability -> ability.applyEffect(target, player, source, amount, cir));
    }

    public static void applyPassiveEffectAbilities(PlayerEntity player) {
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

    public static void applyPlayerDamagedAbilities(PlayerEntity player, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        PlayerClass playerClass = PlayerClassManager.getClass(player);
        if (playerClass == null) {
            return;
        }
        playerClass.getAbilities().stream()
                .filter(ability -> ability instanceof WithPlayerDamagedEffect)
                .filter(ability -> getAbilityLevel(player, ability) > 0)
                .map(ability -> (WithPlayerDamagedEffect) ability)
                .forEach(ability -> ability.applyEffect(player, source, amount, cir));
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
