package net.jayugg.cardinalclasses;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.jayugg.cardinalclasses.advancement.ModCriteria;
import net.jayugg.cardinalclasses.block.ModBlockEntities;
import net.jayugg.cardinalclasses.block.ModBlocks;
import net.jayugg.cardinalclasses.command.ModCommands;
import net.jayugg.cardinalclasses.effect.ModBrewing;
import net.jayugg.cardinalclasses.event.PlayerLoginHandler;
import net.jayugg.cardinalclasses.base.ModAbilities;
import net.jayugg.cardinalclasses.base.ModClasses;
import net.jayugg.cardinalclasses.item.ModItemGroup;
import net.jayugg.cardinalclasses.item.ModItems;
import net.fabricmc.api.ModInitializer;

import net.jayugg.cardinalclasses.util.ModLootTableModifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class CardinalClasses implements ModInitializer {
	public static final String MOD_ID = "cardinalclasses";
    public static final Logger LOGGER;

	static {
		Optional<ModContainer> modContainer = FabricLoader.getInstance().getModContainer(MOD_ID);
		String modName = modContainer.map(container -> container.getMetadata().getName()).orElse(MOD_ID);
		LOGGER = LoggerFactory.getLogger(modName);
	}

	@Override
	public void onInitialize() {
		LOGGER.info("Starting CardinalClasses");
		ModItemGroup.registerItemGroup();
		ModBlocks.registerModBlocks();
		ModBlockEntities.registerBlockEntities();
		ModItems.registerModItems();
		ModAbilities.registerAbilities();
		ModClasses.registerClasses();
		ModBrewing.registerBrewingRecipes();
		ModCriteria.registerCriteria();
		ModCommands.register();
		PlayerLoginHandler.register();
		ModLootTableModifier.modifyLootTables();
	}
}