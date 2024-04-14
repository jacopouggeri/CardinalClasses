package net.jayugg.leanclass.advancement;

import com.google.gson.JsonObject;
import net.jayugg.leanclass.base.PlayerClass;
import net.jayugg.leanclass.base.PlayerSkill;
import net.jayugg.leanclass.registry.AbilityRegistry;
import net.jayugg.leanclass.registry.PlayerClassRegistry;
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
        String playerClassId = json.get("player_class").getAsString();
        String playerSkillId = json.get("player_skill").getAsString();
        int level = json.get("level").getAsInt();
        return new Conditions(playerPredicate, PlayerClassRegistry.getPlayerClass(playerClassId), AbilityRegistry.getSkill(playerSkillId), level);
    }

    @Override
    public Identifier getId() {
        return ID;
    }

    public void trigger(ServerPlayerEntity player, PlayerClass playerClass, PlayerSkill playerSkill, int skillLevel) {
        trigger(player, conditions -> conditions.requirementsMet(playerClass.getId(), playerSkill.getId(), skillLevel));
    }

    public static class Conditions extends AbstractCriterionConditions {
        PlayerSkill playerSkill;
        PlayerClass playerClass;
        int level;
        public Conditions(EntityPredicate.Extended extended, PlayerClass playerClass, PlayerSkill playerSkill, int level) {
            super(ID, extended);
            this.playerClass = playerClass;
            this.playerSkill = playerSkill;
            this.level = level;
        }

        boolean requirementsMet(String playerClassId, String playerSkillId, int skillLevel) {
            return playerClass.getId().equals(playerClassId) &&
                    playerSkill.getId().equals(playerSkillId) &&
                    skillLevel == level;
        }

        @Override
        public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
            JsonObject json = super.toJson(predicateSerializer);
            json.addProperty("player_class", playerClass.getId());
            json.addProperty("player_skill", playerSkill.getId());
            json.addProperty("level", level);
            return json;
        }
    }
}
