package juuxel.bee.mixin;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.advancement.criterion.Criterion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Criteria.class)
public interface CriteriaAccessor {
    @SuppressWarnings("PublicStaticMixinMember")
    @Invoker
    static <T extends Criterion<?>> T callRegister(T object) {
        throw new AssertionError("@Invoker dummy body called");
    }
}
