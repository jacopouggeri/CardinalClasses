package com.jayugg.simple_rogue.item;

import com.jayugg.simple_rogue.SimpleRogue;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static com.jayugg.simple_rogue.item.ModItems.SKILL_SHARD;

public class ModItemGroup {
    public static ItemGroup SIMPLE_GROUP;
    public static void registerItemGroup() {
        SIMPLE_GROUP = FabricItemGroup.builder(new Identifier(SimpleRogue.MOD_ID, "simple_rogue"))
                .displayName(Text.literal("Simple Rogue"))
                .icon(() -> new ItemStack(SKILL_SHARD)).build();
    }

}