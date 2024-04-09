package net.jayugg.leanclass.modules;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import static net.jayugg.leanclass.LeanClass.MOD_ID;

public abstract class PlayerAddon {
    protected final String id;
    protected final MutableText description;

    public PlayerAddon(String id) {
        this.id = id;
        this.description = Text.translatable(this.getLocalizationKey());
    }

    abstract String getLocalizationKey();
    public String getId() { return id; }
    public MutableText getDescription() { return description; }

}
