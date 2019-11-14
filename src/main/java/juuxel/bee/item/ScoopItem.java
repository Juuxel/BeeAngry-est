package juuxel.bee.item;

import juuxel.bee.BeeAngryest;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.Util;

public class ScoopItem extends Item {
    public ScoopItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (entity instanceof BeeEntity && !user.getEntityWorld().isClient) {
            ItemStack bee = new ItemStack(BeeAngryest.BEE);
            entity.removeAllPassengers();
            Util.create(bee.getOrCreateSubTag("Bee"), entity::saveToTag);
            ItemScatterer.spawn(entity.world, entity.getX(), entity.getY(), entity.getZ(), bee);
            entity.remove();
            stack.damage(1, user, player -> player.sendToolBreakStatus(hand));
            user.incrementStat(Stats.USED.getOrCreateStat(this));

            return true;
        }
        return super.useOnEntity(stack, user, entity, hand);
    }
}
