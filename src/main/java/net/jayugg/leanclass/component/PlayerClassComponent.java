package net.jayugg.leanclass.component;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.jayugg.leanclass.base.AbilityType;
import net.jayugg.leanclass.base.PerkSlot;
import net.jayugg.leanclass.base.SkillSlot;
import net.minecraft.nbt.NbtCompound;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static net.jayugg.leanclass.base.PlayerSkill.SKILL_MAX_LEVEL;

public class PlayerClassComponent implements ComponentV3, AutoSyncedComponent {
    private String classId = "base";
    private final Map<SkillSlot, Integer> passiveSkillLevels = new HashMap<>();
    private final Map<SkillSlot, Integer> activeSkillLevels = new HashMap<>();
    private Optional<PerkSlot> ascendedPerkSlot;

    public void setClass(String newId) {
        this.classId = newId;
    }

    public boolean setAscendedPerk(PerkSlot slot) {
        if (ascendedPerkSlot.isEmpty()) {
            ascendedPerkSlot = Optional.of(slot);
            return true;
        }
        return false;
    }

    public void setAscendedPerkCreative(PerkSlot slot) {
        ascendedPerkSlot = Optional.of(slot);
    }

    public Optional<PerkSlot> getAscendedPerk() {
        return ascendedPerkSlot;
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

    public boolean skillDown(AbilityType type, SkillSlot skillSlot) {
        int level = getSkillLevel(type, skillSlot);
        return setSkillLevel(type, skillSlot, level - 1);
    }

    public void resetSkills() {
        // Set all skill levels to 0
        for (SkillSlot slot : SkillSlot.values()) {
            passiveSkillLevels.put(slot, 0);
            activeSkillLevels.put(slot, 0);
        }
    }

    public void resetAscendedPerk() {
        ascendedPerkSlot = Optional.empty();
    }

    public Map<SkillSlot, Integer> getSkillLevels(AbilityType type) {
        return type == AbilityType.PASSIVE ? passiveSkillLevels : activeSkillLevels;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.classId = tag.getString("ClassId");
        String ascendedPerkSlotName = tag.getString("AscendedPerkSlot");
        if (!ascendedPerkSlotName.isEmpty()) {
            this.ascendedPerkSlot = Optional.of(PerkSlot.valueOf(ascendedPerkSlotName));
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
    public void writeToNbt(NbtCompound tag) {
        tag.putString("ClassId", classId);
        ascendedPerkSlot.ifPresent(perkSlot -> tag.putString("AscendedPerkSlot", perkSlot.name()));
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