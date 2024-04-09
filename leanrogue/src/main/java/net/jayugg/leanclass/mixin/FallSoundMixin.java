package net.jayugg.leanclass.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.jayugg.leanclass.leanrogue.LeanRogue.ROGUE_CLASS;

@Mixin({LivingEntity.class})
public class FallSoundMixin {

    public FallSoundMixin() { }

    @Inject(
            at = {@At("HEAD")},
            method = {"getFallSound"},
            cancellable = true
    )
    protected void getFallSound(int distance, CallbackInfoReturnable<SoundEvent> cir) {
        if ((Entity) (Object) this instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) (Object) this;
            if (ROGUE_CLASS.hasOmega(player)) {
                cir.setReturnValue(SoundEvents.BLOCK_WOOL_HIT);
            }
        }

    }
}
