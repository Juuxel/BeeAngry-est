package juuxel.bee;

import juuxel.bee.item.BeeItem;
import juuxel.bee.item.ScoopItem;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public final class BeeAngryest implements ModInitializer {
    public static final Item BEE = new BeeItem(new Item.Settings().group(ItemGroup.MISC));
    public static final Item SCOOP = new ScoopItem(new Item.Settings().group(ItemGroup.TOOLS));

    public static Identifier id(String path) {
        return new Identifier("beeangry-est", path);
    }

    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, id("bee"), BEE);
        Registry.register(Registry.ITEM, id("scoop"), SCOOP);
    }
}
