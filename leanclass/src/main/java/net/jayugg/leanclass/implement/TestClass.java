package net.jayugg.leanclass.implement;

import net.jayugg.leanclass.base.*;
import net.minecraft.item.Items;

import java.util.Map;

public class TestClass extends PlayerClass {
    private static Map<SkillSlot, PassiveSkill> createPassiveSkills() {
        return Map.of(
                SkillSlot.NORTH, (PassiveSkill) ModAbilities.TEST_PASSIVE_RED,
                SkillSlot.EAST, (PassiveSkill) ModAbilities.TEST_PASSIVE_BLUE,
                SkillSlot.SOUTH, (PassiveSkill) ModAbilities.TEST_PASSIVE_GREEN,
                SkillSlot.WEST, (PassiveSkill) ModAbilities.TEST_PASSIVE_YELLOW
        );
    }

    private static Map<SkillSlot, ActiveSkill> createActiveSkills() {
        return Map.of(
                SkillSlot.NORTH, (ActiveSkill) ModAbilities.TEST_ACTIVE,
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

    public TestClass() {
        super("test", createPassiveSkills(), createActiveSkills(), createPerks(), Items.COMMAND_BLOCK);
    }
}
