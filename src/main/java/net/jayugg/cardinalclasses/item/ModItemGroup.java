package net.jayugg.cardinalclasses.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.jayugg.cardinalclasses.block.ModBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static net.jayugg.cardinalclasses.CardinalClasses.MOD_ID;

public class ModItemGroup {
    public static ItemGroup MOD_GROUP;
    public static void registerItemGroup() {
        MOD_GROUP = FabricItemGroup.builder(new Identifier(MOD_ID, MOD_ID))
                .displayName(Text.literal("Cardinal Classes"))
                .icon(() -> new ItemStack(ModBlocks.SKILL_ALTAR.asItem())).build();
    }

}