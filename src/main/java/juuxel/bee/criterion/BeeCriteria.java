package juuxel.bee.criterion;

import juuxel.bee.mixin.CriterionsAccessor;

public final class BeeCriteria {
    public static final BeeScoopedCriterion BEE_SCOOPED = CriterionsAccessor.callRegister(new BeeScoopedCriterion());

    public static void init() {

    }
}
