package net.jayugg.cardinalclasses.test;

import net.jayugg.cardinalclasses.base.WithAttackEffect;
import net.jayugg.cardinalclasses.core.PerkSlot;
import net.jayugg.cardinalclasses.core.PlayerPerk;
import net.jayugg.cardinalclasses.util.PlayerClassManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class AttackLevitationPerk extends PlayerPerk implements WithAttackEffect {
    public AttackLevitationPerk(String id, Item icon, PerkSlot perkSlot) {
        super(id, icon, perkSlot);
    }

    @Override
    public void applyEffect(LivingEntity target, PlayerEntity attacker, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        boolean ascendedPerk = PlayerClassManager.hasAscendedPerk(attacker, this.perkSlot);
        int amplifier = ascendedPerk ? 2 : 1;
        target.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 100, amplifier));
    }
}
