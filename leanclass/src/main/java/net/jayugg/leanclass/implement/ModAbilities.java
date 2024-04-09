package net.jayugg.leanclass.implement;

import net.jayugg.leanclass.modules.AbilityRegistry;
import net.jayugg.leanclass.modules.AbilityType;
import net.jayugg.leanclass.modules.PlayerPerk;
import net.jayugg.leanclass.modules.PlayerSkill;

public class ModAbilities {
    public static final PlayerSkill BASE_PASSIVE_SKILL = new PlayerSkill("base_passive", AbilityType.PASSIVE);
    public static final PlayerSkill BASE_ACTIVE_SKILL = new PlayerSkill("base_active", AbilityType.ACTIVE);
    public static final PlayerPerk BASE_PERK = new PlayerPerk("base_perk");
    public static void registerAbilities() {
        AbilityRegistry.registerAbility(BASE_PASSIVE_SKILL);
        AbilityRegistry.registerAbility(BASE_ACTIVE_SKILL);
        AbilityRegistry.registerAbility(BASE_PERK);
    }
}