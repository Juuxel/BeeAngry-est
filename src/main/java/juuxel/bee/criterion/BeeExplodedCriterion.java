package juuxel.bee.criterion;

import com.google.gson.JsonObject;
import juuxel.bee.BeeAngryest;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.entity.Entity;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

public class BeeExplodedCriterion extends AbstractCriterion<BeeExplodedCriterion.Conditions> {
    private static final Identifier ID = BeeAngryest.id("bee_exploded");

    @Override
    public Identifier getId() {
        return ID;
    }

    @Override
    protected Conditions conditionsFromJson(JsonObject obj, EntityPredicate.Extended playerPredicate, AdvancementEntityPredicateDeserializer predicateDeserializer) {
        return new Conditions(playerPredicate, EntityPredicate.fromJson(obj.get("bee")));
    }

    public void trigger(ServerPlayerEntity player, Entity bee) {
        test(player, conditions -> conditions.matches(player, bee));
    }

    public static class Conditions extends AbstractCriterionConditions {
        private final EntityPredicate bee;

        public Conditions(EntityPredicate.Extended playerPredicate, EntityPredicate bee) {
            super(BeeExplodedCriterion.ID, playerPredicate);
            this.bee = bee;
        }

        public boolean matches(ServerPlayerEntity player, Entity entity) {
            return bee.test(player, entity);
        }

        @Override
        public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
            return Util.make(super.toJson(predicateSerializer), json -> {
                json.add("bee", bee.toJson());
            });
        }
    }
}
