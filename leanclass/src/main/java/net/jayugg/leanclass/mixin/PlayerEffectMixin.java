package net.jayugg.leanclass.mixin;

import net.jayugg.leanclass.modules.PlayerClassManager;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.jayugg.leanclass.implement.ModClasses.BASE_CLASS;

@Mixin(PlayerEntity.class)
public class PlayerEffectMixin {
    @Inject(method = "tick", at = @At(value = "HEAD"))
    public void onTick(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        if (!player.world.isClient) {
            StatusEffect effect = StatusEffects.HEALTH_BOOST;
            if (PlayerClassManager.hasClass(player, BASE_CLASS) && !player.hasStatusEffect(effect)) {
                StatusEffectInstance newEffect = new StatusEffectInstance(effect, StatusEffectInstance.INFINITE, 1);
                player.addStatusEffect(newEffect);
            }
        }
    }
}
