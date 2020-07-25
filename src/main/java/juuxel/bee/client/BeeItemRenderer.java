package juuxel.bee.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Lazy;
import net.minecraft.world.World;

@Environment(EnvType.CLIENT)
enum BeeItemRenderer implements BuiltinItemRenderer {
    INSTANCE;

    private World currentWorld;
    private BeeEntity defaultBee;
    private BeeEntity dataBee;

    @Override
    public void render(ItemStack stack, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (currentWorld != mc.world) {
            defaultBee = EntityType.BEE.create(mc.world);
            dataBee = EntityType.BEE.create(mc.world);
            currentWorld = mc.world;
        }

        BeeEntity bee;
        CompoundTag tag = stack.getSubTag("Bee");

        if (tag != null) {
            bee = dataBee;
            bee.fromTag(tag);
        } else {
            bee = defaultBee;
        }

        bee.setYaw(0);
        bee.setHeadYaw(0);

        mc.getEntityRenderManager().render(bee, 0, 0, 0, 0, 0.5f, matrices, vertexConsumers, light);
    }
}
