package net.jayugg.leanclass.mixin;

import net.jayugg.leanclass.implement.ExampleAttackSkill;
import net.jayugg.leanclass.modules.PlayerClassManager;
import net.jayugg.leanclass.modules.PlayerSkill;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.jayugg.leanclass.MixinManager.canLoadMixins;
import static net.jayugg.leanclass.implement.ModAbilities.*;

@Mixin(LivingEntity.class)
public class PlayerAttackMixin {
    @Inject(method = "damage", at = @At(value = "HEAD"))
    public void onDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (canLoadMixins()) {
            if (source.getAttacker() instanceof PlayerEntity player) {
                if (!player.world.isClient) {
                    LivingEntity target = (LivingEntity) (Object) this; // This mixin is applied to LivingEntity, so 'this' is the target
                    PlayerSkill[] skills = {BASE_PASSIVE_RED, BASE_PASSIVE_YELLOW, BASE_PASSIVE_GREEN, BASE_PASSIVE_BLUE};
                    for (PlayerSkill skill : skills) {
                        if (PlayerClassManager.hasSkill(player, skill)) {
                            ((ExampleAttackSkill) skill).activateEffect(target, player);
                        }
                    }
                }
            }
        }
    }
}