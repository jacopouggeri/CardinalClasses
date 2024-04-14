package net.jayugg.leanclass;

import net.fabricmc.api.ClientModInitializer;
import net.jayugg.leanclass.event.KeyInputHandler;
import net.jayugg.leanclass.networking.ModMessages;

public class LeanClassClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		KeyInputHandler.register();
		ModMessages.registerC2SPackets();

		//HudRenderCallback.EVENT.register(new ChargeHudOverlay());
	}
}