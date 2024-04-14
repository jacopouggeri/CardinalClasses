package net.jayugg.leanclass.item.custom;

import net.minecraft.item.Item;
import net.minecraft.util.Rarity;

public class SkillShardItem extends Item {
    public SkillShardItem(Item.Settings settings) {
        super(settings.rarity(Rarity.RARE));
    }
}
