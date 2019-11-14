package juuxel.bee.item;

import juuxel.bee.BeeAngryest;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
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
            user.inventory.offerOrDrop(user.getEntityWorld(), bee);
            entity.remove();

            return true;
        }
        return super.useOnEntity(stack, user, entity, hand);
    }
}
