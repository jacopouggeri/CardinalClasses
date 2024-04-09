package net.jayugg.leanclass.leanrogue.addons;

import net.jayugg.leanclass.modules.PlayerPerk;
import net.jayugg.leanclass.registry.AbilityRegistry;

public class CustomAbilities {
    public static final PlayerPerk POISON_HAND_PERK = AbilityRegistry.registerPerk(new PlayerPerk("poison_hand"));
    public static final PlayerPerk MUFFLED_STEPS_PERK = AbilityRegistry.registerPerk(new PlayerPerk("muffled_steps"));

    public static void registerAbilities() {
        // This method does nothing but ensures that the static initializer block is executed
    }
}
