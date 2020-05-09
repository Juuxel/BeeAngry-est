package juuxel.bee.criterion;

import com.google.gson.JsonObject;
import juuxel.bee.BeeAngryest;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

public class BeeScoopedCriterion extends AbstractCriterion<BeeScoopedCriterion.Conditions> {
    private static final Identifier ID = BeeAngryest.id("bee_scooped");

    @Override
    public Identifier getId() {
        return ID;
    }

    @Override
    protected Conditions conditionsFromJson(JsonObject obj, EntityPredicate.Extended playerPredicate, AdvancementEntityPredicateDeserializer predicateDeserializer) {
        return new Conditions(ItemPredicate.fromJson(obj.get("item")), playerPredicate, EntityPredicate.fromJson(obj.get("bee")));
    }

    public void trigger(ServerPlayerEntity player, ItemStack stack, Entity entity) {
        test(player, conditions -> conditions.matches(player, stack, entity));
    }

    public static class Conditions extends AbstractCriterionConditions {
        private final ItemPredicate item;
        private final EntityPredicate bee;

        public Conditions(ItemPredicate item, EntityPredicate.Extended playerPredicate, EntityPredicate bee) {
            super(BeeScoopedCriterion.ID, playerPredicate);
            this.item = item;
            this.bee = bee;
        }

        public boolean matches(ServerPlayerEntity player, ItemStack stack, Entity entity) {
            return item.test(stack) && bee.test(player, entity);
        }

        @Override
        public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
            return Util.make(super.toJson(predicateSerializer), json -> {
                json.add("item", item.toJson());
                json.add("bee", bee.toJson());
            });
        }
    }
}
