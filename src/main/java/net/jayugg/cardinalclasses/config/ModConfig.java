package net.jayugg.cardinalclasses.config;

import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;
import net.jayugg.cardinalclasses.CardinalClasses;

@Modmenu(modId = CardinalClasses.MOD_ID)
@Config(name = "cardinalclasses", wrapperName = "ModConfigWrapper")
@SuppressWarnings("unused")
public class ModConfig {
    public boolean hideSkillBarsWhenNotInUse = false;
}

