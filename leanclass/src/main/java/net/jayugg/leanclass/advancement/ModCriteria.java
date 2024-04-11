package net.jayugg.leanclass.advancement;

import net.minecraft.advancement.criterion.Criteria;

public class ModCriteria {
    public static ObtainClassCriterion OBTAIN_CLASS = Criteria.register(new ObtainClassCriterion());
    public static void registerCriteria() {
        // This method does nothing but ensures that the static initializer block is executed
    }
}
