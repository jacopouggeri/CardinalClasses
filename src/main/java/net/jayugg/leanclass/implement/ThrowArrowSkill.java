package net.jayugg.leanclass.implement;

import net.jayugg.leanclass.base.ActiveSkill;
import net.jayugg.leanclass.base.SkillCooldownHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ThrowArrowSkill extends ActiveSkill {

    public ThrowArrowSkill(String id, Item icon, SkillCooldownHelper cooldownHelper, int color) {
        super(id, icon, cooldownHelper, color);
    }

    @Override
    public void skillEffect(PlayerEntity player, int level) {
        World world = player.world;
        if (!world.isClient) {
            ArrowEntity arrowEntity = new ArrowEntity(world, player);
            Vec3d vec3d = player.getRotationVec(1.0F);
            arrowEntity.setVelocity(vec3d.x, vec3d.y, vec3d.z, 2.5F + 1F * level, 1.0F);
            arrowEntity.setNoClip(false);
            world.spawnEntity(arrowEntity);
        }
    }
}
