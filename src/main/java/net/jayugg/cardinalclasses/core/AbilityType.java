package net.jayugg.cardinalclasses.core;

public enum AbilityType {
     ACTIVE(1), PASSIVE(2), PERK(0);
    @Override
    public String toString() {
        return name().toLowerCase();
    }
    private final int value;
    AbilityType(int value) {
        this.value = value;
    }
    public int getValue() {
        return this.value;
    }
    public static AbilityType fromInt(int value) {
        for (AbilityType type : AbilityType.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unexpected value: " + value);
    }
}