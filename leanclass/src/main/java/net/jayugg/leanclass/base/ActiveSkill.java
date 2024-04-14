package net.jayugg.leanclass.base;

import net.jayugg.leanclass.component.ActiveSkillComponent;
import net.jayugg.leanclass.component.ModComponents;
import net.jayugg.leanclass.util.PlayerClassManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;

public class ActiveSkill extends PlayerSkill {
    private final int color;
    private final SkillCooldownHelper cooldownHelper;
    public ActiveSkill(String id, Item icon, SkillCooldownHelper cooldownHelper) {
        super(id, AbilityType.ACTIVE, icon);
        this.cooldownHelper = cooldownHelper;
        this.color = 0xFFFFFF;
    }

    public ActiveSkill(String id, Item icon, SkillCooldownHelper cooldownHelper, int color) {
        super(id, AbilityType.ACTIVE, icon);
        this.cooldownHelper = cooldownHelper;
        this.color = color;
    }

    public void use(PlayerEntity player) {
        ActiveSkillComponent component = ModComponents.ACTIVE_SKILLS_COMPONENT.get(player);
        SkillSlot slot = PlayerClassManager.getClass(player).getSkillSlot(this);
        int skillLevel = PlayerClassManager.getSkillLevel(player, AbilityType.ACTIVE, slot);
        if (skillLevel == 0) {
            return;
        }
        long lastUsed = component.getLastUsed(slot);
        long worldTime = player.world.getTime();
        if (cooldownHelper.useSkill(worldTime, lastUsed, skillLevel)) {
            // LOGGER.warn("Skill {} is on cooldown", slot);
            // Update the last used time by adding the charge time
            lastUsed = lastUsed + cooldownHelper.getChargeTime();
            component.setLastUsed(slot, lastUsed);
            // LOGGER.warn("Charges: {}", cooldownHelper.getCharges(worldTime, lastUsed));
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
}
