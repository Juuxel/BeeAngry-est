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
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.Util;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class ScoopItem extends Item {
    public ScoopItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        World world = user.world;
        if (entity instanceof BeeEntity) {
            scoopBee(world, user, hand, (BeeEntity) entity, stack);
            world.playSound(user, entity.getBlockPos(), SoundEvents.ENTITY_PLAYER_HURT_SWEET_BERRY_BUSH, SoundCategory.PLAYERS, 1f, 1.5f);
            return ActionResult.SUCCESS;
        }
        return super.useOnEntity(stack, user, entity, hand);
    }

    public static boolean hasScoop(PlayerEntity player) {
        return player.getMainHandStack().getItem() instanceof ScoopItem || player.getOffHandStack().getItem() instanceof ScoopItem;
    }

    public static void scoopBee(World world, @Nullable PlayerEntity user, @Nullable Hand hand, BeeEntity entity, @Nullable ItemStack scoopStack) {
        scoopBee(world, user, hand, entity, scoopStack, bee -> {
            if (world.getGameRules().getBoolean(BeeGameRules.ALWAYS_DROP_SCOOPED_BEES) || user == null) {
                ItemScatterer.spawn(world, entity.getX(), entity.getY(), entity.getZ(), bee);
            } else {
                user.inventory.offerOrDrop(world, bee);
            }
        });
    }

    public static void scoopBee(World world, @Nullable PlayerEntity user, @Nullable Hand hand, BeeEntity entity, @Nullable ItemStack scoopStack, Consumer<ItemStack> handler) {
        if (!entity.world.isClient) {
            ItemStack bee = new ItemStack(BeeAngryest.BEE);
            entity.removeAllPassengers();
            if (world.getGameRules().getBoolean(BeeGameRules.SAVE_SCOOPED_BEE_NBT)) {
                Util.make(bee.getOrCreateSubTag("Bee"), entity::saveToTag);
            }
            if (entity.hasCustomName()) {
                bee.setCustomName(entity.getCustomName());
            }
            handler.accept(bee);
            if (user != null) {
                if (scoopStack != null) {
                    scoopStack.damage(1, user, player -> player.sendToolBreakStatus(hand));
                    user.incrementStat(Stats.USED.getOrCreateStat(scoopStack.getItem()));
                }

                if (user instanceof ServerPlayerEntity) {
                    BeeCriteria.BEE_SCOOPED.trigger((ServerPlayerEntity) user, scoopStack, entity);
                }
            } else {
                if (scoopStack != null) {
                    scoopStack.damage(1, world.random, null);
                }
            }
            entity.remove();
        }
    }
}
