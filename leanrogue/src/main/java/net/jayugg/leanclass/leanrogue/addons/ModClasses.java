package net.jayugg.leanclass.leanrogue.addons;

import net.jayugg.leanclass.leanrogue.RogueClass;
import net.jayugg.leanclass.modules.PlayerClassRegistry;

public class ModClasses {
    public static final RogueClass ROGUE_CLASS = new RogueClass();
    public static void registerClasses() {
        PlayerClassRegistry.registerClass(ROGUE_CLASS);
    }
}
