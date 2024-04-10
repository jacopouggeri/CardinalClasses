package net.jayugg.leanclass;

public class MixinManager {
    static boolean classLoaded = false;
    public static boolean canLoadMixins() {
        return classLoaded;
    }

    public static void setClassLoaded() {
        classLoaded = true;
    }
}
