package net.jayugg.leanclass.implement;

import net.jayugg.leanclass.base.ActiveSkill;
import net.jayugg.leanclass.base.SkillCooldownHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class ThrowSnowballSkill extends ActiveSkill {

    public ThrowSnowballSkill(String id, Item icon, SkillCooldownHelper cooldownHelper) {
        super(id, icon, cooldownHelper);
    }

    public ThrowSnowballSkill(String id, Item icon, SkillCooldownHelper cooldownHelper, int color) {
        super(id, icon, cooldownHelper, color);
    }

    @Override
    public void skillEffect(PlayerEntity player, int level) {
        World world = player.world;
        if (!world.isClient) {
            SnowballEntity snowballEntity = new SnowballEntity(world, player);
            snowballEntity.setVelocity(player, player.prevPitch, player.headYaw, 0.0F, 1.0F + 0.5F*level, 1.0F);
            world.spawnEntity(snowballEntity);
        }
    }
}
