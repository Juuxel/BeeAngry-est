package juuxel.bee;

import juuxel.bee.item.BeeItem;
import juuxel.bee.item.ScoopItem;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.registry.Registry;

import java.util.List;

public final class BeeAngryest implements ModInitializer {
    public static final String ID = "beeangry-est";

    public static final Item BEE = new BeeItem(new Item.Settings().group(ItemGroup.MISC)/*.maxCount(1)*/);
    public static final Item SCOOP = new ScoopItem(new Item.Settings().group(ItemGroup.TOOLS).maxDamage(100));

    public static Identifier id(String path) {
        return new Identifier(ID, path);
    }

    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, id("bee"), BEE);
        Registry.register(Registry.ITEM, id("scoop"), SCOOP);

        DispenserBlock.registerBehavior(BEE, (pointer, stack) -> {
            BlockPos pos = pointer.getBlockPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
            BeeItem.place(pointer.getWorld(), pos, null, stack);
            return stack;
        });

        DispenserBlock.registerBehavior(SCOOP, new FallibleItemDispenserBehavior() {
            @Override
            protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
                BlockPos pos = pointer.getBlockPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
                List<BeeEntity> bees = pointer.getWorld().getNonSpectatingEntities(BeeEntity.class, new Box(pos));
                if (!bees.isEmpty()) {
                    ScoopItem.scoopBee(pointer.getWorld(), null, null, bees.get(0), stack);
                } else {
                    success = false;
                }
                return stack;
            }
        });
    }
}
