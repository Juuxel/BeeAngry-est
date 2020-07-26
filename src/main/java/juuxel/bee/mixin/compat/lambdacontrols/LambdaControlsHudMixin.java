package juuxel.bee.mixin.compat.lambdacontrols;

import juuxel.bee.item.ScoopItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.BeeEntity;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(targets = "me.lambdaurora.lambdacontrols.client.gui.LambdaControlsHud")
public abstract class LambdaControlsHudMixin {
    @Shadow(remap = false) private MinecraftClient client;

    @ModifyVariable(method = "tick()V", at=@At(value = "FIELD", target = "Lme/lambdaurora/lambdacontrols/client/gui/LambdaControlsHud;placeAction:Ljava/lang/String;", opcode = Opcodes.PUTFIELD, shift = At.Shift.BEFORE, remap = false), name = "placeAction", remap = false)
    private String beeangryest_catchBee(String placeAction){
        ClientPlayerEntity player = this.client.player;
        if (player == null) { return placeAction; }
        Entity e = this.client.targetedEntity;
        if (e instanceof BeeEntity) {
            boolean hasScoop = client.player.getMainHandStack().getItem() instanceof ScoopItem || client.player.getOffHandStack().getItem() instanceof ScoopItem;
            if (hasScoop){
                return "gui.beeangry-est.hud.catch";
            }
        }
        return placeAction;
    }
}
