package net.jayugg.leanclass.modules;

public abstract class PlayerAbility {
    protected final String id;
    private final int minLevel;
    private final int maxLevel;
    private final AbilityType type;
    public PlayerAbility(String id, int minLevel, int maxLevel, AbilityType type) {
        this.id = id;
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
        this.type = type;
    }

    public int getMinLevel() { return minLevel; }
    public int getMaxLevel() { return maxLevel; }
    public AbilityType getType() { return type; }
    public String getId() { return id; }

}

