package juuxel.bee;

import net.minecraft.entity.passive.BeeEntity;

public interface ExtendedBee {
    boolean beeAngryest_isNocturnal();

    static ExtendedBee of(BeeEntity bee) {
        return (ExtendedBee) bee;
    }
}
