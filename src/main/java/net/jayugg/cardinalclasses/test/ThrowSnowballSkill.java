package net.jayugg.cardinalclasses.test;

import net.jayugg.cardinalclasses.core.ActiveSkill;
import net.jayugg.cardinalclasses.core.SkillCooldownHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.item.Item;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ThrowSnowballSkill extends ActiveSkill {

    public ThrowSnowballSkill(String id, Item icon, SkillCooldownHelper cooldownHelper, int color) {
        super(id, icon, cooldownHelper, color, true);
    }

    @Override
    public boolean skillEffect(PlayerEntity player, int level) {
        World world = player.world;
        if (!world.isClient) {
            Vec3d vec3d = player.getRotationVec(1.0F);
            SnowballEntity snowballEntity = new SnowballEntity(world, player);
            snowballEntity.setVelocity(vec3d.x, vec3d.y, vec3d.z, 1.5F + 0.5F*level, 1.0F);
            world.spawnEntity(snowballEntity);
        }
        return true;
    }
}
