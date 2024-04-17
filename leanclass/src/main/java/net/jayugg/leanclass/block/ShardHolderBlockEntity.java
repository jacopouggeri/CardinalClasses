package net.jayugg.leanclass.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Nameable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ShardHolderBlockEntity extends BlockEntity implements Nameable {
    private Text customName;

    public ShardHolderBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SHARD_HOLDER, pos, state);
    }

    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        if (this.hasCustomName()) {
            nbt.putString("CustomName", Text.Serializer.toJson(this.customName));
        }

    }

    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        if (nbt.contains("CustomName", 8)) {
            this.customName = Text.Serializer.fromJson(nbt.getString("CustomName"));
        }

    }

    public static void tick(World world, BlockPos pos, BlockState state, ShardHolderBlockEntity blockEntity) {
    }

    public Text getName() {
        return this.customName != null ? this.customName : Text.translatable("container.floating_item");
    }

    public void setCustomName(@Nullable Text customName) {
        this.customName = customName;
    }

    @Nullable
    public Text getCustomName() {
        return this.customName;
    }
}
