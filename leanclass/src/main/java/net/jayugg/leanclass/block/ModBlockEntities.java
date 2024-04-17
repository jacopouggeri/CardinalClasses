package net.jayugg.leanclass.block;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static net.jayugg.leanclass.LeanClass.MOD_ID;


public class ModBlockEntities {
    public static BlockEntityType<ShardHolderBlockEntity> SHARD_HOLDER;

    public static void registerBlockEntities() {
        SHARD_HOLDER = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, "shard_holder"),
                FabricBlockEntityTypeBuilder.create(
                ShardHolderBlockEntity::new, ModBlocks.SKILL_ALTAR
                ).build(null));
    }
}
