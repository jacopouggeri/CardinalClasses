package net.jayugg.leanclass;

import net.fabricmc.api.ClientModInitializer;
import net.jayugg.leanclass.render.ShardHolderBlockEntityRenderer;
import net.jayugg.leanclass.render.SkillAltarBlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.jayugg.leanclass.block.ModBlockEntities;
import net.jayugg.leanclass.event.KeyInputHandler;
import net.jayugg.leanclass.networking.ModMessages;

public class LeanClassClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		KeyInputHandler.register();
		ModMessages.registerC2SPackets();
		BlockEntityRendererFactories.register(ModBlockEntities.SHARD_HOLDER, ShardHolderBlockEntityRenderer::new);
		BlockEntityRendererFactories.register(ModBlockEntities.SKILL_ALTAR, SkillAltarBlockEntityRenderer::new);
		//HudRenderCallback.EVENT.register(new ChargeHudOverlay());
	}
}