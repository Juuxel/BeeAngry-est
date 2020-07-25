package juuxel.bee.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.Window;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

@Environment(EnvType.CLIENT)
public final class BeeAngryestClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HudRenderCallback.EVENT.register((matrices, tickDelta) -> {
            MinecraftClient mc = MinecraftClient.getInstance();
            if (mc.targetedEntity instanceof BeeEntity) {
                Text name = mc.targetedEntity.getDisplayName();
                Text message = new TranslatableText("gui.beeangry-est.hud.catch", name);

                Window window = mc.getWindow();
                TextRenderer font = mc.textRenderer;

                int x = (window.getScaledWidth() - font.getWidth(message)) / 2;
                int y = window.getScaledHeight() / 2 + 16;

                font.drawWithShadow(matrices, message, x, y, 0xFFFFFF);
            }
        });
    }
}
