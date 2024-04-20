package net.jayugg.cardinalclasses.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.jayugg.cardinalclasses.effect.ClassGrantEffect;
import net.jayugg.cardinalclasses.registry.PlayerClassRegistry;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static net.jayugg.cardinalclasses.CardinalClasses.MOD_ID;

public class LanguageProvider extends FabricLanguageProvider {

    public LanguageProvider(FabricDataOutput dataOutput) {
        super(dataOutput, "en_us");
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        for (ClassGrantEffect classGrantEffect : PlayerClassRegistry.CLASS_EFFECTS.getRegistry().values()) {
            String classId = classGrantEffect.getPlayerClassId();
            String formattedClassId = classId.substring(0, 1).toUpperCase() + classId.substring(1).toLowerCase();
            String effectTranslationKey = String.format("effect.%s.grant_%s", MOD_ID, classId);
            translationBuilder.add(effectTranslationKey, String.format("Grant %s Class", formattedClassId));

            String potionTranslationKey = String.format("item.minecraft.potion.effect.grant_%s", classId);
            String splashPotionTranslationKey = String.format("item.minecraft.splash_potion.effect.grant_%s", classId);
            String lingeringPotionTranslationKey = String.format("item.minecraft.lingering_potion.effect.grant_%s", classId);
            translationBuilder.add(potionTranslationKey, "Class Potion");
            translationBuilder.add(splashPotionTranslationKey, "Class Splash Potion");
            translationBuilder.add(lingeringPotionTranslationKey, "Class Lingering Potion");

            // Load an existing language file.
            try {
                Path existingFilePath = Paths.get("../../src/main/resources/assets/cardinalclasses/lang/en_us.json");
                if (Files.exists(existingFilePath)) {
                    translationBuilder.add(existingFilePath);
                } else {
                    LOGGER.warn("Warning: Language file does not exist: {}", existingFilePath);
                }
            } catch (IOException e) {
                LOGGER.error("Error: Failed to add existing language file!", e);
            }
        }
    }
}
