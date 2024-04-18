package net.jayugg.cardinalclasses;

import net.fabricmc.api.ClientModInitializer;
import net.jayugg.cardinalclasses.render.ShardHolderBlockEntityRenderer;
import net.jayugg.cardinalclasses.render.SkillAltarBlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.jayugg.cardinalclasses.block.ModBlockEntities;
import net.jayugg.cardinalclasses.event.KeyInputHandler;
import net.jayugg.cardinalclasses.networking.ModMessages;

public class CardinalClassesClient implements ClientModInitializer {
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