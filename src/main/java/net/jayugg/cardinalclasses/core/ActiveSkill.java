package net.jayugg.cardinalclasses.core;

import net.jayugg.cardinalclasses.component.ActiveSkillComponent;
import net.jayugg.cardinalclasses.component.ModComponents;
import net.jayugg.cardinalclasses.util.PlayerClassManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;

import static net.jayugg.cardinalclasses.CardinalClasses.LOGGER;

public class ActiveSkill extends PlayerSkill {
    private final int color;
    private final SkillCooldownHelper cooldownHelper;
    private final boolean spammable;
    public ActiveSkill(String id, Item icon, SkillCooldownHelper cooldownHelper) {
        super(id, AbilityType.ACTIVE, icon);
        this.cooldownHelper = cooldownHelper;
        this.color = 0xFFFFFF;
        this.spammable = false;
    }

    public ActiveSkill(String id, Item icon, SkillCooldownHelper cooldownHelper, int color) {
        super(id, AbilityType.ACTIVE, icon);
        this.cooldownHelper = cooldownHelper;
        this.color = color;
        this.spammable = false;
    }
    public ActiveSkill(String id, Item icon, SkillCooldownHelper cooldownHelper, int color, boolean spammable) {
        super(id, AbilityType.ACTIVE, icon);
        this.cooldownHelper = cooldownHelper;
        this.color = color;
        this.spammable = spammable;
    }

    public void use(PlayerEntity player) {
        ActiveSkillComponent component = ModComponents.ACTIVE_SKILLS_COMPONENT.get(player);
        PlayerClass playerClass = PlayerClassManager.getClass(player);
        if (playerClass == null) { return; }
        SkillSlot slot = playerClass.getSkillSlot(this);
        int skillLevel = PlayerClassManager.getSkillLevel(player, AbilityType.ACTIVE, slot);
        if (skillLevel == 0) {
            return;
        }
        long lastUsed = component.getLastUsed(slot);
        long worldTime = player.world.getTime();
        if (cooldownHelper.useSkill(worldTime, lastUsed, skillLevel)) {
            // LOGGER.warn("Skill {} is on cooldown", slot);
            // Update the last used time by adding the charge time
            int chargeTime = cooldownHelper.getChargeTime(skillLevel);
            int maxCharges = cooldownHelper.getMaxCharges(skillLevel);
            int maxChargeTime = chargeTime * maxCharges;
            if ((worldTime - lastUsed) > maxChargeTime) {
                lastUsed = worldTime - maxChargeTime + chargeTime;
            } else {
                lastUsed = lastUsed + chargeTime;
            }
            component.setLastUsed(slot, lastUsed);
            LOGGER.warn("Using skill {}", slot);
            LOGGER.warn("Last used: {}", lastUsed);
            LOGGER.warn("Charges: {}", cooldownHelper.getCharges(worldTime, lastUsed, skillLevel));
            skillEffect(player, skillLevel);
        }
    }

    public SkillCooldownHelper getCooldownHelper() {
        return cooldownHelper;
    }

    public void skillEffect(PlayerEntity player, int level) {}

    public int getColor() {
        return color;
    }

    public boolean isSpammable() {
        return spammable;
    }
}
