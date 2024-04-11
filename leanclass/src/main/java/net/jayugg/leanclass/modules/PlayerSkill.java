package net.jayugg.leanclass.modules;

import static net.jayugg.leanclass.LeanClass.MOD_ID;

public class PlayerSkill extends PlayerAbility {
    public static final int SKILL_MAX_LEVEL = 3;
    public PlayerSkill(String id, AbilityType type) {
        super(id, 0, SKILL_MAX_LEVEL, type);
    }

    @Override
    String getTranslationKey() {
        return String.format("skill.%s.%s", MOD_ID, id);
}
}
