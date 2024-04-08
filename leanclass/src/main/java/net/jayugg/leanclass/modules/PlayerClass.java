package net.jayugg.leanclass.modules;

import net.jayugg.leanclass.Utils.PerkSlot;
import net.jayugg.leanclass.component.ModComponents;
import net.jayugg.leanclass.component.PlayerPerkComponent;
import net.minecraft.entity.player.PlayerEntity;

public interface PlayerClass {
    String getName();
    default PerkSlot getAscendedSlotFrom(PlayerEntity player) {
        PlayerPerkComponent perkComponent = ModComponents.PERK_COMPONENT.get(player);
        return perkComponent.getAscendedSlot();
    }
}



