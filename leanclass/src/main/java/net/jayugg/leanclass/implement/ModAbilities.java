package net.jayugg.leanclass.implement;

import net.jayugg.leanclass.base.*;
import net.jayugg.leanclass.registry.AbilityRegistry;
import net.minecraft.item.Items;

import java.awt.*;

public class ModAbilities {
    public static final PlayerSkill BASE_PASSIVE_1 = AbilityRegistry.registerSkill(
            new PassiveSkill("base_passive_1", Items.CHAINMAIL_HELMET));
    public static final PlayerSkill BASE_PASSIVE_2 = AbilityRegistry.registerSkill(
            new PassiveSkill("base_passive_2", Items.CHAINMAIL_CHESTPLATE));
    public static final PlayerSkill BASE_PASSIVE_3 = AbilityRegistry.registerSkill(
            new PassiveSkill("base_passive_3", Items.CHAINMAIL_LEGGINGS));
    public static final PlayerSkill BASE_PASSIVE_4 = AbilityRegistry.registerSkill(
            new PassiveSkill("base_passive_4", Items.CHAINMAIL_BOOTS));
    public static final PlayerSkill BASE_ACTIVE_1 = AbilityRegistry.registerSkill(
            new ActiveSkill("base_active_1", Items.WOODEN_SWORD,
                    new SkillCooldownHelper(50, 1, 1, 1)));
    public static final PlayerSkill BASE_ACTIVE_2 = AbilityRegistry.registerSkill(
            new ActiveSkill("base_active_2", Items.WOODEN_AXE,
                    new SkillCooldownHelper(50, 1, 1, 1)));
    public static final PlayerSkill BASE_ACTIVE_3 = AbilityRegistry.registerSkill(
            new ActiveSkill("base_active_3", Items.WOODEN_PICKAXE,
                    new SkillCooldownHelper(50, 1, 1, 1)));
    public static final PlayerSkill BASE_ACTIVE_4 = AbilityRegistry.registerSkill(
            new ThrowEggSkill("base_active_4", Items.WOODEN_SHOVEL,
                    new SkillCooldownHelper(50, 1, 1, 0.9f)));
    public static final PlayerSkill TEST_PASSIVE_RED = AbilityRegistry.registerSkill(
            new ExampleAttackSkill("test_passive_red", 0xFF0000, Items.RED_DYE));
    public static final PlayerSkill TEST_PASSIVE_BLUE = AbilityRegistry.registerSkill(
            new ExampleAttackSkill("test_passive_blue", 0x0000FF, Items.BLUE_DYE));
    public static final PlayerSkill TEST_PASSIVE_GREEN = AbilityRegistry.registerSkill(
            new ExampleAttackSkill("test_passive_green", 0x00FF00, Items.GREEN_DYE));
    public static final PlayerSkill TEST_PASSIVE_YELLOW = AbilityRegistry.registerSkill(
            new ExampleAttackSkill("test_passive_yellow", 0xFFFF00, Items.YELLOW_DYE));
    public static final PlayerSkill TEST_ACTIVE_1 = AbilityRegistry.registerSkill(
            new ThrowEggSkill("test_active_egg", Items.EGG,
                    new SkillCooldownHelper(200, 10, 2, 1), Color.YELLOW.getRGB()));
    public static final PlayerSkill TEST_ACTIVE_2 = AbilityRegistry.registerSkill(
            new ThrowFireballSkill("test_active_fire", Items.FIRE_CHARGE,
                    new SkillCooldownHelper(600, 3, 1, 0.9f), Color.RED.getRGB()));
    public static final PlayerSkill TEST_ACTIVE_3 = AbilityRegistry.registerSkill(
            new ThrowSnowballSkill("test_active_snow", Items.SNOWBALL,
                    new SkillCooldownHelper(200, 10, 2, 1), Color.WHITE.getRGB()));
    public static final PlayerSkill TEST_ACTIVE_4 = AbilityRegistry.registerSkill(
            new ThrowArrowSkill("test_active_arrow", Items.ARROW,
                    new SkillCooldownHelper(400, 5, 3, 0.8f), Color.GREEN.getRGB()));
    public static final PlayerPerk BASE_EFFECT_PERK = AbilityRegistry.registerPerk(
            new PlayerPerk("test", Items.POTION));
    public static final PlayerPerk BASE_PERK = AbilityRegistry.registerPerk(
            new PlayerPerk("base", Items.BEACON));

    public static void registerAbilities() {
        // This method does nothing but ensures that the static initializer block is executed
    }
}