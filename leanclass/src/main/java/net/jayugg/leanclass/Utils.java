package net.jayugg.leanclass;

public class Utils {
    public enum PerkSlot {
        NONE(0),
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

}
