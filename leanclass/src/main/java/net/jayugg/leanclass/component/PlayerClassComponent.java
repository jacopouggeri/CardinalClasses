package net.jayugg.leanclass.component;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import net.jayugg.leanclass.modules.PerkSlot;
import net.jayugg.leanclass.modules.SkillSlot;
import net.minecraft.nbt.NbtCompound;

import java.util.HashMap;
import java.util.Map;

import static net.jayugg.leanclass.modules.PlayerSkill.SKILL_MAX_LEVEL;

public class PlayerClassComponent implements ComponentV3 {
    private String classId = "base";
    private Map<String, Integer> skillLevels = new HashMap<>();
    private Map<SkillSlot, String> skillSlots = new HashMap<>();
    private int ascendedPerkId = 0;

    public boolean setClass(String newId) {
        if (this.classId.equals("Base")) {
            this.classId = newId;
            return true;
        }
        return false;
    }

    public boolean setAscendedPerk(PerkSlot slot) {
        if (ascendedPerkId == 0) {
            ascendedPerkId = slot.getValue();
            return true;
        }
        return false;
    }

    public int getAscendedPerk() {
        return ascendedPerkId;
    }

    public String getId() {
        return classId;
    }

    public boolean setSkillLevel(String skillId, int level) {
        if (0 <= level && level <= SKILL_MAX_LEVEL) {
            skillLevels.put(skillId, level);
            return true;
        }
        return false;
    }

    public int getSkillLevel(String skillId) {
        return skillLevels.getOrDefault(skillId, 0);
    }

    public boolean skillUp(String skillId) {
        int level = getSkillLevel(skillId);
        return setSkillLevel(skillId, level + 1);
    }

    public boolean skillDown(String skillId) {
        int level = getSkillLevel(skillId);
        return setSkillLevel(skillId, level - 1);
    }

    public Map<String, Integer> getSkillLevels() { return skillLevels; }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.classId = tag.getString("ClassId");
        NbtCompound skillLevelsTag = tag.getCompound("SkillLevels");
        for (String key : skillLevelsTag.getKeys()) {
            int level = skillLevelsTag.getInt(key);
            skillLevels.put(key, level);
        }
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putString("ClassId", classId);
        NbtCompound skillLevelsTag = new NbtCompound();
        for (Map.Entry<String, Integer> entry : skillLevels.entrySet()) {
            skillLevelsTag.putInt(entry.getKey(), entry.getValue());
        }
        tag.put("SkillLevels", skillLevelsTag);
    }
}