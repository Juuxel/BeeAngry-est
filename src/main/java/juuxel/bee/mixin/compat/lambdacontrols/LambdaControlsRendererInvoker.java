package juuxel.bee.mixin.compat.lambdacontrols;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(targets = "me.lambdaurora.lambdacontrols.client.gui.LambdaControlsRenderer")
public interface LambdaControlsRendererInvoker {
    @Invoker(value = "drawButton")
    static int drawButton(MatrixStack matrices, int x, int y, int button, @NotNull MinecraftClient client){
        throw new IllegalStateException("Dummy method body should not be invoked. Critical mixin failure.");
    }
}
