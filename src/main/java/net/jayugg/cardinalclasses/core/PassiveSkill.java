package net.jayugg.cardinalclasses.core;

import net.minecraft.item.Item;

public class PassiveSkill extends PlayerSkill {
    public PassiveSkill(String id, Item icon) {
        super(id, AbilityType.PASSIVE, icon);
    }
}
