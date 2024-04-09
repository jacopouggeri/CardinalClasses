package net.jayugg.leanclass.modules;

import net.minecraft.text.Text;

import static net.jayugg.leanclass.LeanClass.MOD_ID;

public abstract class PlayerAbility extends PlayerAddon {
    private final int minLevel;
    private final int maxLevel;
    private final AbilityType type;
    public PlayerAbility(String id, int minLevel, int maxLevel, AbilityType type) {
        super(id);
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
        this.type = type;
    }

    public String getLocalizationKey() {
        return String.format("%s.%s.%s", type.toString().toLowerCase(), MOD_ID, this.id);
    }

    // Getters
    public int getMinLevel() { return minLevel; }
    public int getMaxLevel() { return maxLevel; }
    public AbilityType getType() { return type; }

}

