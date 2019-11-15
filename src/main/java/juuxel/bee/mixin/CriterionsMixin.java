package juuxel.bee.mixin;

import juuxel.bee.criterion.BeeCriteria;
import net.minecraft.advancement.criterion.Criterions;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Criterions.class)
public class CriterionsMixin {
    static {
        BeeCriteria.init();
    }
}
