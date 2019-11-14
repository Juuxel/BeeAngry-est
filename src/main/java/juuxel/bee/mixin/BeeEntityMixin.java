package juuxel.bee.mixin;

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
}
