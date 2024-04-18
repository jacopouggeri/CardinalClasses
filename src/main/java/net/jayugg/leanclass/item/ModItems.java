package net.jayugg.leanclass.item;

import net.jayugg.leanclass.LeanClass;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import static net.jayugg.leanclass.item.ModItemGroup.LEAN_GROUP;

public class ModItems {

    // Texture credits: https://github.com/malcolmriley/unused-textures
    public static final Item SKILL_SHARD = registerItem("skill_shard",
            new Item(new FabricItemSettings().rarity(Rarity.UNCOMMON)));

    // Texture credits: https://github.com/malcolmriley/unused-textures
    public static final Item SAGE_EMERALD = registerItem("sage_emerald",
            new Item(new FabricItemSettings().rarity(Rarity.RARE)));

    public static final Item SKILL_FRAGMENT = registerItem("skill_fragment",
            new Item(new FabricItemSettings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(LeanClass.MOD_ID, name), item);
    }

    public static void addItemsToItemGroups() {
        addToItemGroup(LEAN_GROUP, SKILL_SHARD);
        addToItemGroup(LEAN_GROUP, SAGE_EMERALD);
        addToItemGroup(LEAN_GROUP, SKILL_FRAGMENT);
    }

    public static void addToItemGroup(ItemGroup group, Item item) {
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));
    }

    public static void registerModItems() {
        LeanClass.LOGGER.debug("Registering Mod Items for " + LeanClass.MOD_ID);
        addItemsToItemGroups();
    }
}
