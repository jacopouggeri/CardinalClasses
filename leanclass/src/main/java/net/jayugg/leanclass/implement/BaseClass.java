package net.jayugg.leanclass.implement;

import net.jayugg.leanclass.modules.*;

import java.util.Map;

public class BaseClass extends PlayerClass {
    private static Map<SkillSlot, PlayerSkill> createSkills() {
        return Map.of(
                SkillSlot.PASSIVE1, ModAbilities.BASE_PASSIVE_SKILL,
                SkillSlot.PASSIVE2, ModAbilities.BASE_PASSIVE_SKILL,
                SkillSlot.PASSIVE3, ModAbilities.BASE_PASSIVE_SKILL,
                SkillSlot.PASSIVE4, ModAbilities.BASE_PASSIVE_SKILL,
                SkillSlot.ACTIVE1, ModAbilities.BASE_ACTIVE_SKILL,
                SkillSlot.ACTIVE2, ModAbilities.BASE_ACTIVE_SKILL,
                SkillSlot.ACTIVE3, ModAbilities.BASE_ACTIVE_SKILL,
                SkillSlot.ACTIVE4, ModAbilities.BASE_ACTIVE_SKILL
        );
    }

    private static Map<PerkSlot, PlayerPerk> createPerks() {
        return Map.of(
                PerkSlot.ALPHA, ModAbilities.BASE_PERK,
                PerkSlot.OMEGA, ModAbilities.BASE_PERK
        );
    }

    public BaseClass() {
        super("base", createSkills(), createPerks());
    }
}
