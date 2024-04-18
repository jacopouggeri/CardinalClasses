package net.jayugg.cardinalclasses.item;

import net.jayugg.cardinalclasses.CardinalClasses;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import static net.jayugg.cardinalclasses.item.ModItemGroup.MOD_GROUP;

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
        return Registry.register(Registries.ITEM, new Identifier(CardinalClasses.MOD_ID, name), item);
    }

    public static void addItemsToItemGroups() {
        addToItemGroup(MOD_GROUP, SKILL_SHARD);
        addToItemGroup(MOD_GROUP, SAGE_EMERALD);
        addToItemGroup(MOD_GROUP, SKILL_FRAGMENT);
    }

    public static void addToItemGroup(ItemGroup group, Item item) {
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));
    }

    public static void registerModItems() {
        CardinalClasses.LOGGER.debug("Registering Mod Items for " + CardinalClasses.MOD_ID);
        addItemsToItemGroups();
    }
}
