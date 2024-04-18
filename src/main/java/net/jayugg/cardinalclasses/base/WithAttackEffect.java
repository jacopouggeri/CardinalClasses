package net.jayugg.cardinalclasses.base;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public interface WithAttackEffect {
    void activateEffect(LivingEntity target, PlayerEntity attacker);
}
