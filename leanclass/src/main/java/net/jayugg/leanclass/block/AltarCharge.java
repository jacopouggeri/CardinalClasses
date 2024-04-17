package net.jayugg.leanclass.block;

import net.minecraft.util.StringIdentifiable;

public enum AltarCharge implements StringIdentifiable {
    INERT("inert"),
    ACTIVE("active"),
    PASSIVE("passive");

    private final String value;

    AltarCharge(String value) {
        this.value = value;
    }

    @Override
    public String asString() {
        return value;
    }
}
