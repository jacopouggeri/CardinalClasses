package net.jayugg.leanclass.component;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import net.minecraft.nbt.NbtCompound;

public class PlayerSkillComponent implements ComponentV3 {
    private int skillPoints = 0;
    private static final int MAX_LEVEL= 3;

    // Standard getters and setters
    public int getSkillPoints() { return skillPoints; }
    public boolean addSkillPoint() {
        if (skillPoints + 1 <= MAX_LEVEL) {
            this.skillPoints ++;
            return true;
        }
        return false;
    }

    public boolean removeSkillPoint() {
        if (skillPoints - 1 >= 0) {
            this.skillPoints --;
            return true;
        }
        return false;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        skillPoints = tag.getInt("SkillPoints");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putInt("SkillPoints", skillPoints);
    }
}
