package net.jayugg.leanclass.modules;

public enum SkillSlot {
    PASSIVE1(1),
    PASSIVE2(2),
    PASSIVE3(3),
    PASSIVE4(4),
    ACTIVE1(5),
    ACTIVE2(6),
    ACTIVE3(7),
    ACTIVE4(8);

    private final int value;

    SkillSlot(int value) {
        this.value = value;
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
}