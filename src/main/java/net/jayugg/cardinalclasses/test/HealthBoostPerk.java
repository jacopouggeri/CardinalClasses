package net.jayugg.cardinalclasses.test;

import net.jayugg.cardinalclasses.base.WithPassiveEffect;
import net.jayugg.cardinalclasses.core.PerkSlot;
import net.jayugg.cardinalclasses.core.PlayerPerk;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;

public class HealthBoostPerk extends PlayerPerk implements WithPassiveEffect {

    public HealthBoostPerk(String id, ItemConvertible icon, PerkSlot perkSlot) {
        super(id, icon, perkSlot);
    }

    @Override
    public void applyEffect(PlayerEntity player) {
        StatusEffect effect = StatusEffects.HEALTH_BOOST;
        if (!player.hasStatusEffect(effect)) {
            boolean ascendedPerk = this.isAscended(player);
            StatusEffectInstance newEffect = new StatusEffectInstance(effect, StatusEffectInstance.INFINITE, ascendedPerk ? 2 : 1, false, false);
            player.addStatusEffect(newEffect);
        }
    }
}
