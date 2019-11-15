package juuxel.bee.mixin;

import net.minecraft.advancement.criterion.Criterion;
import net.minecraft.advancement.criterion.Criterions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Criterions.class)
public interface CriterionsAccessor {
    @SuppressWarnings("PublicStaticMixinMember")
    @Invoker
    static <T extends Criterion<?>> T callRegister(T object) {
        throw new AssertionError("@Invoker dummy body called");
    }
}
