package net.jayugg.leanclass.implement;

import net.jayugg.leanclass.modules.PlayerClassManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.world.ServerWorld;

public class ExampleAttackSkill extends AttackEffectSkill {
    private final int fireworkColor;
    public ExampleAttackSkill(String id, int color, Item icon) {
        super(id, icon);
        fireworkColor = color;
    }
    public void activateEffect(LivingEntity target, PlayerEntity player) {
        if (!target.world.isClient && target.world instanceof ServerWorld serverWorld) {
            int level = PlayerClassManager.getSkillLevel(player, this).getAsInt();
            ItemStack fireworkStack = new ItemStack(Items.FIREWORK_ROCKET);
            NbtCompound fireworks = new NbtCompound();
            NbtCompound fireworkCompound = new NbtCompound();
            NbtList explosions = new NbtList();
            NbtCompound explosion = new NbtCompound();

            explosion.putInt("Type", level); // 0 for a small ball, see other values for different shapes
            explosion.putIntArray("Colors", new int[]{fireworkColor}); // Red fireworks, see other values for different colors

            explosions.add(explosion);
            fireworkCompound.put("Explosions", explosions);
            fireworks.put("Fireworks", fireworkCompound);
            fireworkStack.setNbt(fireworks);

            FireworkRocketEntity fireworkRocketEntity = new FireworkRocketEntity(target.world, fireworkStack, target);
            fireworkRocketEntity.setPosition(target.getX(), target.getY(), target.getZ());
            serverWorld.spawnEntity(fireworkRocketEntity);
        }
    }
}
