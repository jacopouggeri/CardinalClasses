package net.jayugg.cardinalclasses.effect;

import net.jayugg.cardinalclasses.item.ModItems;
import net.jayugg.cardinalclasses.registry.PlayerClassRegistry;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static net.jayugg.cardinalclasses.CardinalClasses.MOD_ID;

public class ModBrewing {
        public static Potion SWIRLY = Registry.register(Registries.POTION, new Identifier(MOD_ID, "swirly"), new Potion());

    public static void registerBrewingRecipes() {
        BrewingRecipeRegistry.registerPotionRecipe(
                Potions.WATER,
                ModItems.SKILL_FRAGMENT,
                SWIRLY
        );
        for (ClassGrantEffect classEffect : PlayerClassRegistry.CLASS_EFFECTS.getRegistry().values()) {
            BrewingRecipeRegistry.registerPotionRecipe(
                    SWIRLY,
                    classEffect.getIcon().asItem(),
                    PlayerClassRegistry.CLASS_POTIONS.get(classEffect.getPlayerClassId())
            );
        }
    }
}
