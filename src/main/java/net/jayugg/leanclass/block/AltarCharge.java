package net.jayugg.leanclass.block;

import net.minecraft.util.StringIdentifiable;

public enum AltarCharge implements StringIdentifiable {
    INERT(3, "inert"),
    ACTIVE(1, "active"),
    PASSIVE(2, "passive"),
    ASCEND(0, "ascend");

    private final String id;
    private final int value;

    AltarCharge(int value, String id) {
        this.value = value;
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String asString() {
        return id;
    }
}
