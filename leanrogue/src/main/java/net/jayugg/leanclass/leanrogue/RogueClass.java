package net.jayugg.leanclass.leanrogue;

import net.jayugg.leanclass.implement.ModAbilities;
import net.jayugg.leanclass.leanrogue.addons.CustomAbilities;
import net.jayugg.leanclass.util.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Map;

public class RogueClass extends PlayerClass {
    private static Map<SkillSlot, PlayerSkill> createSkills() {
        return Map.of(
                SkillSlot.PASSIVE1, ModAbilities.BASE_PASSIVE_SKILL,
                SkillSlot.PASSIVE2, ModAbilities.BASE_PASSIVE_SKILL,
                SkillSlot.PASSIVE3, ModAbilities.BASE_PASSIVE_SKILL,
                SkillSlot.PASSIVE4, ModAbilities.BASE_PASSIVE_SKILL,
                SkillSlot.ACTIVE1, ModAbilities.BASE_ACTIVE_SKILL,
                SkillSlot.ACTIVE2, ModAbilities.BASE_ACTIVE_SKILL,
                SkillSlot.ACTIVE3, ModAbilities.BASE_ACTIVE_SKILL,
                SkillSlot.ACTIVE4, ModAbilities.BASE_ACTIVE_SKILL
        );
    }

    private static Map<PerkSlot, PlayerPerk> createPerks() {
        return Map.of(
                PerkSlot.ALPHA, CustomAbilities.POISON_HAND_PERK,
                PerkSlot.OMEGA, ModAbilities.BASE_PERK
        );
    }

    public RogueClass() {
        super("rogue", createSkills(), createPerks());
    }

    public static void applyPerkEffects(PlayerEntity player, Entity target) {
        if (!(target instanceof LivingEntity livingTarget)) return;

        // Check if player perk is ascended
        StatusEffect effectToApply = PlayerClassManager.hasAscendedPerk(player, PerkSlot.ALPHA) ? StatusEffects.WITHER : StatusEffects.POISON;
        applyEffectToTarget(effectToApply, livingTarget);
    }

    private static void applyEffectToTarget(StatusEffect effect, LivingEntity target) {
        final int DEFAULT_DURATION = 20;
        final int MAX_DURATION = 800; // 200 ticks = 10 seconds
        StatusEffectInstance currentEffect = target.getStatusEffect(effect);

        int newDuration = DEFAULT_DURATION;
        if (currentEffect != null) {
            int duration = currentEffect.getDuration();
            double decayFactor = Math.exp(-duration / (double) MAX_DURATION);
            newDuration = (int) (duration + DEFAULT_DURATION * decayFactor);
        }
        StatusEffectInstance newEffect = new StatusEffectInstance(effect, newDuration, 1);

        target.addStatusEffect(newEffect);
    }
}
