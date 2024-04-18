package net.jayugg.cardinalclasses.test.mixin;

import net.jayugg.cardinalclasses.core.PerkSlot;
import net.jayugg.cardinalclasses.util.PlayerClassManager;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.jayugg.cardinalclasses.base.ModClasses.TEST_CLASS;

@Mixin(PlayerEntity.class)
public class PlayerEffectMixin {
    @Inject(method = "tick", at = @At(value = "HEAD"))
    public void onTick(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        if (!player.world.isClient) {
            StatusEffect effect = StatusEffects.HEALTH_BOOST;
            if (PlayerClassManager.hasClass(player, TEST_CLASS) && !player.hasStatusEffect(effect)) {
                boolean ascendedPerk = PlayerClassManager.hasAscendedPerk(player, PerkSlot.ALPHA);
                StatusEffectInstance newEffect = new StatusEffectInstance(effect, StatusEffectInstance.INFINITE, ascendedPerk ? 2 : 1);
                player.addStatusEffect(newEffect);
            }
        }
    }
}
