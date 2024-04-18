package net.jayugg.cardinalclasses.advancement;

import com.google.gson.JsonObject;
import net.jayugg.cardinalclasses.core.PlayerClass;
import net.jayugg.cardinalclasses.registry.PlayerClassRegistry;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import static net.jayugg.cardinalclasses.CardinalClasses.MOD_ID;

public class ObtainClassCriterion extends AbstractCriterion<ObtainClassCriterion.Conditions> {
    static final Identifier ID = new Identifier(MOD_ID, "obtain_class");

    @Override
    protected Conditions conditionsFromJson(JsonObject json,
                                            EntityPredicate.Extended playerPredicate,
                                            AdvancementEntityPredicateDeserializer predicateDeserializer) {
        if (!json.has("player_class")) return new Conditions(playerPredicate, null);
        String playerClassId = json.get("player_class").getAsString();
        return new Conditions(playerPredicate, PlayerClassRegistry.getPlayerClass(playerClassId));
    }

    @Override
    public Identifier getId() {
        return ID;
    }

    public void trigger(ServerPlayerEntity player, PlayerClass playerClass) {
        trigger(player, conditions -> conditions.requirementsMet(playerClass.getId()));
    }

    public static class Conditions extends AbstractCriterionConditions {
        final PlayerClass playerClass;
        public Conditions(EntityPredicate.Extended extended, @Nullable PlayerClass playerClass) {
            super(ID, extended);
            this.playerClass = playerClass;
        }

        boolean requirementsMet(String playerClassId) {
            if (playerClass == null) return true;
            return playerClass.getId().equals(playerClassId);
        }

        @Override
        public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
            JsonObject json = super.toJson(predicateSerializer);
            if (playerClass == null) return json;
            json.addProperty("player_class", playerClass.getId());
            return json;
        }
    }
}
