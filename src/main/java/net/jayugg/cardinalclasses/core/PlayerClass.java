package net.jayugg.cardinalclasses.core;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import net.jayugg.cardinalclasses.util.Utils;
import net.minecraft.item.ItemConvertible;

import java.util.Collections;
import java.util.Map;

import static net.jayugg.cardinalclasses.CardinalClasses.MOD_ID;

public abstract class PlayerClass extends PlayerAddon {
    private final BiMap<SkillSlot, PassiveSkill> passiveSkills;
    private final BiMap<SkillSlot, ActiveSkill> activeSkills;
    private final BiMap<PerkSlot, PlayerPerk> perks;
    private final int color;

    public PlayerClass(String id, Map<SkillSlot, PassiveSkill> passiveSkills, Map<SkillSlot, ActiveSkill> activeSkills, Map<PerkSlot, PlayerPerk> perks, ItemConvertible icon, int color) {
        super(id, icon);
        this.color = color;

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

        this.passiveSkills = Utils.mapToBiMap(passiveSkills);
        this.activeSkills = Utils.mapToBiMap(activeSkills);
        this.perks = Utils.mapToBiMap(perks);
    }

    public BiMap<SkillSlot, ActiveSkill> getActiveSkills() {
        return ImmutableBiMap.copyOf(activeSkills);
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

    public SkillSlot getSkillSlot(PlayerSkill skill) {
        if (skill instanceof PassiveSkill) {
            if (passiveSkills.inverse().get(skill) == null) {
                throw new IllegalArgumentException("Skill not found in class: " + skill);
            }
            return passiveSkills.inverse().get(skill);
        } else if (skill instanceof ActiveSkill) {
            if (activeSkills.inverse().get(skill) == null) {
                throw new IllegalArgumentException("Skill not found in class: " + skill);
            }
            return activeSkills.inverse().get(skill);
        }
        throw new IllegalArgumentException("Invalid skill type: " + skill);
    }

    @Override
    public String getTranslationKey() {
        return String.format("class.%s.%s", MOD_ID, id);
    }

    public int getColor() {
        return color;
    }
}



