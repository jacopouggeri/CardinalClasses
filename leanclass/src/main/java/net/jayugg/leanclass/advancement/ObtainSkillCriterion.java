package net.jayugg.leanclass.advancement;

import com.google.gson.JsonObject;
import net.jayugg.leanclass.base.PlayerSkill;
import net.jayugg.leanclass.registry.AbilityRegistry;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import static net.jayugg.leanclass.LeanClass.MOD_ID;

public class ObtainSkillCriterion extends AbstractCriterion<ObtainSkillCriterion.Conditions> {
    static final Identifier ID = new Identifier(MOD_ID, "obtain_skill");

    @Override
    protected Conditions conditionsFromJson(JsonObject json,
                                            EntityPredicate.Extended playerPredicate,
                                            AdvancementEntityPredicateDeserializer predicateDeserializer) {
        String playerSkillId = json.get("player_skill").getAsString();
        int level = json.get("level").getAsInt();
        return new Conditions(playerPredicate, AbilityRegistry.getSkill(playerSkillId), level);
    }

    @Override
    public Identifier getId() {
        return ID;
    }

    public void trigger(ServerPlayerEntity player, PlayerSkill playerSkill, int skillLevel) {
        trigger(player, conditions -> conditions.requirementsMet(playerSkill.getId(), skillLevel));
    }

    public static class Conditions extends AbstractCriterionConditions {
        PlayerSkill playerSkill;
        int level;
        public Conditions(EntityPredicate.Extended extended, PlayerSkill playerSkill, int level) {
            super(ID, extended);
            this.playerSkill = playerSkill;
            this.level = level;
        }

        boolean requirementsMet(String playerClassId, int skillLevel) {
            return playerSkill.getId().equals(playerClassId) && skillLevel == level;
        }

        @Override
        public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
            JsonObject json = super.toJson(predicateSerializer);
            json.addProperty("player_skill", playerSkill.getId());
            json.addProperty("level", level);
            return json;
        }
    }
}