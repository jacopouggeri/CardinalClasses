package net.jayugg.leanclass.block;

import net.jayugg.leanclass.item.ModItems;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class ShardHolderBlock extends BlockWithEntity {
    protected static final VoxelShape SHAPE = VoxelShapes.combineAndSimplify(Block.createCuboidShape(1, 0, 1, 15, 5, 15), Block.createCuboidShape(4, 5, 4, 12, 9, 12), BooleanBiFunction.OR);
    public static final BooleanProperty HAS_SHARD = BooleanProperty.of("has_shard");

    protected ShardHolderBlock(Settings settings) {
        super(settings.luminance(state -> state.get(HAS_SHARD) ? 10 : 0));
        this.setDefaultState(this.stateManager.getDefaultState().with(HAS_SHARD, false));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) { builder.add(HAS_SHARD); }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (state.get(HAS_SHARD)) {
            world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ModItems.SKILL_SHARD)));
        }
    }

    public static void loadShard(@Nullable Entity charger, World world, BlockPos pos, BlockState state) {
        BlockState blockState = state.with(HAS_SHARD, true);
        world.addParticle(ParticleTypes.GLOW, (double)pos.getX() + 0.5, (double)pos.getY() + 1.0, (double)pos.getZ() + 0.5, 0.0, 0.0, 0.0);
        updateShardSlot(charger, world, pos, blockState);
    }

    public static void unloadShard(@Nullable Entity charger, World world, BlockPos pos, BlockState state) {
        BlockState blockState = state.with(HAS_SHARD, false);
        updateShardSlot(charger, world, pos, blockState);
    }

    private static void updateShardSlot(@Nullable Entity charger, World world, BlockPos pos, BlockState blockState) {
        world.setBlockState(pos, blockState, Block.NOTIFY_ALL);
        world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(charger, blockState));
        world.playSound(null, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, SoundEvents.BLOCK_AMETHYST_CLUSTER_PLACE, SoundCategory.BLOCKS, 1.5f, 0.5f);
    }

    public static boolean canLoadShard(BlockState state) {
        return !state.get(HAS_SHARD);
    }

    private static boolean isChargeItem(ItemStack stack) {
        return stack.isOf(ModItems.SKILL_SHARD);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        boolean isChargeItem = isChargeItem(itemStack);
        boolean hasShard = world.getBlockState(pos).get(HAS_SHARD);
        boolean isCreativeMode = player.getAbilities().creativeMode;

        if (hand == Hand.MAIN_HAND && !isChargeItem && ShardHolderBlock.isChargeItem(player.getStackInHand(Hand.OFF_HAND))) {
            return ActionResult.PASS;
        }

        if (isChargeItem && canLoadShard(state)) {
            loadShard(player, world, pos, state);
            if (!isCreativeMode) {
                itemStack.decrement(1);
            }
        } else if (hasShard) {
            if (itemStack.isEmpty()) {
                player.setStackInHand(hand, new ItemStack(ModItems.SKILL_SHARD));
                unloadShard(player, world, pos, state);
            } else if (isChargeItem) {
                if (!isCreativeMode) {
                    ItemStack stack = new ItemStack(ModItems.SKILL_SHARD);
                    if (!player.getInventory().insertStack(stack)) {
                        player.dropItem(stack, false);
                    }
                }
                unloadShard(player, world, pos, state);
            } else {
                return ActionResult.PASS;
            }
        } else {
            return ActionResult.PASS;
        }

        return ActionResult.success(world.isClient);
    }
    // Block Entity

    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ShardHolderBlockEntity(pos, state);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? checkType(type, ModBlockEntities.SHARD_HOLDER, ShardHolderBlockEntity::tick) : null;
    }
}
