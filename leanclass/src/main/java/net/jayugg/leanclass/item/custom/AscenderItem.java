package net.jayugg.leanclass.item.custom;

import net.jayugg.leanclass.base.PerkSlot;
import net.jayugg.leanclass.util.PlayerClassManager;
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
            PerkSlot perkSlot = PerkSlot.ALPHA;
            boolean ascend = PlayerClassManager.ascendPerk(user, PerkSlot.ALPHA);
            if (ascend) {
                Text message = Text.literal("You have ascended the " + perkSlot.name() + " perk.");
                user.sendMessage(message, false);
                return TypedActionResult.consume(itemStack);
            }
            Text message = Text.literal("You already have " + PlayerClassManager.getAscendedPerk(user) + " perk ascended.");
            user.sendMessage(message, false);
        }
        return TypedActionResult.pass(itemStack);
    }
}