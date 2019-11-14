package juuxel.bee;

import juuxel.bee.item.BeeItem;
import juuxel.bee.item.ScoopItem;
import juuxel.bee.mixin.BooleanRuleAccessor;
import juuxel.bee.mixin.GameRulesAccessor;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.GameRules;

public final class BeeAngryest implements ModInitializer {
    public static final Item BEE = new BeeItem(new Item.Settings().group(ItemGroup.MISC)/*.maxCount(1)*/);
    public static final Item SCOOP = new ScoopItem(new Item.Settings().group(ItemGroup.TOOLS).maxDamage(100));
    public static final GameRules.RuleKey<GameRules.BooleanRule> SAVE_SCOOPED_BEE_NBT = GameRulesAccessor.register("saveScoopedBeeNbt", BooleanRuleAccessor.of(true));
    public static final GameRules.RuleKey<GameRules.BooleanRule> ALWAYS_DROP_SCOOPED_BEES = GameRulesAccessor.register("alwaysDropScoopedBees", BooleanRuleAccessor.of(true));

    public static Identifier id(String path) {
        return new Identifier("beeangry-est", path);
    }

    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, id("bee"), BEE);
        Registry.register(Registry.ITEM, id("scoop"), SCOOP);
    }
}
