package net.jayugg.leanclass.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.jayugg.leanclass.base.PlayerClass;
import net.jayugg.leanclass.block.ModBlocks;
import net.jayugg.leanclass.item.ModItems;
import net.jayugg.leanclass.registry.PlayerClassRegistry;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;

import java.util.function.Consumer;

public class ModRecipeGenerator extends FabricRecipeProvider {
    public ModRecipeGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, ModItems.SKILL_FRAGMENT,
                RecipeCategory.MISC, ModItems.SKILL_SHARD);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.SAGE_EMERALD, 1)
                .pattern("FFF")
                .pattern("FEF")
                .pattern("FFF")
                .input('F', ModItems.SKILL_FRAGMENT)
                .input('E', Items.EMERALD)
                .criterion("has_skill_fragment", conditionsFromItem(ModItems.SKILL_FRAGMENT))
                .criterion("has_emerald", conditionsFromItem(Items.EMERALD))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.SKILL_ALTAR.asItem(), 1)
                .pattern(" F ")
                .pattern("ETE")
                .pattern("TTT")
                .input('F', ModItems.SKILL_FRAGMENT)
                .input('T', Items.DEEPSLATE_TILES)
                .input('E', ModItems.SAGE_EMERALD)
                .criterion("has_skill_fragment", conditionsFromItem(ModItems.SKILL_FRAGMENT))
                .criterion("has_deepslate_tiles", conditionsFromItem(Items.DEEPSLATE_TILES))
                .criterion("has_sage_emerald", conditionsFromItem(ModItems.SAGE_EMERALD))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.SHARD_HOLDER.asItem(), 1)
                .pattern(" F ")
                .pattern(" T ")
                .pattern("TTT")
                .input('F', ModItems.SKILL_FRAGMENT)
                .input('T', Items.DEEPSLATE_TILES)
                .criterion("has_skill_fragment", conditionsFromItem(ModItems.SKILL_FRAGMENT))
                .criterion("has_deepslate_tiles", conditionsFromItem(Items.DEEPSLATE_TILES))
                .offerTo(exporter);

        for (PlayerClass playerClass : PlayerClassRegistry.CLASSES.getRegistry().values()) {
            Item classPotion = PlayerClassRegistry.CLASS_POTIONS.get(playerClass.getId());
            offerQuadrupleInputShapelessRecipe(exporter, classPotion, ModItems.SKILL_FRAGMENT, Items.HONEY_BOTTLE,
                    Items.POISONOUS_POTATO, playerClass.getIcon(),
                    RecipeCategory.BREWING);
        }
    }

    @SuppressWarnings("SameParameterValue")
    private void offerQuadrupleInputShapelessRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible input1, ItemConvertible input2, ItemConvertible input3, ItemConvertible input4, RecipeCategory category) {
        ShapelessRecipeJsonBuilder.create(category, output, 1)
                .input(input1)
                .input(input2)
                .input(input3)
                .input(input4)
                .criterion("has_input_item1", conditionsFromItem(input1))
                .criterion("has_input_item2", conditionsFromItem(input2))
                .offerTo(exporter);
    }
}
