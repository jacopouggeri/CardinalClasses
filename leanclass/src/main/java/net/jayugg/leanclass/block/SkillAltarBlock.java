package net.jayugg.leanclass.block;

import net.jayugg.leanclass.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class SkillAltarBlock extends Block {
    public static final BooleanProperty PASSIVE = BooleanProperty.of("passive");
    public static final EnumProperty<ShardSlot> SHARD_SLOT = EnumProperty.of("shard_slot", ShardSlot.class);
    protected static final VoxelShape SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 12.0, 16.0);
    public SkillAltarBlock(Settings settings) {
        super(settings.luminance(state -> state.get(SHARD_SLOT).asInt() > 0 ? 10 : 0));
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(SHARD_SLOT, ShardSlot.EMPTY)
                .with(PASSIVE, true)
        );
    }

    public static void loadShard(@Nullable Entity charger, World world, BlockPos pos, BlockState state, Direction direction) {
        BlockState blockState = state.with(SHARD_SLOT,ShardSlot.fromInt(direction.getHorizontal() + 1));
        updateShardSlot(charger, world, pos, blockState);
    }

    public static void unloadShard(@Nullable Entity charger, World world, BlockPos pos, BlockState state) {
        BlockState blockState = state.with(SHARD_SLOT, ShardSlot.EMPTY);
        updateShardSlot(charger, world, pos, blockState);
    }

    private static void updateShardSlot(@Nullable Entity charger, World world, BlockPos pos, BlockState blockState) {
        world.setBlockState(pos, blockState, Block.NOTIFY_ALL);
        world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(charger, blockState));
        world.playSound(null, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, SoundEvents.BLOCK_RESPAWN_ANCHOR_CHARGE, SoundCategory.BLOCKS, 1.0f, 1.0f);
    }

    public static boolean canLoadShard(BlockState state) {
        return state.get(SHARD_SLOT).asInt() == 0;
    }

    private static boolean isChargeItem(ItemStack stack) {
        return stack.isOf(ModItems.SKILL_SHARD);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(PASSIVE, SHARD_SLOT);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        boolean isChargeItem = SkillAltarBlock.isChargeItem(itemStack);
        Direction blockSide = hit.getSide();
        int shardSlotState = world.getBlockState(pos).get(SHARD_SLOT).asInt();
        boolean isCreativeMode = player.getAbilities().creativeMode;

        if (blockSide.getHorizontal() == -1 || (hand == Hand.MAIN_HAND && !isChargeItem && SkillAltarBlock.isChargeItem(player.getStackInHand(Hand.OFF_HAND)))) {
            return ActionResult.PASS;
        }

        if (isChargeItem && SkillAltarBlock.canLoadShard(state)) {
            SkillAltarBlock.loadShard(player, world, pos, state, blockSide);
            if (!isCreativeMode) {
                itemStack.decrement(1);
            }
        } else if (shardSlotState == blockSide.getHorizontal() + 1) {
            if (itemStack.isEmpty()) {
                player.setStackInHand(hand, new ItemStack(ModItems.SKILL_SHARD));
                SkillAltarBlock.unloadShard(player, world, pos, state);
            } else if (isChargeItem) {
                if (!isCreativeMode) {
                    itemStack.increment(1);
                }
                SkillAltarBlock.unloadShard(player, world, pos, state);
            } else {
                return ActionResult.PASS;
            }
        } else {
            return ActionResult.PASS;
        }

        return ActionResult.success(world.isClient);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
}
