package net.jayugg.cardinalclasses.mixin;

import net.jayugg.cardinalclasses.util.AbilityManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.jayugg.cardinalclasses.util.MixinManager.canLoadMixins;

@Mixin(LivingEntity.class)
public class PlayerAttackMixin {

    @Inject(method = "damage", at = @At(value = "HEAD"))
    public void onDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (canLoadMixins()) {
            if (source.getAttacker() instanceof PlayerEntity player) {
                if (!player.world.isClient) {
                    LivingEntity target = (LivingEntity) (Object) this; // This mixin is applied to LivingEntity, so 'this' is the target
                    AbilityManager.applyAttackEffectAbilities(target, player);
                }
            }
        }
    }
}