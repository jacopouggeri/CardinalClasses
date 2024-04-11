package net.jayugg.leanclass;

import net.fabricmc.api.ClientModInitializer;
import net.jayugg.leanclass.event.KeyInputHandler;

public class LeanClassClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		KeyInputHandler.register();
	}
}