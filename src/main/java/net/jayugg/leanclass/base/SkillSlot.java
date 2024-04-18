package net.jayugg.leanclass.base;

public enum SkillSlot {
    NORTH(1, "North"),
    EAST(2, "East"),
    SOUTH(3, "South"),
    WEST(4, "West");

    private final int value;
    private final String name;

    SkillSlot(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return this.value;
    }

    public static SkillSlot fromValue(int value) {
        for (SkillSlot slot : SkillSlot.values()) {
            if (slot.getValue() == value) {
                return slot;
            }
        }
        throw new IllegalArgumentException("Unexpected value: " + value);
    }

    public String getName() {
        return this.name;
    }
}