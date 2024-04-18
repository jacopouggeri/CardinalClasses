package net.jayugg.leanclass.base;

import net.minecraft.item.Item;

public class PassiveSkill extends PlayerSkill {
    public PassiveSkill(String id, Item icon) {
        super(id, AbilityType.PASSIVE, icon);
    }
}
