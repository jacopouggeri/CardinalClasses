package net.jayugg.cardinalclasses.advancement;

import net.minecraft.advancement.criterion.Criteria;

public class ModCriteria {
    public static ObtainClassCriterion OBTAIN_CLASS = Criteria.register(new ObtainClassCriterion());
    public static ObtainSkillCriterion OBTAIN_SKILL = Criteria.register(new ObtainSkillCriterion());
    public static AscendPerkCriterion ASCEND_PERK = Criteria.register(new AscendPerkCriterion());
    public static void registerCriteria() {
        // This method does nothing but ensures that the static initializer block is executed
    }
}
