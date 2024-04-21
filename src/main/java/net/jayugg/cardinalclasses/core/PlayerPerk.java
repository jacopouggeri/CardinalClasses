package net.jayugg.cardinalclasses.core;

import net.jayugg.cardinalclasses.util.PlayerClassManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;

import static net.jayugg.cardinalclasses.CardinalClasses.MOD_ID;

public class PlayerPerk extends PlayerAbility {
    public final PerkSlot perkSlot;
    public PlayerPerk(String id, ItemConvertible icon, PerkSlot perkSlot) {
        super(id, 1, 2, AbilityType.PERK, icon);
        this.perkSlot = perkSlot;
    }
    @Override
    public String getTranslationKey() {
        return String.format("perk.%s.%s", MOD_ID, id);
    }

    public PerkSlot getPerkSlot() {
        return perkSlot;
    }

    public boolean isAscended(PlayerEntity player) {
        return PlayerClassManager.hasAscendedPerk(player, perkSlot);
    }
}
