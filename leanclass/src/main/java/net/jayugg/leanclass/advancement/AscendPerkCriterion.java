package net.jayugg.leanclass.advancement;

import com.google.gson.JsonObject;
import net.jayugg.leanclass.base.PlayerPerk;
import net.jayugg.leanclass.registry.AbilityRegistry;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import static net.jayugg.leanclass.LeanClass.MOD_ID;

public class AscendPerkCriterion extends AbstractCriterion<AscendPerkCriterion.Conditions> {
    static final Identifier ID = new Identifier(MOD_ID, "ascend_perk");

    @Override
    protected Conditions conditionsFromJson(JsonObject json,
                                            EntityPredicate.Extended playerPredicate,
                                            AdvancementEntityPredicateDeserializer predicateDeserializer) {
        String playerPerkId = json.get("player_perk").getAsString();
        return new Conditions(playerPredicate, AbilityRegistry.getPerk(playerPerkId));
    }

    @Override
    public Identifier getId() {
        return ID;
    }

    public void trigger(ServerPlayerEntity player, PlayerPerk playerPerk, boolean ascended) {
        trigger(player, conditions -> conditions.requirementsMet(playerPerk.getId(), ascended));
    }

    public static class Conditions extends AbstractCriterionConditions {
        PlayerPerk playerPerk;
        public Conditions(EntityPredicate.Extended extended, PlayerPerk playerPerk) {
            super(ID, extended);
            this.playerPerk = playerPerk;
        }

        boolean requirementsMet(String playerClassId, boolean ascended) {
            return playerPerk.getId().equals(playerClassId) && ascended;
        }

        @Override
        public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
            JsonObject json = super.toJson(predicateSerializer);
            json.addProperty("player_perk", playerPerk.getId());
            return json;
        }
    }
}