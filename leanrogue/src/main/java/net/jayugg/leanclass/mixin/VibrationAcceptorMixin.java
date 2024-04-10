package net.jayugg.leanclass.mixin;

import net.jayugg.leanclass.modules.PerkSlot;
import net.jayugg.leanclass.modules.PlayerClassManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.listener.VibrationListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.jayugg.leanclass.MixinManager.canLoadMixins;
import static net.jayugg.leanclass.leanrogue.addons.CustomAbilities.MUFFLED_STEPS_PERK;
import static net.jayugg.leanclass.leanrogue.addons.ModClasses.ROGUE_CLASS;

@Mixin({VibrationListener.Callback.class})
public interface VibrationAcceptorMixin {
    @Inject(
            at = {@At("HEAD")},
            method = {"canAccept"},
            cancellable = true
    )
    default void canAccept(GameEvent event, GameEvent.Emitter emitter, CallbackInfoReturnable<Boolean> cir) {
        if (canLoadMixins()) {
            PerkSlot perkSlot = ROGUE_CLASS.getPerkSlot(MUFFLED_STEPS_PERK);
            Entity entity = emitter.sourceEntity();
            if (entity instanceof PlayerEntity player) {
                if (PlayerClassManager.hasAscendedPerk(player, perkSlot)) {
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
}
