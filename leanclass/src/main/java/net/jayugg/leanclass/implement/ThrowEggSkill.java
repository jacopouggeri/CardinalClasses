package net.jayugg.leanclass.implement;

import net.jayugg.leanclass.base.ActiveSkill;
import net.jayugg.leanclass.base.SkillCooldownHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.EggEntity;
import net.minecraft.item.Item;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ThrowEggSkill extends ActiveSkill {

    public ThrowEggSkill(String id, Item icon, SkillCooldownHelper cooldownHelper) {
        super(id, icon, cooldownHelper);
    }

    public ThrowEggSkill(String id, Item icon, SkillCooldownHelper cooldownHelper, int color) {
        super(id, icon, cooldownHelper, color, true);
    }

    @Override
    public void skillEffect(PlayerEntity player, int level) {
        World world = player.world;
        if (!world.isClient) {
            EggEntity eggEntity = new EggEntity(world, player);
            Vec3d vec3d = player.getRotationVec(1.0F);
            eggEntity.setVelocity(vec3d.x, vec3d.y, vec3d.z, 1.5F + 0.5F*level, 1.0F);
            world.spawnEntity(eggEntity);
        }
    }
}
