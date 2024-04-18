package net.jayugg.cardinalclasses.test;

import net.jayugg.cardinalclasses.core.ActiveSkill;
import net.jayugg.cardinalclasses.core.SkillCooldownHelper;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.item.Item;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ThrowFireballSkill extends ActiveSkill {

    public ThrowFireballSkill(String id, Item icon, SkillCooldownHelper cooldownHelper, int color) {
        super(id, icon, cooldownHelper, color);
    }

    @Override
    public void skillEffect(PlayerEntity player, int level) {
        World world = player.world;
        if (!world.isClient) {
            Vec3d vec3d = player.getRotationVec(1.0F); // get the player's look vector (x, y, z
            FireballEntity fireballEntity = new FireballEntity(world, player, vec3d.x, vec3d.y, vec3d.z, level);
            fireballEntity.setPosition(player.getX() + vec3d.x * 4.0, player.getBodyY(0.5) + 0.5, fireballEntity.getZ() + vec3d.z * 4.0);
            world.spawnEntity(fireballEntity);
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 20, 4));
        }
    }
}
