package net.jayugg.leanclass.implement;

import net.jayugg.leanclass.modules.AbilityType;
import net.jayugg.leanclass.modules.PlayerSkill;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public abstract class AttackEffectSkill extends PlayerSkill {
    public AttackEffectSkill(String id) {
        super(id, AbilityType.PASSIVE);
    }

    public abstract void activateEffect(LivingEntity target, PlayerEntity player);
}
