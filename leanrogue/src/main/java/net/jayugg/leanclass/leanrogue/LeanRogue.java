package net.jayugg.leanclass.leanrogue;

import net.fabricmc.api.ModInitializer;

import net.jayugg.leanclass.leanrogue.addons.CustomAbilities;
import net.jayugg.leanclass.leanrogue.addons.ModClasses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LeanRogue implements ModInitializer {
	public static final String MOD_ID = "leanrogue";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		CustomAbilities.registerAbilities();
		ModClasses.registerClasses();
		LOGGER.info("Lean Rogue Started!");
	}
}