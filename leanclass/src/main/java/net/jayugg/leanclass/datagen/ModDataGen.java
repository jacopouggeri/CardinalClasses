package net.jayugg.leanclass.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.jayugg.leanclass.Utils;
import net.jayugg.leanclass.advancement.ObtainClassCriterion;
import net.jayugg.leanclass.advancement.ObtainSkillCriterion;
import net.jayugg.leanclass.modules.SkillSlot;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.item.Items;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

import static net.jayugg.leanclass.LeanClass.MOD_ID;
import static net.jayugg.leanclass.implement.ModClasses.TEST_CLASS;
import static net.jayugg.leanclass.registry.AbilityRegistry.SKILLS;

public class ModDataGen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();

        pack.addProvider(AdvancementsProvider::new);
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
                            AdvancementFrame.CHALLENGE, // Options: TASK, CHALLENGE, GOAL
                            true, // Show toast top right
                            true, // Announce to chat
                            false // Hidden in the advancement tab
                    )
                    .rewards(AdvancementRewards.Builder.experience(1000))
                    // The first string used in criterion is the name referenced by other advancements when they want to have 'requirements'
                    .criterion("classer",  new ObtainClassCriterion.Conditions(EntityPredicate.Extended.EMPTY, null))
                    .build(consumer, MOD_ID + "/root");

            Advancement testClassAdvancement = Advancement.Builder.create().parent(rootAdvancement)
                    .display(
                            Items.COMMAND_BLOCK, // The display icon
                            Text.literal("Test Class"), // The title
                            Text.literal("A test class"), // The description
                            new Identifier("textures/gui/advancements/backgrounds/adventure.png"), // Background image used
                            AdvancementFrame.TASK, // Options: TASK, CHALLENGE, GOAL
                            true, // Show toast top right
                            true, // Announce to chat
                            false // Hidden in the advancement tab
                    )
                    // The first string used in criterion is the name referenced by other advancements when they want to have 'requirements'
                    .criterion("test_class", new ObtainClassCriterion.Conditions(EntityPredicate.Extended.EMPTY, TEST_CLASS))
                    .build(consumer, MOD_ID + "/test_class");

            // loop through skills and create advancements for each skill
            for (SkillSlot skillSlot : TEST_CLASS.getSkills().keySet()) {
                String skillId = TEST_CLASS.getSkills().get(skillSlot).getId();
                MutableText skillName = TEST_CLASS.getSkills().get(skillSlot).getName();
                MutableText skillDesc = TEST_CLASS.getSkills().get(skillSlot).getDescription();
                Advancement parentAdvancement = testClassAdvancement;
                for (int i = 1; i < 4; i++) {
                    MutableText skillTitle = Text.literal("").append(skillName).append(" ").append(Utils.toRomanNumerals(i));
                    Advancement.Builder builder = Advancement.Builder.create().parent(parentAdvancement)
                            .display(
                                    Items.DIAMOND_SWORD,
                                    skillTitle,
                                    skillDesc,
                                    null, // children to parent advancements don't need a background set
                                    AdvancementFrame.TASK,
                                    true,
                                    true,
                                    false
                            )
                            .criterion(String.format("skill_%s_%d", skillId, i),
                                    new ObtainSkillCriterion.Conditions(EntityPredicate.Extended.EMPTY, SKILLS.get(skillId), i));
                    Advancement newAdvancement = builder.build(consumer, MOD_ID + String.format("/skill_%s_%d", skillId, i));
                    consumer.accept(newAdvancement);
                    parentAdvancement = newAdvancement;
                }
            }
        }
    }
}
