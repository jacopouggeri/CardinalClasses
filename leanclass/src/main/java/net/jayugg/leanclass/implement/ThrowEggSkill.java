package net.jayugg.leanclass.implement;

import net.jayugg.leanclass.base.ActiveSkill;
import net.jayugg.leanclass.base.SkillCooldownHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.EggEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class ThrowEggSkill extends ActiveSkill {

    public ThrowEggSkill(String id, Item icon, SkillCooldownHelper cooldownHelper) {
        super(id, icon, cooldownHelper);
    }

    @Override
    public void skillEffect(PlayerEntity player) {
        World world = player.world;
        if (!world.isClient) {
            EggEntity eggEntity = new EggEntity(world, player);
            eggEntity.setVelocity(player, player.prevPitch, player.headYaw, 0.0F, 1.5F, 1.0F);
            world.spawnEntity(eggEntity);
        }
    }
}
