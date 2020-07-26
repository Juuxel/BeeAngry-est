package juuxel.bee.client;

import juuxel.bee.item.ScoopItem;
import me.lambdaurora.lambdacontrols.ControlsMode;
import me.lambdaurora.lambdacontrols.client.LambdaControlsClient;
import me.lambdaurora.lambdacontrols.client.compat.CompatHandler;
import me.lambdaurora.lambdacontrols.client.compat.LambdaControlsCompat;
import me.lambdaurora.lambdacontrols.client.controller.ButtonBinding;
import me.lambdaurora.lambdacontrols.client.gui.LambdaControlsRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.BlockHitResult;

import javax.annotation.Nullable;

@Environment(EnvType.CLIENT)
enum BeeLambdaControlsCompat implements CompatHandler {
    INSTANCE;

    private LambdaControlsClient lambdaControls;

    static void init() {
        BeeAngryestClient.useLabelRenderer = (matrices, mc, font, x, y) -> {
            if (INSTANCE.lambdaControls.config.getControlsMode() == ControlsMode.CONTROLLER) {
                LambdaControlsRenderer.drawButton(matrices, x - 17, y - 3, ButtonBinding.USE, mc);
            } else {
                BeeAngryestClient.renderUseLabel(matrices, mc, font, x, y);
            }
        };

        LambdaControlsCompat.registerCompatHandler(INSTANCE);
    }

    @Override
    public void handle(LambdaControlsClient mod) {
        lambdaControls = mod;
    }

    @Override
    public String getUseActionAt(MinecraftClient client, @Nullable BlockHitResult placeResult) {
        if (client.targetedEntity instanceof BeeEntity) {
            PlayerEntity player = client.player;

            if (player == null) return null;
            if (ScoopItem.hasScoop(player)) {
                return "gui.beeangry-est.hud.catch";
            }
        }

        return null;
    }
}
