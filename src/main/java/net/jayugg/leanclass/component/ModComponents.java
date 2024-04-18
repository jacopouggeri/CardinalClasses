package net.jayugg.leanclass.component;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.minecraft.util.Identifier;

import static net.jayugg.leanclass.LeanClass.MOD_ID;

public class ModComponents implements EntityComponentInitializer {
    public static final ComponentKey<PlayerClassComponent> CLASS_COMPONENT = ComponentRegistry.getOrCreate(new Identifier(MOD_ID, "player_class"), PlayerClassComponent.class);
    public static final ComponentKey<ActiveSkillComponent> ACTIVE_SKILLS_COMPONENT = ComponentRegistry.getOrCreate(new Identifier(MOD_ID, "active_skills"), ActiveSkillComponent.class);
    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(CLASS_COMPONENT, player -> new PlayerClassComponent(), RespawnCopyStrategy.ALWAYS_COPY);
        registry.registerForPlayers(ACTIVE_SKILLS_COMPONENT, player -> new ActiveSkillComponent(), RespawnCopyStrategy.ALWAYS_COPY);
    }
}
