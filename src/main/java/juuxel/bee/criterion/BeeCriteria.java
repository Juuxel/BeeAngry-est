package juuxel.bee.criterion;

import net.minecraft.advancement.criterion.Criteria;

public final class BeeCriteria {
    public static final BeeScoopedCriterion BEE_SCOOPED = Criteria.register(new BeeScoopedCriterion());
    public static final BeeExplodedCriterion BEE_EXPLODED = Criteria.register(new BeeExplodedCriterion());

    public static void init() {

    }
}
