package net.jayugg.leanclass.leanrogue.addons;

import net.jayugg.leanclass.leanrogue.RogueClass;
import net.jayugg.leanclass.modules.PlayerClass;
import net.jayugg.leanclass.registry.PlayerClassRegistry;

public class ModClasses {
    public static final PlayerClass ROGUE_CLASS = PlayerClassRegistry.registerClass(new RogueClass());
    public static void registerClasses() {
        // This method does nothing but ensures that the static initializer block is executed
    }
}
