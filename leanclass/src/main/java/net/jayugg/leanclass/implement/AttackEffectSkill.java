package net.jayugg.leanclass.implement;

import net.jayugg.leanclass.modules.AbilityType;
import net.jayugg.leanclass.modules.PlayerSkill;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;

public abstract class AttackEffectSkill extends PlayerSkill {
    public AttackEffectSkill(String id, Item icon) {
        super(id, AbilityType.PASSIVE, icon);
    }

    public abstract void activateEffect(LivingEntity target, PlayerEntity player);
}
