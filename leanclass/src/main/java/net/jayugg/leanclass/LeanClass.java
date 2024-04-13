package net.jayugg.leanclass;

import net.jayugg.leanclass.advancement.ModCriteria;
import net.jayugg.leanclass.block.ModBlocks;
import net.jayugg.leanclass.command.ModCommands;
import net.jayugg.leanclass.event.PlayerLoginHandler;
import net.jayugg.leanclass.implement.ModAbilities;
import net.jayugg.leanclass.implement.ModClasses;
import net.jayugg.leanclass.item.ModItemGroup;
import net.jayugg.leanclass.item.ModItems;
import net.fabricmc.api.ModInitializer;

import net.jayugg.leanclass.networking.ModMessages;
import net.jayugg.leanclass.potion.ModPotions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LeanClass implements ModInitializer {
	public static final String MOD_ID = "leanclass";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Starting LeanClasses");
		ModItemGroup.registerItemGroup();
		ModBlocks.registerModBlocks();
		ModItems.registerModItems();
		ModAbilities.registerAbilities();
		ModClasses.registerClasses();
		ModPotions.registerPotionsRecipes();
		ModCriteria.registerCriteria();
		ModCommands.register();
		PlayerLoginHandler.register();
	}
}