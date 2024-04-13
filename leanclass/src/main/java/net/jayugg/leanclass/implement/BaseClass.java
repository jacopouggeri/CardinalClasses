package net.jayugg.leanclass.implement;

import net.jayugg.leanclass.base.*;
import net.minecraft.item.Items;

import java.util.Map;

public class BaseClass extends PlayerClass {
    private static Map<SkillSlot, PassiveSkill> createPassiveSkills() {
        return Map.of(
                SkillSlot.NORTH, (PassiveSkill) ModAbilities.BASE_PASSIVE,
                SkillSlot.EAST, (PassiveSkill) ModAbilities.BASE_PASSIVE,
                SkillSlot.SOUTH, (PassiveSkill) ModAbilities.BASE_PASSIVE,
                SkillSlot.WEST, (PassiveSkill) ModAbilities.BASE_PASSIVE
        );
    }

    private static Map<SkillSlot, ActiveSkill> createActiveSkills() {
        return Map.of(
                SkillSlot.NORTH, (ActiveSkill) ModAbilities.BASE_ACTIVE,
                SkillSlot.EAST, (ActiveSkill) ModAbilities.BASE_ACTIVE,
                SkillSlot.SOUTH, (ActiveSkill) ModAbilities.BASE_ACTIVE,
                SkillSlot.WEST, (ActiveSkill) ModAbilities.BASE_ACTIVE
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