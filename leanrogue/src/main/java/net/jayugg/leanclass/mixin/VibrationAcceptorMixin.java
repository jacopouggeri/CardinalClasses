package net.jayugg.leanclass.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.listener.VibrationListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.jayugg.leanclass.leanrogue.LeanRogue.ROGUE_CLASS;

@Mixin({VibrationListener.Callback.class})
public interface VibrationAcceptorMixin {
    @Inject(
            at = {@At("HEAD")},
            method = {"canAccept"},
            cancellable = true
    )
    default void canAccept(GameEvent event, GameEvent.Emitter emitter, CallbackInfoReturnable<Boolean> cir) {
        Entity entity = emitter.sourceEntity();
        if (entity instanceof PlayerEntity player) {
            if (ROGUE_CLASS.hasOmega(player)) {
                if (event.equals(GameEvent.STEP)) {
                    cir.setReturnValue(false);
                }

                if (event.equals(GameEvent.HIT_GROUND)) {
                    cir.setReturnValue(false);
                }

                if (event.equals(GameEvent.PROJECTILE_SHOOT)) {
                    cir.setReturnValue(false);
                }
            }
        }

    }
}
