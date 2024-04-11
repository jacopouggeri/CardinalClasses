package net.jayugg.leanclass.modules;

import static net.jayugg.leanclass.LeanClass.MOD_ID;

public class PlayerPerk extends PlayerAbility {
    public PlayerPerk(String id) {
        super(id, 1, 2, AbilityType.PERK);
    }
    @Override
    String getTranslationKey() {
        return String.format("perk.%s.%s", MOD_ID, id);
    }
}
