package juuxel.bee.mixin.compat.lambdacontrols;

import juuxel.bee.client.BeeAngryestClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Environment(EnvType.CLIENT)
@Mixin(BeeAngryestClient.class)
public abstract class BeeAngryestClientMixin {

    @Inject(method = "lambda$onInitializeClient$0(Lnet/minecraft/client/util/math/MatrixStack;F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/text/TranslatableText;<init>(Ljava/lang/String;)V", ordinal = 1), locals = LocalCapture.CAPTURE_FAILHARD, remap = false, cancellable = true)
    private static void inject(MatrixStack matrices, float tickDelta, CallbackInfo ctx, MinecraftClient mc, boolean hasScoop, Text message, Text name, Window window, TextRenderer font, int x, int y) {
        ctx.cancel();
        LambdaControlsRendererInvoker.drawButton(matrices, x - 17, y - 3, 104, mc);
    }
}
