package net.jayugg.leanclass.modules;

public enum PerkSlot {
    ALPHA(1),
    OMEGA(2);

    private final int value;

    PerkSlot(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static PerkSlot fromValue(int value) {
        for (PerkSlot slot : PerkSlot.values()) {
            if (slot.getValue() == value) {
                return slot;
            }
        }
        throw new IllegalArgumentException("Unexpected value: " + value);
    }
}