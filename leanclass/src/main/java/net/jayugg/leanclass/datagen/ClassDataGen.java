package net.jayugg.leanclass.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.jayugg.leanclass.Utils;
import net.jayugg.leanclass.advancement.ObtainClassCriterion;
import net.jayugg.leanclass.advancement.AscendPerkCriterion;
import net.jayugg.leanclass.advancement.ObtainSkillCriterion;
import net.jayugg.leanclass.modules.*;
import net.jayugg.leanclass.registry.PlayerClassRegistry;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.Items;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

import static net.jayugg.leanclass.LeanClass.MOD_ID;
import static net.jayugg.leanclass.registry.AbilityRegistry.PERKS;
import static net.jayugg.leanclass.registry.AbilityRegistry.SKILLS;

public class ClassDataGen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();
        pack.addProvider(AdvancementProvider::new);
        pack.addProvider(ModelProvider::new);
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
