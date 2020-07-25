package juuxel.bee.mixin;

import juuxel.bee.BeeAngryest;
import juuxel.bee.item.BeeItem;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(MobEntity.class)
abstract class MobEntityMixin {
    @Inject(method = "getPreferredEquipmentSlot", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private static void onGetPreferredEquipmentSlot(ItemStack stack, CallbackInfoReturnable<EquipmentSlot> info, Item item) {
        if (item == BeeAngryest.BEE && !BeeItem.isBaby(stack)) {
            info.setReturnValue(EquipmentSlot.HEAD);
        }
    }
}
