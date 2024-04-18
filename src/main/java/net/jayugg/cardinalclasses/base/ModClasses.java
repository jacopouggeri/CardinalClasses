package net.jayugg.cardinalclasses.base;
import net.jayugg.cardinalclasses.test.TestClass;
import net.jayugg.cardinalclasses.util.MixinManager;
import net.jayugg.cardinalclasses.core.PlayerClass;
import net.jayugg.cardinalclasses.registry.PlayerClassRegistry;

public class ModClasses {
    public static final PlayerClass TEST_CLASS = PlayerClassRegistry.registerClass(new TestClass());
    public static void registerClasses() {
        MixinManager.setClassLoaded();
        // This method does nothing but ensures that the static initializer block is executed
    }
}
