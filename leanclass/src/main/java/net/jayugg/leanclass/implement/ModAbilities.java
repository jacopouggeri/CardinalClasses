package net.jayugg.leanclass.implement;

import net.jayugg.leanclass.base.*;
import net.jayugg.leanclass.registry.AbilityRegistry;
import net.minecraft.item.Items;

public class ModAbilities {
    public static final PlayerSkill TEST_PASSIVE_RED = AbilityRegistry.registerSkill(
            new ExampleAttackSkill("test_passive_red", 0xFF0000, Items.RED_DYE));
    public static final PlayerSkill TEST_PASSIVE_BLUE = AbilityRegistry.registerSkill(
            new ExampleAttackSkill("test_passive_blue", 0x0000FF, Items.BLUE_DYE));
    public static final PlayerSkill TEST_PASSIVE_GREEN = AbilityRegistry.registerSkill(
            new ExampleAttackSkill("test_passive_green", 0x00FF00, Items.GREEN_DYE));
    public static final PlayerSkill TEST_PASSIVE_YELLOW = AbilityRegistry.registerSkill(
            new ExampleAttackSkill("test_passive_yellow", 0xFFFF00, Items.YELLOW_DYE));
    public static final PlayerSkill BASE_PASSIVE = AbilityRegistry.registerSkill(
            new PassiveSkill("base_passive", Items.CHAINMAIL_CHESTPLATE));
    public static final PlayerSkill BASE_ACTIVE = AbilityRegistry.registerSkill(
            new ThrowEggSkill("base_active", Items.WOODEN_SWORD,
                    new SkillCooldownHelper(30, 10)));
    public static final PlayerSkill TEST_ACTIVE = AbilityRegistry.registerSkill(
            new ActiveSkill("test_active", Items.EGG,
                    new SkillCooldownHelper(30, 10)));
    public static final PlayerPerk BASE_EFFECT_PERK = AbilityRegistry.registerPerk(
            new PlayerPerk("test", Items.POTION));
    public static final PlayerPerk BASE_PERK = AbilityRegistry.registerPerk(
            new PlayerPerk("base", Items.BEACON));

    public static void registerAbilities() {
        // This method does nothing but ensures that the static initializer block is executed
    }
}