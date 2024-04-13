package net.jayugg.leanclass.leanrogue.addons;

import net.jayugg.leanclass.MixinManager;
import net.jayugg.leanclass.leanrogue.RogueClass;
import net.jayugg.leanclass.util.PlayerClass;
import net.jayugg.leanclass.registry.PlayerClassRegistry;

public class ModClasses {
    public static final PlayerClass ROGUE_CLASS = PlayerClassRegistry.registerClass(new RogueClass());
    public static void registerClasses() {
        MixinManager.setClassLoaded();
        // This method does nothing but ensures that the static initializer block is executed
    }
}
