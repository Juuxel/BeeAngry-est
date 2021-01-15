package juuxel.bee.client;

import juuxel.bee.ExtendedBee;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BeeItemRenderer extends BuiltinModelItemRenderer {
    private World currentWorld;
    private BeeEntity defaultBee;
    private BeeEntity dataBee;

    @Override
    public void render(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
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
        ((ExtendedBee) bee).beeAngryest_disableShadows();

        EntityRenderDispatcher renderDispatcher = mc.getEntityRenderDispatcher();
        renderDispatcher.render(bee, 0, 0, 0, 0, 0.5f, matrices, vertexConsumers, light);
    }
}
