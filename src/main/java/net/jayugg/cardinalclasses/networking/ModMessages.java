package net.jayugg.cardinalclasses.networking;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class ModMessages {
    public static final Identifier ACTIVE_SKILL= new Identifier("cardinalclasses", "active_skill");

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(ACTIVE_SKILL, ActiveSkillC2SPacket::receive);
    }

    public static void registerS2CPackets() {
        // Register the packet for sending class data to the client
    }
}
