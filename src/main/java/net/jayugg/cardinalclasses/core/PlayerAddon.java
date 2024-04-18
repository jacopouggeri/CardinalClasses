package net.jayugg.cardinalclasses.core;

import net.minecraft.item.Item;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public abstract class PlayerAddon {
    protected final String id;
    private final Item icon;
    public PlayerAddon(String id, Item icon) {
        this.id = id;
        this.icon = icon;
    }
    public String getId() {
        return id;
    }
    public MutableText getName() {
        return Text.translatable(getTranslationKey());
    }
    public MutableText getDescription() {
        return Text.translatable(getTranslationKey() + ".desc");
    }
    public abstract String getTranslationKey();
    public Item getIcon() {
        return icon;
    }

}
