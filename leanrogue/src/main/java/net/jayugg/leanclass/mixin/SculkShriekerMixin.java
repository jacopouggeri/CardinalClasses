package net.jayugg.leanclass.mixin;

import net.jayugg.leanclass.util.PerkSlot;
import net.jayugg.leanclass.util.PlayerClassManager;
import net.minecraft.block.BlockState;
import net.minecraft.block.SculkShriekerBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.jayugg.leanclass.MixinManager.canLoadMixins;
import static net.jayugg.leanclass.leanrogue.addons.CustomAbilities.MUFFLED_STEPS_PERK;
import static net.jayugg.leanclass.leanrogue.addons.ModClasses.ROGUE_CLASS;

@Mixin({SculkShriekerBlock.class})
public class SculkShriekerMixin {

    public SculkShriekerMixin() {
    }

    @Inject(
            at = {@At("HEAD")},
            method = {"onSteppedOn"},
            cancellable = true
    )
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity, CallbackInfo cir) {
        if (canLoadMixins()) {
            PerkSlot perkSlot = ROGUE_CLASS.getPerkSlot(MUFFLED_STEPS_PERK);
            if (entity instanceof PlayerEntity player) {
                if (PlayerClassManager.hasAscendedPerk(player, perkSlot)) {
                    cir.cancel();
                }
            }
        }
    }
}
