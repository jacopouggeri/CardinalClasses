package net.jayugg.cardinalclasses.core;

import net.minecraft.item.Item;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public abstract class PlayerAbility extends PlayerAddon {
    private final int minLevel;
    private final int maxLevel;
    private final AbilityType type;
    public PlayerAbility(String id, int minLevel, int maxLevel, AbilityType type, Item icon) {
        super(id, icon);
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
        this.type = type;
    }

    public AbilityType getType() { return type; }

    public MutableText getDescription(int level) {
        String levelDescriptor = level == 1 ? "" : "_" + level;
        return Text.translatable(getTranslationKey() + levelDescriptor + ".desc");
    }

}

