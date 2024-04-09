package net.jayugg.leanclass.modules;

public class PlayerSkill extends PlayerAbility {
    public static final int SKILL_MAX_LEVEL = 3;
    public PlayerSkill(String id, AbilityType type) {
        super(id, 0, SKILL_MAX_LEVEL, type);
    }

}
