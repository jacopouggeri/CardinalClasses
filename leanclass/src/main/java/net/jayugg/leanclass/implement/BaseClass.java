package net.jayugg.leanclass.implement;

import net.jayugg.leanclass.modules.*;
import net.minecraft.item.Items;

import java.util.Map;

public class BaseClass extends PlayerClass {
    private static Map<SkillSlot, PlayerSkill> createSkills() {
        return Map.of(
                SkillSlot.PASSIVE1, ModAbilities.BASE_PASSIVE,
                SkillSlot.PASSIVE2, ModAbilities.BASE_PASSIVE,
                SkillSlot.PASSIVE3, ModAbilities.BASE_PASSIVE,
                SkillSlot.PASSIVE4, ModAbilities.BASE_PASSIVE,
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

    public BaseClass() {
        super("base", createSkills(), createPerks(), Items.DIRT);
    }
}