package juuxel.bee.criterion;

import net.fabricmc.fabric.api.object.builder.v1.advancement.CriterionRegistry;

public final class BeeCriteria {
    public static final BeeScoopedCriterion BEE_SCOOPED = CriterionRegistry.register(new BeeScoopedCriterion());

    public static void init() {

    }
}
