package net.jayugg.cardinalclasses.mixin;

import net.jayugg.cardinalclasses.util.AbilityManager;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.jayugg.cardinalclasses.util.MixinManager.canLoadMixins;

@Mixin(PlayerEntity.class)
public class PlayerDamagedMixin {

    @Inject(method = "damage", at = @At(value = "HEAD"), cancellable = true)
    public void onDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (canLoadMixins()) {
            PlayerEntity player = (PlayerEntity) (Object) this;
            AbilityManager.applyPlayerDamagedAbilities(player, source, amount, cir);
        }
    }
}
