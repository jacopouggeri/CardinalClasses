package net.jayugg.leanclass.implement;

import net.jayugg.leanclass.base.*;
import net.minecraft.item.Items;

import java.util.Map;

public class BaseClass extends PlayerClass {
    private static Map<SkillSlot, PassiveSkill> createPassiveSkills() {
        return Map.of(
                SkillSlot.NORTH, (PassiveSkill) ModAbilities.BASE_PASSIVE_1,
                SkillSlot.EAST, (PassiveSkill) ModAbilities.BASE_PASSIVE_2,
                SkillSlot.SOUTH, (PassiveSkill) ModAbilities.BASE_PASSIVE_3,
                SkillSlot.WEST, (PassiveSkill) ModAbilities.BASE_PASSIVE_4
        );
    }

    private static Map<SkillSlot, ActiveSkill> createActiveSkills() {
        return Map.of(
                SkillSlot.NORTH, (ActiveSkill) ModAbilities.BASE_ACTIVE_1,
                SkillSlot.EAST, (ActiveSkill) ModAbilities.BASE_ACTIVE_2,
                SkillSlot.SOUTH, (ActiveSkill) ModAbilities.BASE_ACTIVE_3,
                SkillSlot.WEST, (ActiveSkill) ModAbilities.BASE_ACTIVE_4
                );
    }

    private static Map<PerkSlot, PlayerPerk> createPerks() {
        return Map.of(
                PerkSlot.ALPHA, ModAbilities.BASE_EFFECT_PERK,
                PerkSlot.OMEGA, ModAbilities.BASE_PERK
        );
    }

    public BaseClass() {
        super("base", createPassiveSkills(), createActiveSkills(), createPerks(), Items.DIRT);
    }
}