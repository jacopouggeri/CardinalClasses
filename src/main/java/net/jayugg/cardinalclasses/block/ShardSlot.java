package net.jayugg.cardinalclasses.block;

import net.minecraft.util.StringIdentifiable;

public enum ShardSlot implements StringIdentifiable {
    EMPTY(0, "empty"),
    SOUTH(1, "south"),
    WEST(2, "west"),
    NORTH(3, "north"),
    EAST(4, "east");
    private final int id;
    private final String name;

    ShardSlot(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }

    public int asInt() {
        return this.id;
    }

    public static ShardSlot fromInt(int id) {
        for (ShardSlot slot : values()) {
            if (slot.id == id) {
                return slot;
            }
        }
        return EMPTY;
    }
}
