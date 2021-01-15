package juuxel.bee.item;

import juuxel.bee.BeeAngryest;
import juuxel.bee.client.BeeItemRenderer;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.List;

public final class BeeItems {
    public static final RegistryObject<Item> BEE = regObj("bee");
    public static final RegistryObject<Item> SCOOP = regObj("scoop");

    private static RegistryObject<Item> regObj(String id) {
        return RegistryObject.of(BeeAngryest.id(id), ForgeRegistries.ITEMS);
    }

    public static void register(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();

        Item bee = new BeeItem(new Item.Settings().group(ItemGroup.MISC).setISTER(() -> BeeItemRenderer::new)).setRegistryName("bee");
        Item scoop = new ScoopItem(new Item.Settings().group(ItemGroup.TOOLS).maxDamage(100)).setRegistryName("scoop");
        registry.registerAll(bee, scoop);

        DispenserBlock.registerBehavior(bee, (pointer, stack) -> {
            BlockPos pos = pointer.getBlockPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
            BeeItem.place(pointer.getWorld(), pos, null, stack);
            return stack;
        });

        DispenserBlock.registerBehavior(scoop, new FallibleItemDispenserBehavior() {
            @Override
            protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
                BlockPos pos = pointer.getBlockPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
                List<BeeEntity> bees = pointer.getWorld().getNonSpectatingEntities(BeeEntity.class, new Box(pos));
                if (!bees.isEmpty()) {
                    ScoopItem.scoopBee(pointer.getWorld(), null, null, bees.get(0), stack);
                } else {
                    setSuccess(false);
                }
                return stack;
            }
        });
    }
}
