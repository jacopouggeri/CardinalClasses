package net.jayugg.leanclass.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
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
        pack.addProvider(AdvancementsProvider::new);
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

    static class AdvancementsProvider extends FabricAdvancementProvider {
        protected AdvancementsProvider(FabricDataOutput dataGenerator) {
            super(dataGenerator);
        }

        @Override
        public void generateAdvancement(Consumer<Advancement> consumer) {
            Advancement rootAdvancement = Advancement.Builder.create()
                    .display(
                            Items.CONDUIT, // The display icon
                            Text.literal("Classer!"), // The title
                            Text.literal("Obtain a Class"), // The description
                            new Identifier("textures/gui/advancements/backgrounds/adventure.png"), // Background image used
                            AdvancementFrame.TASK, // Options: TASK, CHALLENGE, GOAL
                            false, // Show toast top right
                            false, // Announce to chat
                            false // Hidden in the advancement tab
                    )
                    .rewards(AdvancementRewards.Builder.experience(1000))
                    // The first string used in conditions is the name referenced by other advancements when they want to have 'requirements'
                    .criterion("classer",  new ObtainClassCriterion.Conditions(EntityPredicate.Extended.EMPTY, null))
                    .build(consumer, MOD_ID + "/root");

            for (String playerClassId : PlayerClassRegistry.getClassIds()) {
                PlayerClass playerClass = PlayerClassRegistry.getPlayerClass(playerClassId);

                Advancement playerClassAdvancement = Advancement.Builder.create().parent(rootAdvancement)
                        .display(
                                playerClass.getIcon(), // The display icon
                                playerClass.getName(), // The title
                                playerClass.getDescription(), // The description
                                new Identifier("textures/gui/advancements/backgrounds/adventure.png"), // Background image used
                                AdvancementFrame.TASK, // Options: TASK, CHALLENGE, GOAL
                                true, // Show toast top right
                                true, // Announce to chat
                                false // Hidden in the advancement tab
                        )
                        // The first string used in conditions is the name referenced by other advancements when they want to have 'requirements'
                        .criterion(String.format("class_%s", playerClassId), new ObtainClassCriterion.Conditions(EntityPredicate.Extended.EMPTY, playerClass))
                        .build(consumer, MOD_ID + String.format("/class_%s", playerClassId));

                for (PerkSlot perkSlot : playerClass.getPerks().keySet()) {
                    PlayerPerk playerPerk = playerClass.getPerks().get(perkSlot);
                    String perkId = playerPerk.getId();
                    Advancement parentAdvancement = playerClassAdvancement;
                    for (int i = 1; i < 3; i++) {
                        String ascensionIndicator = i == 1 ? "" : " *";
                        MutableText perkTitle = Text.literal("").append(playerPerk.getName()).append(ascensionIndicator);
                        AbstractCriterionConditions conditions = i == 1 ?
                                new ObtainClassCriterion.Conditions(EntityPredicate.Extended.EMPTY, playerClass) :
                                new AscendPerkCriterion.Conditions(EntityPredicate.Extended.EMPTY, PERKS.get(perkId));
                        Advancement.Builder builder = Advancement.Builder.create().parent(parentAdvancement)
                                .display(
                                        playerPerk.getIcon(),
                                        perkTitle,
                                        playerPerk.getDescription(i),
                                        null, // children to parent advancements don't need a background set
                                        i == 2 ? AdvancementFrame.GOAL : AdvancementFrame.TASK,
                                        i == 2,
                                        i == 2,
                                        false
                                )
                                .criterion(String.format("perk_%s_%d", perkId, i), conditions);
                        Advancement newAdvancement = builder.build(consumer, MOD_ID + String.format("/perk_%s_%d", perkId, i));
                        consumer.accept(newAdvancement);
                        parentAdvancement = newAdvancement;
                    }
                }

                // loop through skills and create advancements for each skill
                for (SkillSlot skillSlot : playerClass.getSkills().keySet()) {
                    PlayerSkill playerSkill = playerClass.getSkills().get(skillSlot);
                    String skillId = playerSkill.getId();
                    Advancement parentAdvancement = playerClassAdvancement;
                    for (int i = 1; i < 4; i++) {
                        MutableText skillTitle = Text.literal("").append(playerSkill.getName()).append(" ").append(Utils.toRomanNumerals(i));
                        Advancement.Builder builder = Advancement.Builder.create().parent(parentAdvancement)
                                .display(
                                        playerSkill.getIcon(),
                                        skillTitle,
                                        playerSkill.getDescription(i),
                                        null, // children to parent advancements don't need a background set
                                        i == 3 ? AdvancementFrame.GOAL : AdvancementFrame.TASK,
                                        true,
                                        true,
                                        false
                                )
                                .criterion(String.format("skill_%s_%d", skillId, i),
                                        new ObtainSkillCriterion.Conditions(EntityPredicate.Extended.EMPTY, SKILLS.get(skillId), i)
                                );
                        Advancement newAdvancement = builder.build(consumer, MOD_ID + String.format("/skill_%s_%d", skillId, i));
                        consumer.accept(newAdvancement);
                        parentAdvancement = newAdvancement;
                    }
                }
            }
        }
    }
}
