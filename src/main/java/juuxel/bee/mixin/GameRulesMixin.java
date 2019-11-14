package juuxel.bee.mixin;

import juuxel.bee.BeeGameRules;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(GameRules.class)
public class GameRulesMixin {
    static {
        BeeGameRules.init();
    }
}
