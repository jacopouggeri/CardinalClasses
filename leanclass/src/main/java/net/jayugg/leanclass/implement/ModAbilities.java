package net.jayugg.leanclass.implement;

import net.jayugg.leanclass.registry.AbilityRegistry;
import net.jayugg.leanclass.modules.AbilityType;
import net.jayugg.leanclass.modules.PlayerPerk;
import net.jayugg.leanclass.modules.PlayerSkill;

public class ModAbilities {
    public static final PlayerSkill BASE_PASSIVE_SKILL = AbilityRegistry.registerSkill(
            new PlayerSkill("base_passive", AbilityType.PASSIVE));
    public static final PlayerSkill BASE_ACTIVE_SKILL = AbilityRegistry.registerSkill(
            new PlayerSkill("base_active", AbilityType.ACTIVE));
    public static final PlayerPerk BASE_PERK = AbilityRegistry.registerPerk(
            new PlayerPerk("base_perk"));

    public static void registerAbilities() {
        // This method does nothing but ensures that the static initializer block is executed
    }
}