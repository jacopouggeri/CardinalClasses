package net.jayugg.cardinalclasses.test.mixin;

import net.jayugg.cardinalclasses.base.WithAttackEffect;
import net.jayugg.cardinalclasses.core.AbilityType;
import net.jayugg.cardinalclasses.core.PlayerAbility;
import net.jayugg.cardinalclasses.util.PlayerClassManager;
import net.jayugg.cardinalclasses.core.PlayerSkill;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.jayugg.cardinalclasses.base.ModClasses.TEST_CLASS;
import static net.jayugg.cardinalclasses.util.MixinManager.canLoadMixins;
import static net.jayugg.cardinalclasses.base.ModAbilities.*;

@Mixin(LivingEntity.class)
public class PlayerAttackMixin {

    @Inject(method = "damage", at = @At(value = "HEAD"))
    public void onDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (canLoadMixins()) {
            if (source.getAttacker() instanceof PlayerEntity player) {
                if (!player.world.isClient) {
                    LivingEntity target = (LivingEntity) (Object) this; // This mixin is applied to LivingEntity, so 'this' is the target
                    WithAttackEffect[] abilities = { (WithAttackEffect) TEST_PASSIVE_RED,
                            (WithAttackEffect) TEST_PASSIVE_YELLOW,(WithAttackEffect) TEST_PASSIVE_GREEN,
                            (WithAttackEffect) TEST_PASSIVE_BLUE, (WithAttackEffect) TEST_ATTACK_PERK};
                    for (WithAttackEffect ability : abilities) {
                        if (PlayerClassManager.hasClass(player, TEST_CLASS)) {
                            if (((PlayerAbility) ability).getType() == AbilityType.PERK) {
                                ability.activateEffect(target, player);
                            } else if (PlayerClassManager.hasSkill(player, (PlayerSkill) ability)) {
                                ability.activateEffect(target, player);
                            }
                        }
                    }
                }
            }
        }
    }
}