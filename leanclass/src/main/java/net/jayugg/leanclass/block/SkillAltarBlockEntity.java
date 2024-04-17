package net.jayugg.leanclass.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SkillAltarBlockEntity extends BlockEntity {

    public SkillAltarBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SKILL_ALTAR, pos, state);
    }

    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
    }

    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
    }

    public static void tick(World world, BlockPos pos, BlockState state, SkillAltarBlockEntity blockEntity) {
    }
}