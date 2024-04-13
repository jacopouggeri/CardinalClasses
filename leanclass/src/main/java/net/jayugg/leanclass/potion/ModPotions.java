package net.jayugg.leanclass.potion;

import net.jayugg.leanclass.item.ModItems;
import net.jayugg.leanclass.mixin.BrewingRecipeRegistryMixin;
import net.jayugg.leanclass.modules.PlayerClass;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static net.jayugg.leanclass.LeanClass.MOD_ID;

public class ModPotions {

    public static final Potion SWIRLY_POTION =
            Registry.register(Registries.POTION, new Identifier(MOD_ID, "swirly_potion"),
                    new Potion(new StatusEffectInstance(StatusEffects.WEAKNESS, 1000),
                            new StatusEffectInstance(StatusEffects.LUCK, 1000)));

    public static void registerPotions(){
    }

    public static void registerPotionsRecipes(){
        BrewingRecipeRegistryMixin.invokeRegisterPotionRecipe(Potions.MUNDANE, ModItems.SKILL_SHARD, ModPotions.SWIRLY_POTION);
    }

    public static void registerClassPotionRecipe(PlayerClass playerClass, Item potionItem) {
        Item swirlyPotionItem = PotionUtil.setPotion(new ItemStack(Items.POTION), ModPotions.SWIRLY_POTION).getItem();
        BrewingRecipeRegistryMixin.invokeRegisterItemRecipe(swirlyPotionItem, playerClass.getIcon(), potionItem);
    }
}