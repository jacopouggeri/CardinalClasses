package net.jayugg.leanclass.leanrogue;

import net.jayugg.leanclass.Utils.PerkSlot;
import net.jayugg.leanclass.component.ModComponents;
import net.jayugg.leanclass.component.PlayerPerkComponent;
import net.jayugg.leanclass.modules.PlayerClass;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;

public class RogueClass implements PlayerClass {
    private final String name = "Rogue";
    @Override
    public String getName() {
        return name;
    }

    public static void applyPerkEffects(PlayerEntity player, Entity target) {
        if (!(target instanceof LivingEntity livingTarget)) return;

        PlayerPerkComponent perkComponent = ModComponents.PERK_COMPONENT.get(player);

        // Check if player perk is ascended
        StatusEffect effectToApply = determineEffect(perkComponent);
        applyEffectToTarget(effectToApply, livingTarget);
    }

    private static StatusEffect determineEffect(PlayerPerkComponent perkComponent) {
        return perkComponent.isSlotAscended(PerkSlot.ALPHA) ? StatusEffects.WITHER : StatusEffects.POISON;
    }

    private static void applyEffectToTarget(StatusEffect effect, LivingEntity target) {
        final int DEFAULT_DURATION = 20;
        final int MAX_DURATION = 200; // 200 ticks = 10 seconds
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
