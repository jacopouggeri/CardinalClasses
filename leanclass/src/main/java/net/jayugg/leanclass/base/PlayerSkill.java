package net.jayugg.leanclass.base;

import net.jayugg.leanclass.base.AbilityType;
import net.jayugg.leanclass.base.PlayerAbility;
import net.minecraft.item.Item;

import static net.jayugg.leanclass.LeanClass.MOD_ID;

public class PlayerSkill extends PlayerAbility {
    public static final int SKILL_MAX_LEVEL = 3;
    public PlayerSkill(String id, AbilityType type, Item icon) {
        super(id, 0, SKILL_MAX_LEVEL, type, icon);
    }

    @Override
    public String getTranslationKey() {
        return String.format("skill.%s.%s", MOD_ID, id);
}

}
