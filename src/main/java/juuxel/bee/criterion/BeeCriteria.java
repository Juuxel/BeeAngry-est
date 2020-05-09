package juuxel.bee.criterion;

import juuxel.bee.mixin.CriteriaAccessor;

public final class BeeCriteria {
    public static final BeeScoopedCriterion BEE_SCOOPED = CriteriaAccessor.callRegister(new BeeScoopedCriterion());

    public static void init() {

    }
}
