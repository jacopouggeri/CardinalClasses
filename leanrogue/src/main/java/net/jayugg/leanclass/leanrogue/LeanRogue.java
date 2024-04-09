package net.jayugg.leanclass.leanrogue;

import net.fabricmc.api.ModInitializer;

import net.jayugg.leanclass.modules.PlayerClassRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LeanRogue implements ModInitializer {
	public static final String MOD_ID = "leanrogue";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final RogueClass ROGUE_CLASS = new RogueClass();

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		PlayerClassRegistry.registerClass(ROGUE_CLASS);
		LOGGER.info("Lean Rogue Started!");
	}
}