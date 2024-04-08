package net.jayugg.leanclass.item.custom;

import net.jayugg.leanclass.component.ModComponents;
import net.jayugg.leanclass.component.PlayerPerkComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class AscenderItem extends Item {

    public AscenderItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        if (!world.isClient) {
            PlayerPerkComponent perk = ModComponents.PERK_COMPONENT.get(user);
            boolean ascend = perk.ascend(1); // Use one skill point to unlock
            if (ascend) {
                Text message = Text.literal("You have ascended the " + perk.getCurrentSlotName() + " perk.");
                user.sendMessage(message, false); // false means it's not a system message
                return TypedActionResult.consume(itemStack);
            }
            Text message = Text.literal("You already have " + perk.getCurrentSlotName() + " perk ascended.");
            user.sendMessage(message, false);
        }
        return TypedActionResult.pass(user.getStackInHand(hand));
    }
}