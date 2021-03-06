package juuxel.bee.mixin;

import juuxel.bee.BeeGameRules;
import juuxel.bee.ExtendedBee;
import juuxel.bee.criterion.BeeCriteria;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BeeEntity.class)
abstract class BeeEntityMixin extends AnimalEntity implements ExtendedBee {
    @Unique
    private static final float BEE_EXPLOSION_STRENGTH = 2.3f;

    @Unique
    private boolean nocturnal = false;

    @Unique
    private boolean shadowDisabled = false;

    protected BeeEntityMixin(EntityType<? extends AnimalEntity> type, World world) {
        super(type, world);
    }

    @Override
    public boolean beeAngryest_isNocturnal() {
        return nocturnal;
    }

    @Override
    public boolean beeAngryest_isShadowDisabled() {
        return shadowDisabled;
    }

    @Override
    public void beeAngryest_disableShadows() {
        shadowDisabled = true;
    }

    @Shadow
    public abstract boolean hasStung();

    @Inject(method = "tryAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/BeeEntity;setHasStung(Z)V"))
    private void onTryAttack(Entity entity, CallbackInfoReturnable<Boolean> info) {
        // Explode the bee when it attacks
        if (entity instanceof LivingEntity && !hasStung() && world.getGameRules().getBoolean(BeeGameRules.BEES_EXPLODE)) {
            world.createExplosion(this, getX(), getY(), getZ(), BEE_EXPLOSION_STRENGTH, Explosion.DestructionType.NONE);

            if (entity instanceof ServerPlayerEntity) {
                BeeCriteria.BEE_EXPLODED.trigger((ServerPlayerEntity) entity, this);
            }
        }
    }

    @Inject(method = "mobTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/BeeEntity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z", ordinal = 1))
    private void onMobTick(CallbackInfo info) {
        // Explode the bee when it dies from the lack of a stinger
        if (world.getGameRules().getBoolean(BeeGameRules.BEES_EXPLODE)) {
            world.createExplosion(this, getX(), getY(), getZ(), BEE_EXPLOSION_STRENGTH, Explosion.DestructionType.DESTROY);
        }
    }

    @Redirect(method = "canEnterHive", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;isRaining()Z", ordinal = 0))
    private boolean onCanEnterHive_redirectIsRaining(World world) {
        return world.isRaining() && world.getGameRules().getBoolean(BeeGameRules.BEES_SEEK_RAIN_SHELTER);
    }

    @Redirect(method = "canEnterHive", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;isNight()Z", ordinal = 0))
    private boolean onCanEnterHive_redirectIsNight(World world) {
        return nocturnal ? world.isDay() : world.isNight();
    }

    @Inject(method = "readCustomDataFromTag", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/nbt/CompoundTag;getInt(Ljava/lang/String;)I", ordinal = 2), cancellable = true)
    private void onReadCustomDataToTag(CompoundTag tag, CallbackInfo info) {
        nocturnal = tag.getBoolean("Nocturnal");

        // Prevent the game from crashing when client worlds are passed here
        if (!(world instanceof ServerWorld)) info.cancel();
    }

    @Inject(method = "writeCustomDataToTag", at = @At("RETURN"))
    private void onWriteCustomDataToTag(CompoundTag tag, CallbackInfo info) {
        tag.putBoolean("Nocturnal", nocturnal);
    }

    @Mixin(targets = "net.minecraft.entity.passive.BeeEntity$PollinateGoal")
    static class PollinateGoalMixin {
        @Redirect(method = {"canBeeStart()Z", "canBeeContinue()Z"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;isRaining()Z", ordinal = 0))
        private boolean redirectIsRaining(World world) {
            return world.isRaining() && world.getGameRules().getBoolean(BeeGameRules.BEES_SEEK_RAIN_SHELTER);
        }
    }
}
