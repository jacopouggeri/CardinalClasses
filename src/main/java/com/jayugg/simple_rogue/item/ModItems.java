package com.jayugg.simple_rogue.item;

import com.jayugg.simple_rogue.SimpleRogue;
import com.jayugg.simple_rogue.item.custom.SkillShardItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static com.jayugg.simple_rogue.item.ModItemGroup.SIMPLE_GROUP;

public class ModItems {
    public static final Item SKILL_SHARD = registerItem("skill_shard",
            new SkillShardItem(new FabricItemSettings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(SimpleRogue.MOD_ID, name), item);
    }

    public static void addItemsToItemGroups() {
        addToItemGroup(SIMPLE_GROUP, SKILL_SHARD);
    }

    public static void addToItemGroup(ItemGroup group, Item item) {
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));
    }

    public static void registerModItems() {
        SimpleRogue.LOGGER.debug("Registering Mod Items for " + SimpleRogue.MOD_ID);

        addItemsToItemGroups();
    }
}
