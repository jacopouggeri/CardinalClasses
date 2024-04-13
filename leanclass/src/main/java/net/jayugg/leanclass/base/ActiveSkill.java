package net.jayugg.leanclass.base;

import net.jayugg.leanclass.component.ActiveSkillComponent;
import net.jayugg.leanclass.component.ModComponents;
import net.jayugg.leanclass.util.PlayerClassManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;

import static net.jayugg.leanclass.LeanClass.LOGGER;

public class ActiveSkill extends PlayerSkill {
    private final SkillCooldownHelper cooldownHelper;
    public ActiveSkill(String id, Item icon, SkillCooldownHelper cooldownHelper) {
        super(id, AbilityType.ACTIVE, icon);
        this.cooldownHelper = cooldownHelper;
    }

    public void use(PlayerEntity player) {
        ActiveSkillComponent component = ModComponents.ACTIVE_SKILLS_COMPONENT.get(player);
        SkillSlot slot = PlayerClassManager.getClass(player).getSkillSlot(this);
        long lastUsed = component.getLastUsed(slot);
        long worldTime = player.world.getTime();
        if (cooldownHelper.useSkill(worldTime, lastUsed)) {
            LOGGER.warn("Skill {} is on cooldown", slot);
            // Update the last used time by adding the charge time, but don't exceed the current world time
            lastUsed = Math.min(worldTime, lastUsed + cooldownHelper.getChargeTime());
            component.setLastUsed(slot, lastUsed);
            LOGGER.warn("Charges: {}", cooldownHelper.getCharges(worldTime, lastUsed));
            skillEffect(player);
        }
    }

    public void skillEffect(PlayerEntity player) {}
}
