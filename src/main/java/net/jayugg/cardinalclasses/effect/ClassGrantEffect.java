package net.jayugg.cardinalclasses.effect;

import net.jayugg.cardinalclasses.core.PlayerClass;
import net.jayugg.cardinalclasses.util.PlayerClassManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.math.random.Random;

public class ClassGrantEffect extends StatusEffect {
    private final String playerClassId;
    private final ItemConvertible icon;
    private final Random random = Random.create();

    public ClassGrantEffect(PlayerClass playerClass) {
        super(StatusEffectCategory.NEUTRAL, playerClass.getColor());
        this.playerClassId = playerClass.getId();
        this.icon = playerClass.getIcon();
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return this.random.nextFloat() < 0.01 + 0.005 * (double)amplifier;
    }

    // This method is called when it applies the status effect. We implement custom functionality here.
    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerEntity) {
            PlayerClassManager.setPlayerClass((PlayerEntity) entity, playerClassId);
            entity.clearStatusEffects();
        }
    }

    public String getPlayerClassId() {
        return playerClassId;
    }

    public ItemConvertible getIcon() {
        return icon;
    }
}
