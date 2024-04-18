package net.jayugg.cardinalclasses.block;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.jayugg.cardinalclasses.CardinalClasses;
import net.jayugg.cardinalclasses.item.ModItemGroup;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static final Block SHARD_HOLDER = registerBlock("shard_holder",
            new ShardHolderBlock(FabricBlockSettings.of(Material.STONE, MapColor.DEEPSLATE_GRAY).requiresTool().strength(3.5f, 6.0f).sounds(BlockSoundGroup.DEEPSLATE)), ModItemGroup.MOD_GROUP);
    public static final Block SKILL_ALTAR = registerBlock("skill_altar",
            new SkillAltarBlock(FabricBlockSettings.copy(SHARD_HOLDER).strength(50.0f, 1200.0f)), ModItemGroup.MOD_GROUP);
    private static Block registerBlock(String name, Block block, ItemGroup group) {
        registerBlockItem(name, block, group);
        return Registry.register(Registries.BLOCK, new Identifier(CardinalClasses.MOD_ID, name), block);
    }
    @SuppressWarnings("UnusedReturnValue")
    private static Item registerBlockItem(String name, Block block, ItemGroup group) {
        Item blockItem = Registry.register(Registries.ITEM, new Identifier(CardinalClasses.MOD_ID, name), new BlockItem(block, new Item.Settings()));
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(blockItem));
        return blockItem;
    }
    public static void registerModBlocks() {
        CardinalClasses.LOGGER.debug("Registering Mod Blocks for " + CardinalClasses.MOD_ID);
    }
}
