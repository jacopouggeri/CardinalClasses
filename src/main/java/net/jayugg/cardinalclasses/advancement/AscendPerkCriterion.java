package net.jayugg.cardinalclasses.advancement;

import com.google.gson.JsonObject;
import net.jayugg.cardinalclasses.core.PlayerClass;
import net.jayugg.cardinalclasses.core.PlayerPerk;
import net.jayugg.cardinalclasses.registry.AbilityRegistry;
import net.jayugg.cardinalclasses.registry.PlayerClassRegistry;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import static net.jayugg.cardinalclasses.CardinalClasses.MOD_ID;

public class AscendPerkCriterion extends AbstractCriterion<AscendPerkCriterion.Conditions> {
    static final Identifier ID = new Identifier(MOD_ID, "ascend_perk");

    @Override
    protected Conditions conditionsFromJson(JsonObject json,
                                            EntityPredicate.Extended playerPredicate,
                                            AdvancementEntityPredicateDeserializer predicateDeserializer) {
        String playerClassId = json.get("player_class").getAsString();
        String playerPerkId = json.get("player_perk").getAsString();
        return new Conditions(playerPredicate, PlayerClassRegistry.getPlayerClass(playerClassId), AbilityRegistry.getPerk(playerPerkId));
    }

    @Override
    public Identifier getId() {
        return ID;
    }

    public void trigger(ServerPlayerEntity player, PlayerClass playerClass, PlayerPerk playerPerk, boolean ascended) {
        trigger(player, conditions -> conditions.requirementsMet(playerClass.getId(), playerPerk.getId(), ascended));
    }

    public static class Conditions extends AbstractCriterionConditions {
        final PlayerClass playerClass;
        final PlayerPerk playerPerk;
        public Conditions(EntityPredicate.Extended extended, PlayerClass playerClass, PlayerPerk playerPerk) {
            super(ID, extended);
            this.playerClass = playerClass;
            this.playerPerk = playerPerk;
        }

        boolean requirementsMet(String playerClassId, String playerPerkId, boolean ascended) {
            return playerClass.getId().equals(playerClassId) &&
                    playerPerk.getId().equals(playerPerkId)
                    && ascended;
        }

        @Override
        public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
            JsonObject json = super.toJson(predicateSerializer);
            json.addProperty("player_class", playerClass.getId());
            json.addProperty("player_perk", playerPerk.getId());
            return json;
        }
    }
}