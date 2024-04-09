package net.jayugg.leanclass.implement;

import net.jayugg.leanclass.modules.PlayerClassRegistry;

public class ModClasses {
    public static final BaseClass BASE_CLASS = new BaseClass();
    public static void registerClasses() {
        PlayerClassRegistry.registerClass(BASE_CLASS);
    }
}
