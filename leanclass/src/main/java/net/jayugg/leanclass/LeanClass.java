package net.jayugg.leanclass;

import net.jayugg.leanclass.implement.ModAbilities;
import net.jayugg.leanclass.implement.ModClasses;
import net.jayugg.leanclass.item.ModItemGroup;
import net.jayugg.leanclass.item.ModItems;
import net.fabricmc.api.ModInitializer;

import net.jayugg.leanclass.implement.BaseClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LeanClass implements ModInitializer {
	public static final String MOD_ID = "leanclass";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Starting LeanClasses");
		ModItemGroup.registerItemGroup();
		ModItems.registerModItems();
		ModAbilities.registerAbilities();
		ModClasses.registerClasses();
	}
}