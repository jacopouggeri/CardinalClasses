package net.jayugg.cardinalclasses.component;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.jayugg.cardinalclasses.core.AbilityType;
import net.jayugg.cardinalclasses.core.PerkSlot;
import net.jayugg.cardinalclasses.core.SkillSlot;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static net.jayugg.cardinalclasses.core.PlayerSkill.SKILL_MAX_LEVEL;

public class PlayerClassComponent implements ComponentV3, AutoSyncedComponent {
    private String classId;
    private final Map<SkillSlot, Integer> passiveSkillLevels = new HashMap<>();
    private final Map<SkillSlot, Integer> activeSkillLevels = new HashMap<>();
    private PerkSlot ascendedPerkSlot;

    public void setClass(String newId) {
        this.classId = newId;
    }

    public boolean setAscendedPerk(PerkSlot slot) {
        if (ascendedPerkSlot == null) {
            ascendedPerkSlot = slot;
            return true;
        }
        return false;
    }

    public void setAscendedPerkCreative(PerkSlot slot) {
        ascendedPerkSlot = slot;
    }

    public Optional<PerkSlot> getAscendedPerk() {
        return Optional.ofNullable(ascendedPerkSlot);
    }

    public String getId() {
        return classId;
    }

    public boolean setSkillLevel(AbilityType type, SkillSlot skillSlot, int level) {
        if (0 <= level && level <= SKILL_MAX_LEVEL) {
            if (type == AbilityType.PASSIVE) {
                passiveSkillLevels.put(skillSlot, level);
            } else {
                activeSkillLevels.put(skillSlot, level);
            }
            return true;
        }
        return false;
    }

    public int getSkillLevel(AbilityType type, SkillSlot skillSlot) {
        Integer level = type == AbilityType.PASSIVE ? passiveSkillLevels.get(skillSlot) : activeSkillLevels.get(skillSlot);
        return level != null ? level : 0;
    }

    public boolean skillUp(AbilityType type, SkillSlot skillSlot) {
        int level = getSkillLevel(type, skillSlot);
        return setSkillLevel(type, skillSlot, level + 1);
    }

    public void resetSkills() {
        // Set all skill levels to 0
        for (SkillSlot slot : SkillSlot.values()) {
            passiveSkillLevels.put(slot, 0);
            activeSkillLevels.put(slot, 0);
        }
    }

    public void resetAscendedPerk() {
        ascendedPerkSlot = null;
    }

    public Map<SkillSlot, Integer> getSkillLevels(AbilityType type) {
        return type == AbilityType.PASSIVE ? passiveSkillLevels : activeSkillLevels;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        classId = tag.getString("ClassId");
        String ascendedPerkSlotName = tag.getString("AscendedPerkSlot");
        if (!ascendedPerkSlotName.isEmpty()) {
            ascendedPerkSlot = PerkSlot.valueOf(ascendedPerkSlotName);
        }
        NbtCompound passiveSkillLevelsTag = tag.getCompound("PassiveSkillLevels");
        NbtCompound activeSkillLevelsTag = tag.getCompound("ActiveSkillLevels");
        for (String key : passiveSkillLevelsTag.getKeys()) {
            int level = passiveSkillLevelsTag.getInt(key);
            passiveSkillLevels.put(SkillSlot.valueOf(key), level);
        }
        for (String key : activeSkillLevelsTag.getKeys()) {
            int level = activeSkillLevelsTag.getInt(key);
            activeSkillLevels.put(SkillSlot.valueOf(key), level);
        }
    }

    @Override
    public void writeToNbt(@NotNull NbtCompound tag) {
        if (classId != null) {
            tag.putString("ClassId", classId);
        }
        if (ascendedPerkSlot != null) {
            tag.putString("AscendedPerkSlot", ascendedPerkSlot.name());
        }
        NbtCompound passiveSkillLevelsTag = new NbtCompound();
        NbtCompound activeSkillLevelsTag = new NbtCompound();
        for (Map.Entry<SkillSlot, Integer> entry : passiveSkillLevels.entrySet()) {
            passiveSkillLevelsTag.putInt(entry.getKey().name(), entry.getValue());
        }
        for (Map.Entry<SkillSlot, Integer> entry : activeSkillLevels.entrySet()) {
            activeSkillLevelsTag.putInt(entry.getKey().name(), entry.getValue());
        }
        tag.put("PassiveSkillLevels", passiveSkillLevelsTag);
        tag.put("ActiveSkillLevels", activeSkillLevelsTag);
    }
}