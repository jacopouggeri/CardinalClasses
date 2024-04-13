package net.jayugg.leanclass.mixin;

import net.jayugg.leanclass.util.PerkSlot;
import net.jayugg.leanclass.util.PlayerClassManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.jayugg.leanclass.MixinManager.canLoadMixins;
import static net.jayugg.leanclass.leanrogue.addons.CustomAbilities.MUFFLED_STEPS_PERK;
import static net.jayugg.leanclass.leanrogue.addons.ModClasses.ROGUE_CLASS;

@Mixin({LivingEntity.class})
public class FallSoundMixin {
    @Inject(
            at = {@At("HEAD")},
            method = {"getFallSound"},
            cancellable = true
    )
    protected void getFallSound(int distance, CallbackInfoReturnable<SoundEvent> cir) {
        if (canLoadMixins()) {
            PerkSlot perkSlot = ROGUE_CLASS.getPerkSlot(MUFFLED_STEPS_PERK);
            if ((Entity) (Object) this instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) (Object) this;
                if (PlayerClassManager.hasAscendedPerk(player, perkSlot)) {
                    cir.setReturnValue(SoundEvents.BLOCK_WOOL_HIT);
                }
            }
        }
    }
}
