package net.jayugg.leanclass.mixin;

import net.jayugg.leanclass.implement.ExampleAttackSkill;
import net.jayugg.leanclass.util.PlayerClassManager;
import net.jayugg.leanclass.base.PlayerSkill;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.jayugg.leanclass.util.MixinManager.canLoadMixins;
import static net.jayugg.leanclass.implement.ModAbilities.*;

@Mixin(LivingEntity.class)
public class PlayerAttackMixin {

    @Inject(method = "damage", at = @At(value = "HEAD"))
    public void onDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (canLoadMixins()) {
            if (source.getAttacker() instanceof PlayerEntity player) {
                if (!player.world.isClient) {
                    LivingEntity target = (LivingEntity) (Object) this; // This mixin is applied to LivingEntity, so 'this' is the target
                    PlayerSkill[] skills = {TEST_PASSIVE_RED, TEST_PASSIVE_YELLOW, TEST_PASSIVE_GREEN, TEST_PASSIVE_BLUE};
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