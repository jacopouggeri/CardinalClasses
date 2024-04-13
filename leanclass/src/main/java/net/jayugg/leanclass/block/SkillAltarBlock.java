package net.jayugg.leanclass.block;

import net.jayugg.leanclass.base.AbilityType;
import net.jayugg.leanclass.item.ModItems;
import net.jayugg.leanclass.util.PlayerClassManager;
import net.jayugg.leanclass.base.SkillSlot;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class SkillAltarBlock extends Block {
    public static final BooleanProperty PASSIVE = BooleanProperty.of("passive");
    public static final EnumProperty<ShardSlot> SHARD_SLOT = EnumProperty.of("shard_slot", ShardSlot.class);
    protected static final VoxelShape SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 13.0, 16.0);
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
        world.playSound(null, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, SoundEvents.BLOCK_AMETHYST_CLUSTER_PLACE, SoundCategory.BLOCKS, 1.5f, 0.5f);
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
                    ItemStack stack = new ItemStack(ModItems.SKILL_SHARD);
                    if (!player.getInventory().insertStack(stack)) {
                        player.dropItem(stack, false);
                    }
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

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);
        if (state.get(SHARD_SLOT) != ShardSlot.EMPTY) {
            world.addParticle(ParticleTypes.ENCHANT, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, random.nextFloat() - 0.5f, random.nextFloat(), random.nextFloat() - 0.5f);
        }
    }

    @Override
    public void onEntityLand(BlockView world, Entity entity) {
        if (entity.bypassesLandingEffects() || isShardSlotEmpty(world, entity)) {
            super.onEntityLand(world, entity);
        } else if (entity instanceof PlayerEntity player) {
            bounce(player);
            if (useShard(player, (World) world)) {
                successEffects((World) world, player);
            } else {
                failBehaviour((World) world, player);
            }
        }
    }

    private static void failBehaviour(World world, PlayerEntity player) {
        player.damage(world.getDamageSources().magic(), 4.0f);
        unloadShard(player, world, player.getBlockPos(), world.getBlockState(player.getBlockPos()));
        ItemStack stack = new ItemStack(ModItems.SKILL_SHARD, 1);
        world.spawnEntity(new ItemEntity(world, player.getX(), player.getY(), player.getZ(), stack));
        world.playSound(null, player.getBlockPos(), SoundEvents.BLOCK_CONDUIT_DEACTIVATE, SoundCategory.BLOCKS, 1.2f, 0.75f);
    }

    private void successEffects(World world, PlayerEntity player) {
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 20, 1));
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 30, 1));
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 30, 1));
        world.playSound(null, player.getBlockPos(), SoundEvents.BLOCK_CONDUIT_ACTIVATE, SoundCategory.BLOCKS, 1.2f, 1.25f);
    }

    private boolean isShardSlotEmpty(BlockView world, Entity entity) {
        return world.getBlockState(entity.getBlockPos()).get(SHARD_SLOT).asInt() == 0;
    }

    private boolean useShard(PlayerEntity player, World world) {
        if (world.isClient) {
            return false;
        }
        BlockState state = world.getBlockState(player.getBlockPos());
        SkillSlot skillSlot = getSkillSlot(state);
        AbilityType type = state.get(PASSIVE) ? AbilityType.PASSIVE : AbilityType.ACTIVE;
        if (skillSlot != null && PlayerClassManager.skillUp(player, skillSlot, type)) {
            world.setBlockState(player.getBlockPos(), state.with(SHARD_SLOT, ShardSlot.EMPTY));
            return true;
        }
        return false;
    }

    private SkillSlot getSkillSlot(BlockState state) {
        ShardSlot shardSlot = state.get(SHARD_SLOT);
        if (shardSlot == ShardSlot.EMPTY) {
            return null;
        }

        int slotNum = shardSlot.asInt();
        if (!state.get(PASSIVE)) {
            slotNum += 4;
        }

        return SkillSlot.fromValue(slotNum);
    }

    private void bounce(Entity entity) {
        Vec3d vec3d = entity.getVelocity();
        if (vec3d.y < 0.0 && entity instanceof PlayerEntity) {
            ((PlayerEntity) entity).jump();
        }
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBreak(world, pos, state, player);
        if (state.get(SHARD_SLOT) != ShardSlot.EMPTY) {
            world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ModItems.SKILL_SHARD)));
        }
    }
}
