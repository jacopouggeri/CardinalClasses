package net.jayugg.leanclass.component;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.jayugg.leanclass.base.SkillSlot;
import net.minecraft.nbt.NbtCompound;

import java.util.EnumMap;

public class ActiveSkillComponent implements ComponentV3, AutoSyncedComponent {
    private EnumMap<SkillSlot, Long> lastUsed = null;

    public void setLastUsed(SkillSlot slot, Long time) {
        if (lastUsed == null) {
            lastUsed = new EnumMap<>(SkillSlot.class);
        }
        lastUsed.put(slot, time);
    }

    public long getLastUsed(SkillSlot slot) {
        if (lastUsed == null) {
            lastUsed = new EnumMap<>(SkillSlot.class);
        }
        return lastUsed.getOrDefault(slot, (long) 0);
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        if (tag.contains("lastUsed")) {
            lastUsed = new EnumMap<>(SkillSlot.class);
            NbtCompound lastUsedTag = tag.getCompound("lastUsed");
            for (SkillSlot slot : SkillSlot.values()) {
                if (lastUsedTag.contains(slot.toString())) {
                    lastUsed.put(slot, lastUsedTag.getLong(slot.toString()));
                }
            }
        }
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        if (lastUsed != null) {
            NbtCompound lastUsedTag = new NbtCompound();
            lastUsed.forEach((slot, time) -> lastUsedTag.putLong(slot.toString(), time));
            tag.put("lastUsed", lastUsedTag);
        }
    }
}
