package net.jayugg.leanclass.modules;

public enum SkillSlot {
    PASSIVE1(1, "North"),
    PASSIVE2(2, "East"),
    PASSIVE3(3, "South"),
    PASSIVE4(4, "West"),
    ACTIVE1(5, "North"),
    ACTIVE2(6, "East"),
    ACTIVE3(7, "South"),
    ACTIVE4(8, "West");

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