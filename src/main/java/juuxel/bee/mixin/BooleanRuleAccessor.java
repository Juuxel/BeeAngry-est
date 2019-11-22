package juuxel.bee.mixin;

import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(GameRules.BooleanRule.class)
public interface BooleanRuleAccessor {
    @SuppressWarnings("PublicStaticMixinMember")
    @Invoker
    static GameRules.RuleType<GameRules.BooleanRule> callCreate(boolean value) {
        throw new AssertionError("@Invoker dummy body called");
    }
}
