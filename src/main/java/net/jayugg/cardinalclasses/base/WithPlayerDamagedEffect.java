package net.jayugg.cardinalclasses.base;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public interface WithPlayerDamagedEffect {
    void applyEffect(PlayerEntity player, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir);
}
