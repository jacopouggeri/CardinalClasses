package net.jayugg.leanclass.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.jayugg.leanclass.util.Utils;
import net.jayugg.leanclass.advancement.AscendPerkCriterion;
import net.jayugg.leanclass.advancement.ObtainClassCriterion;
import net.jayugg.leanclass.advancement.ObtainSkillCriterion;
import net.jayugg.leanclass.base.*;
import net.jayugg.leanclass.item.ModItems;
import net.jayugg.leanclass.registry.PlayerClassRegistry;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

import static net.jayugg.leanclass.LeanClass.MOD_ID;
import static net.jayugg.leanclass.registry.AbilityRegistry.PERKS;
import static net.jayugg.leanclass.registry.AbilityRegistry.SKILLS;
import static net.jayugg.leanclass.registry.PlayerClassRegistry.CLASSES;

public class AdvancementProvider extends FabricAdvancementProvider {
    protected AdvancementProvider(FabricDataOutput dataGenerator) {
        super(dataGenerator);
    }

    @Override
    public void generateAdvancement(Consumer<Advancement> consumer) {
        NbtCompound passiveSkillNbt = new NbtCompound();
        passiveSkillNbt.putString("SkullOwner", "MHF_Cow");
        ItemStack passiveSkillIcon = new ItemStack(Items.PLAYER_HEAD);
        passiveSkillIcon.setNbt(passiveSkillNbt);
        String translationKey = "advancement." + MOD_ID;

        Advancement rootAdvancement = Advancement.Builder.create()
                .display(
                        ModItems.SKILL_SHARD, // The display icon
                        Text.translatable(translationKey + ".root"), // The title
                        Text.translatable(translationKey + ".root.desc"), // The description
                        new Identifier("textures/gui/advancements/backgrounds/adventure.png"), // Background image used
                        AdvancementFrame.CHALLENGE, // Options: TASK, CHALLENGE, GOAL
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
                            null,
                            AdvancementFrame.TASK, // Options: TASK, CHALLENGE, GOAL
                            true, // Show toast top right
                            true, // Announce to chat
                            false // Hidden in the advancement tab
                    )
                    // The first string used in conditions is the name referenced by other advancements when they want to have 'requirements'
                    .criterion(String.format("class_%s", playerClassId), new ObtainClassCriterion.Conditions(EntityPredicate.Extended.EMPTY, playerClass))
                    .build(consumer, MOD_ID + String.format("/class_%s", playerClassId));

            Advancement passiveSkillAdvancement = Advancement.Builder.create().parent(playerClassAdvancement)
                    .display(
                            passiveSkillIcon, // The display icon
                            Text.translatable(translationKey + ".passives"), // The title
                            Text.translatable(translationKey + ".passives.desc"), // The description
                            null, // Background image used
                            AdvancementFrame.GOAL, // Options: TASK, CHALLENGE, GOAL
                            false, // Show toast top right
                            false, // Announce to chat
                            false // Hidden in the advancement tab
                    )
                    // The first string used in conditions is the name referenced by other advancements when they want to have 'requirements'
                    .criterion(String.format("class_%s_passives", playerClassId), new ObtainClassCriterion.Conditions(EntityPredicate.Extended.EMPTY, playerClass))
                    .build(consumer, MOD_ID + String.format("/class_%s_passives", playerClassId));

            Advancement activeSkillAdvancement = Advancement.Builder.create().parent(playerClassAdvancement)
                    .display(
                            Items.SKELETON_SKULL, // The display icon
                            Text.translatable(translationKey + ".actives"), // The title
                            Text.translatable(translationKey + ".actives.desc"), // The description
                            null, // Background image used
                            AdvancementFrame.GOAL, // Options: TASK, CHALLENGE, GOAL
                            false, // Show toast top right
                            false, // Announce to chat
                            false // Hidden in the advancement tab
                    )
                    // The first string used in conditions is the name referenced by other advancements when they want to have 'requirements'
                    .criterion(String.format("class_%s_actives", playerClassId), new ObtainClassCriterion.Conditions(EntityPredicate.Extended.EMPTY, playerClass))
                    .build(consumer, MOD_ID + String.format("/class_%s_actives", playerClassId));

            Advancement perkAdvancement = Advancement.Builder.create().parent(playerClassAdvancement)
                    .display(
                            Items.NETHER_STAR, // The display icon
                            Text.translatable(translationKey + ".actives"), // The title
                            Text.translatable(translationKey + ".actives.desc"), // The description
                            null, // Background image used
                            AdvancementFrame.GOAL, // Options: TASK, CHALLENGE, GOAL
                            false, // Show toast top right
                            false, // Announce to chat
                            false // Hidden in the advancement tab
                    )
                    // The first string used in conditions is the name referenced by other advancements when they want to have 'requirements'
                    .criterion(String.format("class_%s_perks", playerClassId), new ObtainClassCriterion.Conditions(EntityPredicate.Extended.EMPTY, playerClass))
                    .build(consumer, MOD_ID + String.format("/class_%s_perks", playerClassId));

            for (PerkSlot perkSlot : playerClass.getPerks().keySet()) {
                PlayerPerk playerPerk = playerClass.getPerks().get(perkSlot);
                String perkId = playerPerk.getId();
                Advancement parentAdvancement = perkAdvancement;
                for (int i = 1; i < 3; i++) {
                    String ascensionIndicator = i == 1 ? "" : " ★";
                    MutableText perkTitle = Text.literal("Perk: ").append(playerPerk.getName()).append(ascensionIndicator);
                    AbstractCriterionConditions conditions = i == 1 ?
                            new ObtainClassCriterion.Conditions(EntityPredicate.Extended.EMPTY, playerClass) :
                            new AscendPerkCriterion.Conditions(EntityPredicate.Extended.EMPTY, playerClass, PERKS.get(perkId));
                    Advancement.Builder builder = Advancement.Builder.create().parent(parentAdvancement)
                            .display(
                                    playerPerk.getIcon(),
                                    perkTitle,
                                    playerPerk.getDescription(i),
                                    null, // children to parent advancements don't need a background set
                                    i == 2 ? AdvancementFrame.CHALLENGE : AdvancementFrame.TASK,
                                    i == 2,
                                    i == 2,
                                    false
                            )
                            .criterion(String.format("perk_%s_%s_%d", playerClassId, perkId, i), conditions);
                    Advancement newAdvancement = builder.build(consumer, MOD_ID + String.format("/perk_%s_%s_%d", playerClassId, perkId, i));
                    consumer.accept(newAdvancement);
                    parentAdvancement = newAdvancement;
                }
            }

            // loop through skills and create advancements for each skill
            AbilityType[] types = {AbilityType.PASSIVE, AbilityType.ACTIVE};
            for (AbilityType type : types) {
                for (SkillSlot skillSlot : playerClass.getSkills(type).keySet()) {
                    PlayerSkill playerSkill = playerClass.getSkills(type).get(skillSlot);
                    String skillId = playerSkill.getId();
                    Advancement parentAdvancement = type.equals(AbilityType.ACTIVE) ? activeSkillAdvancement : passiveSkillAdvancement;
                    for (int i = 1; i < 4; i++) {
                        MutableText skillTitle = Text.literal(skillSlot.getName() + ": ").append(playerSkill.getName()).append(" ").append(Utils.toRomanNumerals(i));
                        Advancement.Builder builder = Advancement.Builder.create().parent(parentAdvancement)
                                .display(
                                        playerSkill.getIcon(),
                                        skillTitle,
                                        playerSkill.getDescription(i),
                                        null, // children to parent advancements don't need a background set
                                        i == 3 ? AdvancementFrame.CHALLENGE : AdvancementFrame.TASK,
                                        true,
                                        true,
                                        false
                                )
                                .criterion(String.format("skill_%s_%s_%d", playerClassId, skillId, i),
                                        new ObtainSkillCriterion.Conditions(EntityPredicate.Extended.EMPTY, CLASSES.get(playerClassId), SKILLS.get(skillId), i)
                                );
                        Advancement newAdvancement = builder.build(consumer, MOD_ID + String.format("/skill_%s_%s_%d", playerClassId, skillId, i));
                        consumer.accept(newAdvancement);
                        parentAdvancement = newAdvancement;
                    }
                }
            }
        }
    }
}
