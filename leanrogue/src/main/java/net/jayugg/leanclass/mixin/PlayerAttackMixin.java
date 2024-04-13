package net.jayugg.leanclass.mixin;

import net.jayugg.leanclass.leanrogue.RogueClass;
import net.jayugg.leanclass.util.PlayerClassManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.jayugg.leanclass.MixinManager.canLoadMixins;
import static net.jayugg.leanclass.leanrogue.addons.ModClasses.ROGUE_CLASS;

@Mixin(LivingEntity.class)
public class PlayerAttackMixin {
    @Inject(method = "damage", at = @At(value = "HEAD"))
    public void onDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (canLoadMixins()) {
            if (source.getAttacker() instanceof PlayerEntity player) {
                if (!player.world.isClient && PlayerClassManager.hasClass(player, ROGUE_CLASS)) { // Ensure operations are server-side
                    Entity target = (Entity) (Object) this; // This mixin is applied to LivingEntity, so 'this' is the target
                    RogueClass.applyPerkEffects(player, target);
                }
            }
        }
    }
}
