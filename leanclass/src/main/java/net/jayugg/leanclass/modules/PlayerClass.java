package net.jayugg.leanclass.modules;

import net.minecraft.item.Item;

import java.util.Collections;
import java.util.Map;

import static net.jayugg.leanclass.LeanClass.MOD_ID;

public abstract class PlayerClass extends PlayerAddon {
    private final Map<SkillSlot, PlayerSkill> skills;
    private final Map<PerkSlot, PlayerPerk> perks;

    public PlayerClass(String id, Map<SkillSlot, PlayerSkill> skills, Map<PerkSlot, PlayerPerk> perks, Item icon) {
        super(id, icon);

        // Verify the correct number of skills and perks are provided
        if (skills.size() != SkillSlot.values().length || perks.size() != PerkSlot.values().length) {
            throw new IllegalArgumentException("Incorrect number of skills or perks provided.");
        }

        this.skills = skills;
        this.perks = perks;
    }

    public Map<SkillSlot, PlayerSkill> getSkills() {
        return Collections.unmodifiableMap(skills);
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

    @Override
    public String getTranslationKey() {
        return String.format("class.%s.%s", MOD_ID, id);
    }

}



