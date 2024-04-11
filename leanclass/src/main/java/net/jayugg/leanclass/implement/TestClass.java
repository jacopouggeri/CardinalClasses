package net.jayugg.leanclass.implement;

import net.jayugg.leanclass.modules.*;

import java.util.Map;

public class TestClass extends PlayerClass {
    private static Map<SkillSlot, PlayerSkill> createSkills() {
        return Map.of(
                SkillSlot.PASSIVE1, ModAbilities.TEST_PASSIVE_RED,
                SkillSlot.PASSIVE2, ModAbilities.TEST_PASSIVE_BLUE,
                SkillSlot.PASSIVE3, ModAbilities.TEST_PASSIVE_GREEN,
                SkillSlot.PASSIVE4, ModAbilities.TEST_PASSIVE_YELLOW,
                SkillSlot.ACTIVE1, ModAbilities.BASE_ACTIVE,
                SkillSlot.ACTIVE2, ModAbilities.BASE_ACTIVE,
                SkillSlot.ACTIVE3, ModAbilities.BASE_ACTIVE,
                SkillSlot.ACTIVE4, ModAbilities.BASE_ACTIVE
        );
    }

    private static Map<PerkSlot, PlayerPerk> createPerks() {
        return Map.of(
                PerkSlot.ALPHA, ModAbilities.BASE_EFFECT_PERK,
                PerkSlot.OMEGA, ModAbilities.BASE_PERK
        );
    }

    public TestClass() {
        super("test", createSkills(), createPerks());
    }
}
