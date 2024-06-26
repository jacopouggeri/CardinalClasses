package net.jayugg.cardinalclasses.networking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.jayugg.cardinalclasses.core.SkillSlot;
import net.jayugg.cardinalclasses.util.PlayerClassManager;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class ActiveSkillC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        // Read the skill index from the packet
        int skillIndex = buf.readInt();
        // Map the skill index to the corresponding skill slot
        SkillSlot skillSlot = SkillSlot.fromValue(skillIndex + 1);
        PlayerClassManager.useActiveSkill(player, skillSlot);
    }
}