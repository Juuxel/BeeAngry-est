package juuxel.bee.mixin;

import juuxel.bee.criterion.BeeCriteria;
import net.minecraft.advancement.criterion.Criteria;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Criteria.class)
public class CriteriaMixin {
    static {
        BeeCriteria.init();
    }
}