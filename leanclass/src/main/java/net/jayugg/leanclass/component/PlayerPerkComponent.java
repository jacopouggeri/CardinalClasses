package net.jayugg.leanclass.component;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import net.jayugg.leanclass.Utils.PerkSlot;
import net.minecraft.nbt.NbtCompound;
public class PlayerPerkComponent implements ComponentV3 {
    private PerkSlot ascendedSlot = PerkSlot.NONE; // Directly use the enum type

    public boolean ascend(int newSlotID) {
        // Check if currently none and newSlot is either ALPHA or OMEGA
        if (this.ascendedSlot == PerkSlot.NONE && (newSlotID > 0)) {
            this.ascendedSlot = PerkSlot.fromValue(newSlotID);
            return true;
        }
        return false;
    }

    public boolean isSlotAscended(PerkSlot slot) {
        return this.ascendedSlot == slot;
    }

    public PerkSlot getAscendedSlot() {
        return ascendedSlot;
    }

    public int getAscendedSlotID() {
        return ascendedSlot.getValue();
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.ascendedSlot = PerkSlot.fromValue(tag.getInt("AscendedSlot"));
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putInt("AscendedSlot", ascendedSlot.ordinal());
    }

    public String getCurrentSlotName() {
        return ascendedSlot.name();
    }
}