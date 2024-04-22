package net.jayugg.cardinalclasses.base;

import net.jayugg.cardinalclasses.core.ActiveSkill;
import net.jayugg.cardinalclasses.core.SkillCooldownHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class ThrowProjectileSkill<T extends ProjectileEntity> extends ActiveSkill {
    private final Supplier<T> projectileSupplier;
    final float baseSpeed;
    final float levelScaling;

    public ThrowProjectileSkill(String id, Item icon, SkillCooldownHelper cooldownHelper, int color, Supplier<T> projectileSupplier) {
        super(id, icon, cooldownHelper, color, true);
        this.projectileSupplier = projectileSupplier;
        this.baseSpeed = 1.5F;
        this.levelScaling = 0.5F;
    }

    public ThrowProjectileSkill(String id, Item icon, SkillCooldownHelper cooldownHelper, int color, Supplier<T> projectileSupplier, float baseSpeed, float levelScaling) {
        super(id, icon, cooldownHelper, color, true);
        this.projectileSupplier = projectileSupplier;
        this.baseSpeed = baseSpeed;
        this.levelScaling = levelScaling;
    }

    @Override
    public boolean skillEffect(PlayerEntity player, int level) {
        World world = player.world;
        if (!world.isClient) {
            Vec3d vec3d = player.getRotationVec(1.0F);
            T projectile = projectileSupplier.get();
            projectile.updatePosition(player.getX(), player.getEyeY() - 0.1, player.getZ());
            projectile.setVelocity(vec3d.x, vec3d.y, vec3d.z, baseSpeed + levelScaling*level, 1.0F);
            world.spawnEntity(projectile);
        }
        return true;
    }
}