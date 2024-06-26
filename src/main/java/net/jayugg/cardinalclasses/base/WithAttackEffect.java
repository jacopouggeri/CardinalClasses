package net.jayugg.cardinalclasses.base;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public interface WithAttackEffect {
    void applyEffect(LivingEntity target, PlayerEntity attacker, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir);
}
