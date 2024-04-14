package net.jayugg.leanclass.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.jayugg.leanclass.base.SkillSlot;
import net.jayugg.leanclass.networking.ModMessages;
import net.jayugg.leanclass.util.PlayerClassManager;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.network.PacketByteBuf;
import org.lwjgl.glfw.GLFW;

import java.util.Arrays;

import static net.jayugg.leanclass.LeanClass.MOD_ID;

public class KeyInputHandler {
    public static boolean[] keyReleased;
    public static final String KEY_CATEGORY_MOD = "key.category." + MOD_ID;
    public static final String[] KEY_ACTIVE_SKILLS = {
            "key." + MOD_ID + ".active_north",
            "key." + MOD_ID + ".active_east",
            "key." + MOD_ID + ".active_south",
            "key." + MOD_ID + ".active_west"
    };
    public static KeyBinding[] useActiveSkills;

    public static void registerKeyInputs() {
        for (int i = 0; i < useActiveSkills.length; i++) {
            int skillIndex = i;
            ClientTickEvents.END_CLIENT_TICK.register(client -> {
                if (client.player != null) {
                    boolean spammable = PlayerClassManager.getClass(client.player).getActiveSkills().get(SkillSlot.fromValue(skillIndex + 1)).isSpammable();
                    if (spammable) {
                        if (useActiveSkills[skillIndex].wasPressed()) {
                            // Create the packet
                            sendBuf(skillIndex);
                        }
                    } else {
                        if (useActiveSkills[skillIndex].isPressed() && keyReleased[skillIndex]) {
                            // Create the packet
                            sendBuf(skillIndex);
                            keyReleased[skillIndex] = false;
                        } else if (!useActiveSkills[skillIndex].isPressed()) {
                            keyReleased[skillIndex] = true;
                        }
                    }
                }
            });
        }
    }

    private static void sendBuf(int skillIndex) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(skillIndex);

        // Send the packet
        ClientPlayNetworking.send(ModMessages.ACTIVE_SKILL, buf);
    }

    public static void register() {
        useActiveSkills = new KeyBinding[KEY_ACTIVE_SKILLS.length];
        for (int i = 0; i < KEY_ACTIVE_SKILLS.length; i++) {
            useActiveSkills[i] = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                    KEY_ACTIVE_SKILLS[i],
                    GLFW.GLFW_KEY_G + i, // Assign different keys for each skill
                    KEY_CATEGORY_MOD));
        }
        registerKeyInputs();
        keyReleased = new boolean[KEY_ACTIVE_SKILLS.length];
        Arrays.fill(keyReleased, true);
    }
}
