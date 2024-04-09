package net.jayugg.leanclass.item.custom;

import net.jayugg.leanclass.implement.ModAbilities;
import net.jayugg.leanclass.modules.PlayerClassManager;
import net.jayugg.leanclass.modules.PlayerSkill;
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
            PlayerSkill skill = ModAbilities.BASE_PASSIVE_SKILL;
            boolean skillUp = PlayerClassManager.skillDown(user, skill); // Use one skill point to unlock

            if (skillUp) {

                Text message = Text.literal("You have removed a skill point! You now have "
                        + PlayerClassManager.getSkillLevel(user, skill)
                        + " points in "
                        + skill.getId()
                        + ".");

                user.sendMessage(message, false); // false means it's not a system message
                return TypedActionResult.consume(itemStack);
            }

            Text message = Text.literal("You already have "
                    + PlayerClassManager.getSkillLevel(user, skill)
                    + " points in "
                    + skill.getId()
                    + ".");
            user.sendMessage(message, false);
        }
        return TypedActionResult.pass(itemStack);
    }
}
