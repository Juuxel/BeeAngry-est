package juuxel.bee.mixin.client;

import juuxel.bee.item.ScoopItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.BeeEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(MinecraftClient.class)
abstract class MinecraftClientMixin {
    @Shadow
    @Nullable
    public Entity targetedEntity;
    @Shadow
    @Nullable
    public ClientPlayerEntity player;

    @Inject(method = "hasOutline", at = @At("RETURN"), cancellable = true)
    private void onHasOutline(Entity entity, CallbackInfoReturnable<Boolean> info) {
        if (!info.getReturnValueZ() && player != null) {
            info.setReturnValue(entity instanceof BeeEntity && entity == targetedEntity && ScoopItem.hasScoop(player));
        }
    }
}
