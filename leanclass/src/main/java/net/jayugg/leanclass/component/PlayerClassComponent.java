package net.jayugg.leanclass.component;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.jayugg.leanclass.base.PerkSlot;
import net.jayugg.leanclass.base.SkillSlot;
import net.minecraft.nbt.NbtCompound;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static net.jayugg.leanclass.LeanClass.LOGGER;
import static net.jayugg.leanclass.base.PlayerSkill.SKILL_MAX_LEVEL;

public class PlayerClassComponent implements ComponentV3, AutoSyncedComponent {
    private String classId = "base";
    private final Map<SkillSlot, Integer> skillLevels = new HashMap<>();
    private PerkSlot ascendedPerkSlot = null;

    public boolean setClass(String newId) {
        if (this.classId.equals("base")) {
            this.classId = newId;
            return true;
        }
        return false;
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

    public boolean setSkillLevel(SkillSlot skillSlot, int level) {
        if (0 <= level && level <= SKILL_MAX_LEVEL) {
            skillLevels.put(skillSlot, level);
            return true;
        }
        return false;
    }

    public int getSkillLevel(SkillSlot skillSlot) {
        return skillLevels.getOrDefault(skillSlot, 0);
    }

    public boolean skillUp(SkillSlot skillSlot) {
        int level = getSkillLevel(skillSlot);
        LOGGER.warn("Skill level: " + level);
        return setSkillLevel(skillSlot, level + 1);
    }

    public boolean skillDown(SkillSlot skillSlot) {
        int level = getSkillLevel(skillSlot);
        return setSkillLevel(skillSlot, level - 1);
    }

    public void resetSkills() {
        skillLevels.clear();
    }

    public Map<SkillSlot, Integer> getSkillLevels() { return skillLevels; }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.classId = tag.getString("ClassId");
        NbtCompound skillLevelsTag = tag.getCompound("SkillLevels");
        for (String key : skillLevelsTag.getKeys()) {
            int level = skillLevelsTag.getInt(key);
            skillLevels.put(SkillSlot.valueOf(key), level);
        }
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putString("ClassId", classId);
        NbtCompound skillLevelsTag = new NbtCompound();
        for (Map.Entry<SkillSlot, Integer> entry : skillLevels.entrySet()) {
            skillLevelsTag.putInt(entry.getKey().toString(), entry.getValue());
        }
        tag.put("SkillLevels", skillLevelsTag);
    }
}