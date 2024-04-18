package net.jayugg.leanclass.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.jayugg.leanclass.registry.PlayerClassRegistry;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

public class ClassDataGen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();
        pack.addProvider(AdvancementProvider::new);
        pack.addProvider(ModelProvider::new);
        pack.addProvider(ModRecipeGenerator::new);
        pack.addProvider(ModLootTableGenerator::new);
    }

    public static class ModelProvider extends FabricModelProvider {

        public ModelProvider(FabricDataOutput output) {
            super(output);
        }

        @Override
        public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) { }

        @Override
        public void generateItemModels(ItemModelGenerator itemModelGenerator) {
            for (String classId : PlayerClassRegistry.CLASS_POTIONS.getRegistry().keySet()) {
                itemModelGenerator.register(PlayerClassRegistry.CLASS_POTIONS.get(classId), Models.GENERATED);
            }
        }
    }
}
