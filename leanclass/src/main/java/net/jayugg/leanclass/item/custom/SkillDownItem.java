package net.jayugg.leanclass.item.custom;

import net.jayugg.leanclass.component.ModComponents;
import net.jayugg.leanclass.component.PlayerSkillComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class SkillDownItem extends Item {
    public SkillDownItem(Item.Settings settings) {
        super(settings);
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        if (!world.isClient) {
            PlayerSkillComponent skill = ModComponents.SKILL_COMPONENT.get(user);
            boolean skillUp = skill.removeSkillPoint(); // Use one skill point to unlock
            if (skillUp) {
                Text message = Text.literal("You have removed a skill point! You now have " + skill.getSkillPoints() + " points.");
                user.sendMessage(message, false); // false means it's not a system message
                return TypedActionResult.consume(itemStack);
            }
            Text message = Text.literal("You already have " + skill.getSkillPoints() + " skill points.");
            user.sendMessage(message, false);
        }
        return TypedActionResult.pass(user.getStackInHand(hand));
    }
}
