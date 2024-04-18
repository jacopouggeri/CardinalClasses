package net.jayugg.cardinalclasses.test;

import net.jayugg.cardinalclasses.base.WithAttackEffect;
import net.jayugg.cardinalclasses.core.PerkSlot;
import net.jayugg.cardinalclasses.core.PlayerPerk;
import net.jayugg.cardinalclasses.util.PlayerClassManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;

public class AttackLevitationPerk extends PlayerPerk implements WithAttackEffect {
    public AttackLevitationPerk(String id, Item icon, PerkSlot perkSlot) {
        super(id, icon, perkSlot);
    }

    @Override
    public void activateEffect(LivingEntity target, PlayerEntity attacker) {
        boolean ascendedPerk = PlayerClassManager.hasAscendedPerk(attacker, this.perkSlot);
        int amplifier = ascendedPerk ? 2 : 1;
        target.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 100, amplifier));
    }
}
