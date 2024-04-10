package net.jayugg.leanclass.implement;

import net.jayugg.leanclass.modules.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.world.ServerWorld;

import java.util.Map;

public class BaseClass extends PlayerClass {
    private static Map<SkillSlot, PlayerSkill> createSkills() {
        return Map.of(
                SkillSlot.PASSIVE1, ModAbilities.BASE_PASSIVE_RED,
                SkillSlot.PASSIVE2, ModAbilities.BASE_PASSIVE_BLUE,
                SkillSlot.PASSIVE3, ModAbilities.BASE_PASSIVE_GREEN,
                SkillSlot.PASSIVE4, ModAbilities.BASE_PASSIVE_YELLOW,
                SkillSlot.ACTIVE1, ModAbilities.BASE_ACTIVE_SKILL,
                SkillSlot.ACTIVE2, ModAbilities.BASE_ACTIVE_SKILL,
                SkillSlot.ACTIVE3, ModAbilities.BASE_ACTIVE_SKILL,
                SkillSlot.ACTIVE4, ModAbilities.BASE_ACTIVE_SKILL
        );
    }

    private static Map<PerkSlot, PlayerPerk> createPerks() {
        return Map.of(
                PerkSlot.ALPHA, ModAbilities.BASE_EFFECT_PERK,
                PerkSlot.OMEGA, ModAbilities.BASE_PERK
        );
    }

    public BaseClass() {
        super("base", createSkills(), createPerks());
    }
}
