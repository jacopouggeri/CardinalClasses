package net.jayugg.leanclass.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import static net.jayugg.leanclass.LeanClass.MOD_ID;

public class KeyInputHandler {
    public static final String KEY_CATEGORY_MOD = "key.category." + MOD_ID;
    public static final String KEY_OPEN_CLASS_MENU = "key." + MOD_ID + ".class_menu";
    public static KeyBinding openClassMenu;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (openClassMenu.wasPressed()) {
                // Open the class menu
                client.player.sendMessage(Text.literal("Opening class menu"), true);
            }
        });
    }

    public static void register() {
        openClassMenu = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_OPEN_CLASS_MENU,
                GLFW.GLFW_KEY_G,
                KEY_CATEGORY_MOD));
        registerKeyInputs();
    }
}
