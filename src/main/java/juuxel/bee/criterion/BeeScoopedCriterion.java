package juuxel.bee.criterion;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import juuxel.bee.BeeAngryest;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
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
    public Conditions conditionsFromJson(JsonObject obj, JsonDeserializationContext context) {
        return new Conditions(ItemPredicate.fromJson(obj.get("item")), EntityPredicate.fromJson(obj.get("bee")));
    }

    public void trigger(ServerPlayerEntity player, ItemStack stack, Entity entity) {
        test(player.getAdvancementManager(), conditions -> conditions.matches(player, stack, entity));
    }

    public static class Conditions extends AbstractCriterionConditions {
        private final ItemPredicate item;
        private final EntityPredicate bee;

        public Conditions(ItemPredicate item, EntityPredicate bee) {
            super(BeeScoopedCriterion.ID);
            this.item = item;
            this.bee = bee;
        }

        public boolean matches(ServerPlayerEntity player, ItemStack stack, Entity entity) {
            return item.test(stack) && bee.test(player, entity);
        }

        // I think this is used for data generation? So technically useless
        @Override
        public JsonElement toJson() {
            return Util.create(new JsonObject(), json -> {
                json.add("item", item.toJson());
                json.add("bee", bee.serialize());
            });
        }
    }
}
