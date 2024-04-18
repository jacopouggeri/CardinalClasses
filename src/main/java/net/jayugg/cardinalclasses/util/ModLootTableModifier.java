package net.jayugg.cardinalclasses.util;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.jayugg.cardinalclasses.item.ModItems;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;

public class ModLootTableModifier {
    private static final Identifier JUNGLE_TEMPLE = new Identifier("minecraft", "chests/jungle_temple");
    private static final Identifier DESERT_PYRAMID = new Identifier("minecraft", "chests/desert_pyramid");
    private static final Identifier SHIPWRECK_TREASURE = new Identifier("minecraft", "chests/shipwreck_treasure");
    private static final Identifier STRONGHOLD_LIBRARY = new Identifier("minecraft", "chests/stronghold_library");
    private static final Identifier VILLAGE_TEMPLE = new Identifier("minecraft", "chests/village/village_temple");
    private static final Identifier SPAWN_BONUS_CHEST = new Identifier("minecraft", "chests/spawn_bonus_chest");
    private static final Identifier UNDERWATER_RUIN_BIG = new Identifier("minecraft", "chests/underwater_ruin_big");
    private static final Identifier PILLAGER_OUTPOST = new Identifier("minecraft", "chests/pillager_outpost");
    private static final Identifier BASTION_OTHER = new Identifier("minecraft", "chests/bastion_other");

    private static final Identifier WOODLAND_MANSION = new Identifier("minecraft", "chests/woodland_mansion");
    private static final Identifier END_CITY_TREASURE = new Identifier("minecraft", "chests/end_city_treasure");
    private static final Identifier ANCIENT_CITY = new Identifier("minecraft", "chests/ancient_city");
    private static final Identifier ANCIENT_CITY_ICE_BOX = new Identifier("minecraft", "chests/ancient_city/ice_box");
    private static final Identifier BASTION_TREASURE = new Identifier("minecraft", "chests/bastion_treasure");

    private static final Identifier VEX = new Identifier("minecraft", "entities/vex");
    private static final Identifier WITCH = new Identifier("minecraft", "entities/witch");

    private static final Identifier WARDEN = new Identifier("minecraft", "entities/warden");
    private static final Identifier ENDER_DRAGON = new Identifier("minecraft", "entities/ender_dragon");
    private static final Identifier WITHER = new Identifier("minecraft", "entities/wither");
    private static final Identifier RAVAGER = new Identifier("minecraft", "entities/ravager");
    private static final Identifier ELDER_GUARDIAN = new Identifier("minecraft", "entities/elder_guardian");
    public static void modifyLootTables() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (JUNGLE_TEMPLE.equals(id) || DESERT_PYRAMID.equals(id) || SHIPWRECK_TREASURE.equals(id) || STRONGHOLD_LIBRARY.equals(id) || VILLAGE_TEMPLE.equals(id) || SPAWN_BONUS_CHEST.equals(id) || UNDERWATER_RUIN_BIG.equals(id) || PILLAGER_OUTPOST.equals(id) || BASTION_OTHER.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1.0f))
                        .conditionally(RandomChanceLootCondition.builder(0.1f))
                        .with(ItemEntry.builder(ModItems.SKILL_FRAGMENT))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 2.0f)).build());
                tableBuilder.pool(poolBuilder);
            }
            if (WOODLAND_MANSION.equals(id) || END_CITY_TREASURE.equals(id) || ANCIENT_CITY.equals(id) || ANCIENT_CITY_ICE_BOX.equals(id) || BASTION_TREASURE.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1.0f))
                        .conditionally(RandomChanceLootCondition.builder(0.2f))
                        .with(ItemEntry.builder(ModItems.SKILL_SHARD))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)).build());
                tableBuilder.pool(poolBuilder);
            }
            if (VEX.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1.0f))
                        .conditionally(RandomChanceLootCondition.builder(0.1f))
                        .with(ItemEntry.builder(ModItems.SKILL_FRAGMENT))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)).build());
                tableBuilder.pool(poolBuilder);
            }
            if (WITCH.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(2.0f))
                        .conditionally(RandomChanceLootCondition.builder(0.05f))
                        .with(ItemEntry.builder(ModItems.SKILL_FRAGMENT))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)).build());
                tableBuilder.pool(poolBuilder);
            }
            if (WARDEN.equals(id) || ENDER_DRAGON.equals(id) || WITHER.equals(id) || RAVAGER.equals(id) || ELDER_GUARDIAN.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1.0f))
                        .conditionally(RandomChanceLootCondition.builder(0.4f))
                        .with(ItemEntry.builder(ModItems.SKILL_SHARD))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)).build());
                tableBuilder.pool(poolBuilder);
            }
        });
    }
}
