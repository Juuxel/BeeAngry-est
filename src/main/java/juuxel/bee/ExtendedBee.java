package juuxel.bee;

import net.minecraft.entity.passive.BeeEntity;

public interface ExtendedBee {
    boolean beeAngryest_isNocturnal();

    boolean beeAngryest_isShadowDisabled();
    void beeAngryest_disableShadows();

    static ExtendedBee of(BeeEntity bee) {
        return (ExtendedBee) bee;
    }
}
