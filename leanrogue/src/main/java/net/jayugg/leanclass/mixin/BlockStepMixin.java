package net.jayugg.leanclass.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.jayugg.leanclass.leanrogue.LeanRogue.ROGUE_CLASS;

@Mixin({Block.class})
public class BlockStepMixin {
    private Entity entity = null;
    private BlockSoundGroup blockSoundGroup = null;

    public BlockStepMixin() {
    }

    @Inject(at = {@At("HEAD")}, method = {"onSteppedOn"}, cancellable = true)
    protected void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity, CallbackInfo cir) {
        this.entity = entity;
        this.blockSoundGroup = state.getSoundGroup();
    }

    @Inject(at = {@At("HEAD")}, method = {"getSoundGroup"}, cancellable = true)
    protected void getSoundGroup(BlockState state, CallbackInfoReturnable<BlockSoundGroup> cir) {
        BlockSoundGroup defaultSoundGroup = this.blockSoundGroup;
        if (this.entity != null && this.entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) this.entity;
            if (defaultSoundGroup != null) {
                BlockSoundGroup muffledBlockSoundGroup = new BlockSoundGroup(1.0F, 1.0F, defaultSoundGroup.getBreakSound(), SoundEvents.BLOCK_WOOL_STEP, defaultSoundGroup.getPlaceSound(), defaultSoundGroup.getHitSound(), SoundEvents.BLOCK_WOOL_FALL);
                if (ROGUE_CLASS.hasOmega(player)) {
                    cir.setReturnValue(muffledBlockSoundGroup);
                }
            }
        }

    }
}