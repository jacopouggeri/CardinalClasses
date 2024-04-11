package net.jayugg.leanclass.modules;

public enum AbilityType {
    PASSIVE, ACTIVE, PERK;
    @Override
    public String toString() {
        return name().toLowerCase();
    }
}