package net.jayugg.leanclass.leanrogue.addons;

import net.jayugg.leanclass.modules.AbilityRegistry;
import net.jayugg.leanclass.modules.PlayerPerk;

public class CustomAbilities {
    public static final PlayerPerk POISON_HAND_PERK;
    public static final PlayerPerk MUFFLED_STEPS_PERK;

    static {
        POISON_HAND_PERK = new PlayerPerk("poison_hand");
        MUFFLED_STEPS_PERK = new PlayerPerk("muffled_steps");
    }
    public static void registerAbilities() {
        AbilityRegistry.registerAbility(POISON_HAND_PERK);
        AbilityRegistry.registerAbility(MUFFLED_STEPS_PERK);
    }
}
