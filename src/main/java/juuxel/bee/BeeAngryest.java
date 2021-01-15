package juuxel.bee;

import juuxel.bee.client.ScoopHudRenderer;
import juuxel.bee.criterion.BeeCriteria;
import juuxel.bee.item.BeeItems;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(BeeAngryest.ID)
public final class BeeAngryest {
    public static final String ID = "beeangry-est";

    public static Identifier id(String path) {
        return new Identifier(ID, path);
    }

    public BeeAngryest() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Item.class, this::registerItems);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(FMLCommonSetupEvent event) {
        BeeGameRules.init();
        BeeCriteria.init();
    }

    private void registerItems(RegistryEvent.Register<Item> event) {
        BeeItems.register(event);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void renderHud(RenderGameOverlayEvent.Post event) {
        ScoopHudRenderer.render(event);
    }

        // TODO:
        /*BeeWeatherCheckCallback.EVENT.register((world, bee) -> {
            boolean rainShelter = world.getGameRules().getBoolean(BeeGameRules.BEES_SEEK_RAIN_SHELTER);
            return TriState.of(!rainShelter || !world.isRaining());
        });

        BeeTimeCheckCallback.EVENT.register((world, bee) -> {
            boolean nocturnal = ExtendedBee.of(bee).beeAngryest_isNocturnal();
            return nocturnal ? TriState.of(world.isNight()) : TriState.DEFAULT;
        });*/
}
