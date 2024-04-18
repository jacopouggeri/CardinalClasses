package net.jayugg.cardinalclasses.registry;

import net.jayugg.cardinalclasses.core.PlayerPerk;
import net.jayugg.cardinalclasses.core.PlayerSkill;

public class AbilityRegistry {
    public static final ModRegistry<PlayerSkill> SKILLS = new ModRegistry<>();
    public static final ModRegistry<PlayerPerk> PERKS = new ModRegistry<>();
    public static PlayerSkill registerSkill(PlayerSkill playerSkill) {
        if (SKILLS.get(playerSkill.getId()) != null) {
            throw new IllegalArgumentException("Skill already registered: " + playerSkill.getId());
        }
        SKILLS.register(playerSkill.getId(), playerSkill);
        return playerSkill;
    }
    public static PlayerPerk registerPerk(PlayerPerk playerPerk) {
        PERKS.register(playerPerk.getId(), playerPerk);
        return playerPerk;
    }
    public static PlayerSkill getSkill(String id) {
        return SKILLS.get(id);
    }

    public static PlayerPerk getPerk(String id) {
        return PERKS.get(id);
    }

}
