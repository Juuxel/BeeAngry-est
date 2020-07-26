package juuxel.bee.mixin.plugins;

import juuxel.bee.config.Config;

public class LambdaControlsMixinPlugin extends BeeAngryestMixinPlugin {
    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return Config.get().getCompatDefault("lambdaControlsCompatEnabled", true);
    }
}
