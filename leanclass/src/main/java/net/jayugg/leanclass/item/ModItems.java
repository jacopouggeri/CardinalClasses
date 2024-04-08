package net.jayugg.leanclass.item;

import net.jayugg.leanclass.LeanClass;
import net.jayugg.leanclass.item.custom.AscenderItem;
import net.jayugg.leanclass.item.custom.SkillDownItem;
import net.jayugg.leanclass.item.custom.SkillShardItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static net.jayugg.leanclass.item.ModItemGroup.SIMPLE_GROUP;

public class ModItems {
    public static final Item SKILL_SHARD = registerItem("skill_shard",
            new SkillShardItem(new FabricItemSettings()));

    public static final Item SKILL_DOWN = registerItem("skill_down",
            new SkillDownItem(new FabricItemSettings()));

    public static final Item ASCENDER = registerItem("ascender",
            new AscenderItem(new FabricItemSettings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(LeanClass.MOD_ID, name), item);
    }

    public static void addItemsToItemGroups() {
        addToItemGroup(SIMPLE_GROUP, SKILL_SHARD);
        addToItemGroup(SIMPLE_GROUP, SKILL_DOWN);
        addToItemGroup(SIMPLE_GROUP, ASCENDER);
    }

    public static void addToItemGroup(ItemGroup group, Item item) {
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));
    }

    public static void registerModItems() {
        LeanClass.LOGGER.debug("Registering Mod Items for " + LeanClass.MOD_ID);

        addItemsToItemGroups();
    }
}
