package juuxel.bee.mixin;

import juuxel.bee.BeeGameRules;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BeeEntity.class)
public abstract class BeeEntityMixin extends AnimalEntity {
    @Unique
    private static final float BEE_EXPLOSION_STRENGTH = 2.3f;

    protected BeeEntityMixin(EntityType<? extends AnimalEntity> type, World world) {
        super(type, world);
    }

    @Inject(method = "tryAttack", at = @At("RETURN"))
    private void onTryAttack(Entity entity, CallbackInfoReturnable<Boolean> info) {
        if (info.getReturnValueZ()) {
            world.createExplosion(this, getX(), getY(), getZ(), BEE_EXPLOSION_STRENGTH, Explosion.DestructionType.NONE);
        }
    }

    @Inject(method = "mobTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/BeeEntity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z", ordinal = 1))
    private void onMobTick(CallbackInfo info) {
        world.createExplosion(this, getX(), getY(), getZ(), BEE_EXPLOSION_STRENGTH, Explosion.DestructionType.DESTROY);
    }

    @Redirect(method = "canEnterHive", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;isRaining()Z", ordinal = 0))
    private boolean redirectIsRaining(World world) {
        return world.isRaining() && world.getGameRules().getBoolean(BeeGameRules.BEES_SEEK_RAIN_SHELTER);
    }

    @Mixin(targets = "net.minecraft.entity.passive.BeeEntity$PollinateGoal")
    static class PollinateGoalMixin {
        @SuppressWarnings("InvalidMemberReference")
        @Redirect(method = {"canBeeStart", "canBeeContinue"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;isRaining()Z", ordinal = 0))
        private boolean redirectIsRaining(World world) {
            return world.isRaining() && world.getGameRules().getBoolean(BeeGameRules.BEES_SEEK_RAIN_SHELTER);
        }
    }
}
