package juuxel.bee.mixin.beebetter;

import com.github.draylar.beebetter.entity.ModdedBeehiveBlockEntity;
import com.github.draylar.beebetter.util.BeeState;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import javax.annotation.Nullable;
import java.util.List;

@Mixin(ModdedBeehiveBlockEntity.class)
public class ModdedBeehiveBlockEntityMixin extends BlockEntity {
    public ModdedBeehiveBlockEntityMixin(BlockEntityType<?> type) {
        super(type);
    }

    @Redirect(method = "releaseBee", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;isNight()Z"))
    private boolean onReleaseBee_redirectIsNight(World world, BlockState state, CompoundTag tag, @Nullable List<Entity> entities, BeeState beeState) {
        return tag.getBoolean("Nocturnal") ? world.isDay() : world.isNight();
    }
}
