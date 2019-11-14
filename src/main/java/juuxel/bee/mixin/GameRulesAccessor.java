package juuxel.bee.mixin;

import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(GameRules.class)
public interface GameRulesAccessor {
    @SuppressWarnings("PublicStaticMixinMember")
    @Invoker
    static <T extends GameRules.Rule<T>> GameRules.RuleKey<T> register(String name, GameRules.RuleType<T> type) {
        throw new AssertionError("@Invoker dummy body called");
    }
}
