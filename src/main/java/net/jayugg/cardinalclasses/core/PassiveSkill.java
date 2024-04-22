package net.jayugg.cardinalclasses.core;

import net.minecraft.item.ItemConvertible;

public class PassiveSkill extends PlayerSkill {
    public PassiveSkill(String id, ItemConvertible icon) {
        super(id, AbilityType.PASSIVE, icon);
    }
}
