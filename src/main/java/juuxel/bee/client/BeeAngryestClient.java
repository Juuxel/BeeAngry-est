package juuxel.bee.client;

import juuxel.bee.BeeAngryest;
import juuxel.bee.item.ScoopItem;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.Window;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public final class BeeAngryestClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BuiltinItemRendererRegistry.INSTANCE.register(BeeAngryest.BEE, BeeItemRenderer.INSTANCE);

        HudRenderCallback.EVENT.register((matrices, tickDelta) -> {
            MinecraftClient mc = MinecraftClient.getInstance();
            if (mc.player == null) return;
            if (mc.targetedEntity instanceof BeeEntity) {
                boolean hasScoop = mc.player.getMainHandStack().getItem() instanceof ScoopItem || mc.player.getOffHandStack().getItem() instanceof ScoopItem;
                if (hasScoop) {
                    Text message = new TranslatableText("gui.beeangry-est.hud.catch");
                    Text name = mc.targetedEntity.getDisplayName();

                    Window window = mc.getWindow();
                    TextRenderer font = mc.textRenderer;

                    int x = window.getScaledWidth() / 2 - 2;
                    int y = window.getScaledHeight() / 2 + 16;
                    font.drawWithShadow(matrices, message, x + 4.5f, y, 0xFFFFFF);
                    font.drawWithShadow(matrices, name, x + font.getWidth(name)/2, y+8, 0xFFFFFF);

                    Text key = new TranslatableText("gui.beeangry-est.hud.use");
                    mc.getTextureManager().bindTexture(new Identifier("textures/gui/checkbox.png"));
                    DrawableHelper.drawTexture(matrices, x-17, y, 15, 0, 15, 15, 48, 48);
                    matrices.push();
                    matrices.scale(0.75f, 0.75f, 1);
                    font.drawWithShadow(matrices, key, (x-15)*1.33f, (y+5)*1.33f, 0xFFFFFF);
                    matrices.pop();
                }
            }
        });
    }
}
