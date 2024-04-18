package net.jayugg.leanclass.implement;

import net.jayugg.leanclass.base.PassiveSkill;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;

public abstract class AttackEffectSkill extends PassiveSkill {
    public AttackEffectSkill(String id, Item icon) {
        super(id, icon);
    }

    public abstract void activateEffect(LivingEntity target, PlayerEntity player);
}
