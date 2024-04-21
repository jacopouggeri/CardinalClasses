package net.jayugg.cardinalclasses.mixin;

import net.jayugg.cardinalclasses.util.AbilityManager;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEffectMixin {
    @Inject(method = "tick", at = @At(value = "HEAD"))
    public void onTick(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        if (!player.world.isClient) {
            AbilityManager.applyStatusEffectAbilities(player);
        }
    }
}
