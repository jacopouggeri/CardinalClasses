package net.jayugg.cardinalclasses.base;

import net.jayugg.cardinalclasses.core.*;
import net.jayugg.cardinalclasses.test.*;
import net.jayugg.cardinalclasses.registry.AbilityRegistry;
import net.minecraft.item.Items;

import java.awt.*;

public class ModAbilities {
    public static final PlayerSkill TEST_PASSIVE_RED = AbilityRegistry.registerSkill(
            new FireworkAttackSkill("test_passive_red", 0xFF0000, Items.RED_DYE));
    public static final PlayerSkill TEST_PASSIVE_BLUE = AbilityRegistry.registerSkill(
            new FireworkAttackSkill("test_passive_blue", 0x0000FF, Items.BLUE_DYE));
    public static final PlayerSkill TEST_PASSIVE_GREEN = AbilityRegistry.registerSkill(
            new FireworkAttackSkill("test_passive_green", 0x00FF00, Items.GREEN_DYE));
    public static final PlayerSkill TEST_PASSIVE_YELLOW = AbilityRegistry.registerSkill(
            new FireworkAttackSkill("test_passive_yellow", 0xFFFF00, Items.YELLOW_DYE));
    public static final PlayerSkill TEST_ACTIVE_1 = AbilityRegistry.registerSkill(
            new ThrowEggSkill("test_active_egg", Items.EGG,
                    new SkillCooldownHelper(20, 60, 10, 1), Color.YELLOW.getRGB()));
    public static final PlayerSkill TEST_ACTIVE_2 = AbilityRegistry.registerSkill(
            new ThrowFireballSkill("test_active_fire", Items.FIRE_CHARGE,
                    new SkillCooldownHelper(100, 3, 1, 0.9f), Color.RED.getRGB()));
    public static final PlayerSkill TEST_ACTIVE_3 = AbilityRegistry.registerSkill(
            new ThrowSnowballSkill("test_active_snow", Items.SNOWBALL,
                    new SkillCooldownHelper(20, 60, 10, 1), Color.WHITE.getRGB()));
    public static final PlayerSkill TEST_ACTIVE_4 = AbilityRegistry.registerSkill(
            new ThrowArrowSkill("test_active_arrow", Items.ARROW,
                    new SkillCooldownHelper(50, 9, 3, 0.8f), Color.GREEN.getRGB()));
    public static final PlayerPerk TEST_EFFECT_PERK = AbilityRegistry.registerPerk(
            new PlayerPerk("test_effect", Items.POTION, PerkSlot.ALPHA));
    public static final PlayerPerk TEST_ATTACK_PERK = AbilityRegistry.registerPerk(
            new AttackLevitationPerk("test_attack", Items.SPIDER_EYE, PerkSlot.OMEGA));

    public static void registerAbilities() {
        // This method does nothing but ensures that the static initializer block is executed
    }
}