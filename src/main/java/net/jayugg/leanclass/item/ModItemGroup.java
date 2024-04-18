package net.jayugg.leanclass.item;

import net.jayugg.leanclass.LeanClass;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroup {
    public static ItemGroup LEAN_GROUP;
    public static void registerItemGroup() {
        LEAN_GROUP = FabricItemGroup.builder(new Identifier(LeanClass.MOD_ID, "lean_class"))
                .displayName(Text.literal("LeanClass"))
                .icon(() -> new ItemStack(ModItems.SKILL_SHARD)).build();
    }

}