package juuxel.bee.mixin.client;

import net.minecraft.client.MinecraftClient;
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

    @Inject(method = "method_27022", at = @At("RETURN"), cancellable = true)
    private void onIsEntityGlowing(Entity entity, CallbackInfoReturnable<Boolean> info) {
        if (!info.getReturnValueZ()) {
            info.setReturnValue(entity instanceof BeeEntity && entity == targetedEntity);
        }
    }
}
