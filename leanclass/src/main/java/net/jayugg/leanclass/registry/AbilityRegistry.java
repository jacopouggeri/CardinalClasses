package net.jayugg.leanclass.registry;

import net.jayugg.leanclass.modules.PlayerPerk;
import net.jayugg.leanclass.modules.PlayerSkill;

public class AbilityRegistry {
    public static final Registry<PlayerSkill> SKILLS = new Registry<>();
    public static final Registry<PlayerPerk> PERKS = new Registry<>();
    public static PlayerSkill registerSkill(PlayerSkill playerSkill) {
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
