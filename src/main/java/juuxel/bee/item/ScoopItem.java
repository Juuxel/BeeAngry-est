package juuxel.bee.item;

import juuxel.bee.BeeAngryest;
import juuxel.bee.BeeGameRules;
import juuxel.bee.criterion.BeeCriteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.Util;
import net.minecraft.world.World;

public class ScoopItem extends Item {
    public ScoopItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        World world = user.world;
        if (entity instanceof BeeEntity && !world.isClient) {
            ItemStack bee = new ItemStack(BeeAngryest.BEE);
            entity.removeAllPassengers();
            if (world.getGameRules().getBoolean(BeeGameRules.SAVE_SCOOPED_BEE_NBT)) {
                Util.make(bee.getOrCreateSubTag("Bee"), entity::saveToTag);
            }
            if (entity.hasCustomName()) {
                bee.setCustomName(entity.getCustomName());
            }
            if (world.getGameRules().getBoolean(BeeGameRules.ALWAYS_DROP_SCOOPED_BEES)) {
                ItemScatterer.spawn(world, entity.getX(), entity.getY(), entity.getZ(), bee);
            } else {
                user.inventory.offerOrDrop(world, bee);
            }
            stack.damage(1, user, player -> player.sendToolBreakStatus(hand));
            user.incrementStat(Stats.USED.getOrCreateStat(this));
            if (user instanceof ServerPlayerEntity) {
                BeeCriteria.BEE_SCOOPED.trigger((ServerPlayerEntity) user, stack, entity);
            }
            entity.remove();

            return true;
        }
        return super.useOnEntity(stack, user, entity, hand);
    }
}
