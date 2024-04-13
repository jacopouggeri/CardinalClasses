package net.jayugg.leanclass.base;

import net.minecraft.item.Item;

import java.util.Collections;
import java.util.Map;

import static net.jayugg.leanclass.LeanClass.MOD_ID;

public abstract class PlayerClass extends PlayerAddon {
    private final Map<SkillSlot, PassiveSkill> passiveSkills;
    private final Map<SkillSlot, ActiveSkill> activeSkills;
    private final Map<PerkSlot, PlayerPerk> perks;

    public PlayerClass(String id, Map<SkillSlot, PassiveSkill> passiveSkills, Map<SkillSlot, ActiveSkill> activeSkills, Map<PerkSlot, PlayerPerk> perks, Item icon) {
        super(id, icon);

        // Verify the correct number of skills and perks are provided
        if (passiveSkills.size() != SkillSlot.values().length ||
                activeSkills.size() != SkillSlot.values().length ||
                perks.size() != PerkSlot.values().length) {
            throw new IllegalArgumentException("Incorrect number of skills or perks provided.");
        }

        // Verify active skills are provided
        if (activeSkills.size() != 4) {
            throw new IllegalArgumentException("Incorrect number of active skills provided.");
        }

        this.passiveSkills = passiveSkills;
        this.activeSkills = activeSkills;
        this.perks = perks;
    }

    public Map<SkillSlot, PlayerSkill> getPassiveSkills() {
        return Collections.unmodifiableMap(passiveSkills);
    }
    public Map<SkillSlot, PlayerSkill> getActiveSkills() {
        return Collections.unmodifiableMap(activeSkills);
    }

    public Map<SkillSlot, PlayerSkill> getSkills(AbilityType type) {
        if (type == AbilityType.PASSIVE) {
            return Collections.unmodifiableMap(passiveSkills);
        } else if (type == AbilityType.ACTIVE) {
            return Collections.unmodifiableMap(activeSkills);
        }
        throw new IllegalArgumentException("Invalid ability type: " + type);
    }

    public Map<PerkSlot, PlayerPerk> getPerks() {
        return Collections.unmodifiableMap(perks);
    }
    public PerkSlot getPerkSlot(PlayerPerk perk) {
        for (Map.Entry<PerkSlot, PlayerPerk> entry : perks.entrySet()) {
            if (entry.getValue().equals(perk)) {
                return entry.getKey();
            }
        }
        throw new IllegalArgumentException("Perk not found in class: " + perk);
    }

    public SkillSlot getSkillSlot(PlayerSkill skill) {
        if (skill instanceof PassiveSkill) {
            for (Map.Entry<SkillSlot, PassiveSkill> entry : passiveSkills.entrySet()) {
                if (entry.getValue().equals(skill)) {
                    return entry.getKey();
                }
            }
        } else if (skill instanceof ActiveSkill) {
            for (Map.Entry<SkillSlot, ActiveSkill> entry : activeSkills.entrySet()) {
                if (entry.getValue().equals(skill)) {
                    return entry.getKey();
                }
            }
        }
        throw new IllegalArgumentException("Skill not found in class: " + skill);
    }

    @Override
    public String getTranslationKey() {
        return String.format("class.%s.%s", MOD_ID, id);
    }

}



