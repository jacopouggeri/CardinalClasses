package net.jayugg.leanclass.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.SculkSensorBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.jayugg.leanclass.leanrogue.LeanRogue.ROGUE_CLASS;

@Mixin({SculkSensorBlock.class})
public class SculkSensorMixin {
    public SculkSensorMixin() {
    }

    @Inject(
            at = {@At("HEAD")},
            method = {"onSteppedOn"},
            cancellable = true
    )
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity, CallbackInfo cir) {
        if (entity instanceof PlayerEntity player) {
            if (ROGUE_CLASS.hasOmega(player)) {
                cir.cancel();
            }
        }

    }
}
