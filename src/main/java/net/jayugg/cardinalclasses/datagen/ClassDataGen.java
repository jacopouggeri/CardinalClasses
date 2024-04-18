package net.jayugg.cardinalclasses.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class ClassDataGen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();
        pack.addProvider(AdvancementProvider::new);
        pack.addProvider(ModRecipeGenerator::new);
        pack.addProvider(ModLootTableGenerator::new);
        pack.addProvider(LanguageProvider::new);
    }
}
