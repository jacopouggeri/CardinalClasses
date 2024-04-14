package net.jayugg.leanclass.implement;

import net.jayugg.leanclass.base.ActiveSkill;
import net.jayugg.leanclass.base.SkillCooldownHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.thrown.EggEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class ThrowArrowSkill extends ActiveSkill {

    public ThrowArrowSkill(String id, Item icon, SkillCooldownHelper cooldownHelper) {
        super(id, icon, cooldownHelper);
    }

    public ThrowArrowSkill(String id, Item icon, SkillCooldownHelper cooldownHelper, int color) {
        super(id, icon, cooldownHelper, color);
    }

    @Override
    public void skillEffect(PlayerEntity player, int level) {
        World world = player.world;
        if (!world.isClient) {
            ArrowEntity eggEntity = new ArrowEntity(world, player);
            eggEntity.setVelocity(player, player.prevPitch, player.headYaw, 0.0F, (float) level, 1.0F);
            world.spawnEntity(eggEntity);
        }
    }
}
