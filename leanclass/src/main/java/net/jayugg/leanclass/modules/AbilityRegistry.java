package net.jayugg.leanclass.modules;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AbilityRegistry {

    private static final Map<String, PlayerAbility> REGISTERED_ABILITIES = new HashMap<>();

    public static void registerAbility(PlayerAbility playerAbility) {
        REGISTERED_ABILITIES.put(playerAbility.getId(), playerAbility);
    }

    public static PlayerAbility getAbilityByName(String name) {
        return REGISTERED_ABILITIES.get(name);
    }

    public Collection<PlayerAbility> getAllAbilities() {
        return REGISTERED_ABILITIES.values();
    }
}
