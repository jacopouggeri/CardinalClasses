package net.jayugg.cardinalclasses.core;

import net.minecraft.item.Item;

import static net.jayugg.cardinalclasses.CardinalClasses.MOD_ID;

public class PlayerPerk extends PlayerAbility {
    public PlayerPerk(String id, Item icon) {
        super(id, 1, 2, AbilityType.PERK, icon);
    }
    @Override
    public String getTranslationKey() {
        return String.format("perk.%s.%s", MOD_ID, id);
    }
}
