package juuxel.bee.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BeeItem extends Item {
    public BeeItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        place(context.getWorld(), context.getBlockPos().offset(context.getSide()), context.getPlayer(), context.getStack());
        return ActionResult.SUCCESS;
    }

    private void place(World world, BlockPos pos, PlayerEntity user, ItemStack stack) {
        CompoundTag beeData = stack.getSubTag("Bee");
        if (world instanceof ServerWorld) {
            ServerWorld w = (ServerWorld) world;
            Entity bee;
            if (beeData == null) {
                bee = EntityType.BEE.create(w);
            } else {
                beeData.remove("Passengers");
                beeData.remove("Leash");
                beeData.removeUuid("UUID");
                bee = EntityType.loadEntityWithPassengers(beeData, w, e -> e);
            }
            if (bee == null) {
                throw new NullPointerException("Failed to load or create a bee!");
            }
            if (stack.hasCustomName()) {
                bee.setCustomName(stack.getName());
            }
            bee.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
            world.spawnEntity(bee);
        }
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        user.playSound(SoundEvents.BLOCK_BEEHIVE_EXIT, 1f, 1f);
        if (!user.abilities.creativeMode) {
            stack.decrement(1);
        }
    }

    @Override
    public String getTranslationKey() {
        return EntityType.BEE.getTranslationKey();
    }
}
