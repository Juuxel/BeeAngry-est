package juuxel.bee.mixin.client;

import juuxel.bee.BeeAngryest;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(BuiltinModelItemRenderer.class)
public class BuiltinModelItemRendererMixin {
    private final Entity renderBee = new BeeEntity(EntityType.BEE, MinecraftClient.getInstance().world);

    @Inject(method = "render", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void onRender(
            ItemStack stack, MatrixStack matrices, VertexConsumerProvider vertexConsumers,
            int light, int overlay, CallbackInfo info, Item item
    ) {
        if (item == BeeAngryest.BEE) {
            CompoundTag beeData = stack.getSubTag("Bee");
            if (beeData == null) return;

            MinecraftClient mc = MinecraftClient.getInstance();

            renderBee.setWorld(mc.world);
            renderBee.fromTag(beeData);

            mc.getEntityRenderManager().render(
                    renderBee,
                    /* xyz */ 0, 0, 0,
                    /* yaw */ 0,
                    /* tickDelta */ 0f,
                    matrices, vertexConsumers, light
            );
        }
    }
}
