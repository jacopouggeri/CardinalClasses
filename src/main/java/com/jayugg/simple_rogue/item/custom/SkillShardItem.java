package com.jayugg.simple_rogue.item.custom;

import com.jayugg.simple_rogue.component.ModComponents;
import com.jayugg.simple_rogue.component.PlayerSkillComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class SkillShardItem extends Item {
    public SkillShardItem(Item.Settings settings) {
        super(settings);
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        PlayerSkillComponent skill = ModComponents.SKILL_COMPONENT.get(user);
        boolean skillUp = skill.addSkillPoint(); // Use one skill point to unlock
        if (skillUp) {
            Text message = Text.literal("You have activated the Skill Shard! You now have " + skill.getSkillPoints() + " points.");
            user.sendMessage(message, false); // false means it's not a system message
            return TypedActionResult.consume(itemStack);
        }
        Text message = Text.literal("You already have " + skill.getSkillPoints() + " skill points.");
        user.sendMessage(message, false);
        return TypedActionResult.pass(user.getStackInHand(hand));

    }
}
