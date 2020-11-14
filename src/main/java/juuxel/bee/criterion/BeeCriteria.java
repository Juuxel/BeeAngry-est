package juuxel.bee.criterion;

import net.fabricmc.fabric.api.object.builder.v1.advancement.CriterionRegistry;

public final class BeeCriteria {
    public static final BeeScoopedCriterion BEE_SCOOPED = CriterionRegistry.register(new BeeScoopedCriterion());
    public static final BeeExplodedCriterion BEE_EXPLODED = CriterionRegistry.register(new BeeExplodedCriterion());

    public static void init() {

    }
}
