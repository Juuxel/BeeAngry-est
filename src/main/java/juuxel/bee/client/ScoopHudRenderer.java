package juuxel.bee.client;

import juuxel.bee.item.ScoopItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

@OnlyIn(Dist.CLIENT)
public final class ScoopHudRenderer {
    private static void renderUseLabel(MatrixStack matrices, MinecraftClient mc, TextRenderer font, int x, int y) {
        Text key = new TranslatableText("gui.beeangry-est.hud.use");
        mc.getTextureManager().bindTexture(new Identifier("textures/gui/checkbox.png"));
        DrawableHelper.drawTexture(matrices, x - 17, y, 15, 0, 15, 15, 48, 48);
        matrices.push();
        matrices.scale(0.75f, 0.75f, 1);
        font.drawWithShadow(matrices, key, (x - 15) * 1.33f, (y + 5) * 1.33f, 0xFFFFFF);
        matrices.pop();
    }

    public static void render(RenderGameOverlayEvent.Post event) {
        // Render after everything
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) return;

        MinecraftClient mc = MinecraftClient.getInstance();
        MatrixStack matrices = event.getMatrixStack();

        if (mc.targetedEntity instanceof BeeEntity) {
            if (mc.player == null) return;
            boolean hasScoop = ScoopItem.hasScoop(mc.player);
            if (hasScoop) {
                Text message = new TranslatableText("gui.beeangry-est.hud.catch");
                Text name = mc.targetedEntity.getDisplayName();

                Window window = mc.getWindow();
                TextRenderer font = mc.textRenderer;

                int x = window.getScaledWidth() / 2 - 2;
                int y = window.getScaledHeight() / 2 + 16;
                int mw = font.getWidth(message);
                int nw = font.getWidth(name);
                int offset = mw > nw ? mw / 8 : nw / 8;
                font.drawWithShadow(matrices, message, x + offset, y, 0xFFFFFF);
                font.drawWithShadow(matrices, name, x + offset, y + 8, 0xFFFFFF);

                renderUseLabel(matrices, mc, font, x, y);
            }
        }
    }
}
