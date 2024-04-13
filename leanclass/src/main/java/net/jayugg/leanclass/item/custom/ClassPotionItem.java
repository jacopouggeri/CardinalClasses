package net.jayugg.leanclass.item.custom;

import net.jayugg.leanclass.base.PlayerClass;
import net.jayugg.leanclass.util.PlayerClassManager;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import static net.jayugg.leanclass.LeanClass.MOD_ID;

public class ClassPotionItem extends PotionItem {
    private static final int MAX_USE_TIME = 40;
    private final PlayerClass playerClass;

    public ClassPotionItem(Item.Settings settings, PlayerClass playerClass) {
        super(settings);
        this.playerClass = playerClass;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (user instanceof ServerPlayerEntity serverPlayerEntity) {
            PlayerClassManager.setPlayerClass(serverPlayerEntity, playerClass.getId());
            Criteria.CONSUME_ITEM.trigger((ServerPlayerEntity) user, stack);
        }
        if (stack.isEmpty()) {
            return new ItemStack(Items.GLASS_BOTTLE);
        }
        if (user instanceof PlayerEntity playerEntity && !((PlayerEntity) user).getAbilities().creativeMode) {
            stack.decrement(1);
            ItemStack itemStack = new ItemStack(Items.GLASS_BOTTLE);
            if (!playerEntity.getInventory().insertStack(itemStack)) {
                playerEntity.dropItem(itemStack, false);
            }
        }
        user.emitGameEvent(GameEvent.DRINK);
        return stack;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 40;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public SoundEvent getDrinkSound() {
        return SoundEvents.ENTITY_GENERIC_DRINK;
    }

    @Override
    public SoundEvent getEatSound() {
        return SoundEvents.ENTITY_GENERIC_DRINK;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return ItemUsage.consumeHeldItem(world, user, hand);
    }

    @Override
    public Text getName(ItemStack stack) {
        return Text.translatable(String.format("item.%s.class_potion",MOD_ID), playerClass.getName());
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}