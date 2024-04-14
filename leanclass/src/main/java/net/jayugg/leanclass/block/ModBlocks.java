package net.jayugg.leanclass.block;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.jayugg.leanclass.LeanClass;
import net.jayugg.leanclass.item.ModItemGroup;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static final Block SKILL_ALTAR_PASSIVE = registerBlock("skill_altar_passive",
            new SkillAltarBlock(FabricBlockSettings.of(Material.STONE).strength(50.0f, 1200.0f).requiresTool(), true), ModItemGroup.LEAN_GROUP);
    public static final Block SKILL_ALTAR_ACTIVE = registerBlock("skill_altar_active",
            new SkillAltarBlock(FabricBlockSettings.of(Material.STONE).strength(50.0f, 1200.0f).requiresTool(), false), ModItemGroup.LEAN_GROUP);
    private static Block registerBlock(String name, Block block, ItemGroup group) {
        registerBlockItem(name, block, group);
        return Registry.register(Registries.BLOCK, new Identifier(LeanClass.MOD_ID, name), block);
    }
    private static Item registerBlockItem(String name, Block block, ItemGroup group) {
        Item blockItem = Registry.register(Registries.ITEM, new Identifier(LeanClass.MOD_ID, name), new BlockItem(block, new Item.Settings()));
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(blockItem));
        return blockItem;
    }
    public static void registerModBlocks() {
        LeanClass.LOGGER.debug("Registering Mod Blocks for " + LeanClass.MOD_ID);
    }
}
