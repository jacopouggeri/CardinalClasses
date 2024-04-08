package net.jayugg.leanclass.item;

import net.jayugg.leanclass.LeanClass;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroup {
    public static ItemGroup SIMPLE_GROUP;
    public static void registerItemGroup() {
        SIMPLE_GROUP = FabricItemGroup.builder(new Identifier(LeanClass.MOD_ID, "simple_rogue"))
                .displayName(Text.literal("Simple Rogue"))
                .icon(() -> new ItemStack(Items.NETHER_STAR)).build();
    }

}