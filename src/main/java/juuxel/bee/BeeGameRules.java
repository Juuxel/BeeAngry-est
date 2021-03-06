package juuxel.bee;

import juuxel.bee.config.Config;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

public final class BeeGameRules {
    public static GameRules.Key<GameRules.BooleanRule> SAVE_SCOOPED_BEE_NBT = register("saveScoopedBeeNbt", GameRules.Category.MISC, true);
    public static GameRules.Key<GameRules.BooleanRule> ALWAYS_DROP_SCOOPED_BEES = register("alwaysDropScoopedBees", GameRules.Category.DROPS, true);
    public static GameRules.Key<GameRules.BooleanRule> BEES_SEEK_RAIN_SHELTER = register("beesSeekRainShelter", true);
    public static GameRules.Key<GameRules.BooleanRule> BEES_EXPLODE = register("beesExplode", true);

    private BeeGameRules() {}

    public static void init() {
    }

    private static GameRules.Key<GameRules.BooleanRule> register(String name, boolean defaultValue) {
        return register(name, GameRules.Category.MOBS, defaultValue);
    }

    private static GameRules.Key<GameRules.BooleanRule> register(String name, GameRules.Category category, boolean defaultValue) {
        return GameRuleRegistry.register(BeeAngryest.ID + ":" + name, category, GameRuleFactory.createBooleanRule(Config.get().getDefault(name, defaultValue)));
    }
}
