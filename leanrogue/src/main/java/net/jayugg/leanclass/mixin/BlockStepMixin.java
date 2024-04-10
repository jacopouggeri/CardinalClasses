package net.jayugg.leanclass.mixin;

import net.jayugg.leanclass.modules.PerkSlot;
import net.jayugg.leanclass.modules.PlayerClassManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.jayugg.leanclass.MixinManager.canLoadMixins;
import static net.jayugg.leanclass.leanrogue.addons.CustomAbilities.MUFFLED_STEPS_PERK;
import static net.jayugg.leanclass.leanrogue.addons.ModClasses.ROGUE_CLASS;

@Mixin({Block.class})
public class BlockStepMixin {
    @Unique
    private Entity entity = null;
    @Unique
    private BlockSoundGroup blockSoundGroup = null;

    @Inject(at = {@At("HEAD")}, method = {"onSteppedOn"})
    protected void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity, CallbackInfo cir) {
        this.entity = entity;
        this.blockSoundGroup = state.getSoundGroup();
    }

    @Inject(at = {@At("HEAD")}, method = {"getSoundGroup"}, cancellable = true)
    protected void getSoundGroup(BlockState state, CallbackInfoReturnable<BlockSoundGroup> cir) {
        if (canLoadMixins()) {
            PerkSlot perkSlot = ROGUE_CLASS.getPerkSlot(MUFFLED_STEPS_PERK);
            if (this.entity instanceof PlayerEntity player && this.blockSoundGroup != null) {
                BlockSoundGroup muffledBlockSoundGroup = new BlockSoundGroup(1.0F, 1.0F, this.blockSoundGroup.getBreakSound(), SoundEvents.BLOCK_WOOL_STEP, this.blockSoundGroup.getPlaceSound(), this.blockSoundGroup.getHitSound(), SoundEvents.BLOCK_WOOL_FALL);
                if (PlayerClassManager.hasAscendedPerk(player, perkSlot)) {
                    cir.setReturnValue(muffledBlockSoundGroup);
                }
            }
        }
    }
}