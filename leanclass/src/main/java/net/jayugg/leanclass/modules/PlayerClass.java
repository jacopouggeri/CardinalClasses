package net.jayugg.leanclass.modules;

import java.util.Collections;
import java.util.Map;

public abstract class PlayerClass {
    protected final String id;
    private final Map<SkillSlot, PlayerSkill> skills;
    private final Map<PerkSlot, PlayerPerk> perks;

    public PlayerClass(String id, Map<SkillSlot, PlayerSkill> skills, Map<PerkSlot, PlayerPerk> perks) {
        this.id = id;

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
        System.err.println("Perk not found in class: " + perk);
        System.err.println("Returning ALPHA slot as default. This usually happens when the game is loading and the perk is not yet registered.");
        return PerkSlot.ALPHA;
    }

    public String getId() {
        return id;
    }
}



