package net.jayugg.leanclass.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.jayugg.leanclass.block.ModBlocks;

public class ModLootTableGenerator extends FabricBlockLootTableProvider {
    protected ModLootTableGenerator(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        //addDropWithSilkTouch(ModBlocks.SKILL_ALTAR, ModBlocks.SKILL_ALTAR);
        //addDropWithSilkTouch(ModBlocks.SHARD_HOLDER, ModBlocks.SHARD_HOLDER);
    }
}
