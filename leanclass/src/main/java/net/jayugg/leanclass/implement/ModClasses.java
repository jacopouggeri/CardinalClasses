package net.jayugg.leanclass.implement;
import net.jayugg.leanclass.MixinManager;
import net.jayugg.leanclass.modules.PlayerClass;
import net.jayugg.leanclass.registry.PlayerClassRegistry;

public class ModClasses {
    public static final PlayerClass BASE_CLASS = PlayerClassRegistry.registerClass(new BaseClass());
    public static final PlayerClass TEST_CLASS = PlayerClassRegistry.registerClass(new TestClass());
    public static void registerClasses() {
        MixinManager.setClassLoaded();
        // This method does nothing but ensures that the static initializer block is executed
    }
}
