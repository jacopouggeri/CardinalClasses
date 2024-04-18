package net.jayugg.cardinalclasses.base;

import net.jayugg.cardinalclasses.core.PassiveSkill;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;

public abstract class AttackEffectSkill extends PassiveSkill implements WithAttackEffect {
    public AttackEffectSkill(String id, Item icon) {
        super(id, icon);
    }

    public abstract void activateEffect(LivingEntity target, PlayerEntity player);
}
