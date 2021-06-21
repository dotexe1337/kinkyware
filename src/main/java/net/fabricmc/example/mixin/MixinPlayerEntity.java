package net.fabricmc.example.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.fabricmc.example.HackSupport;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.FluidTags;
import net.minecraft.world.World;

@Mixin(PlayerEntity.class)
public abstract class MixinPlayerEntity extends LivingEntity {

	@Shadow public PlayerInventory inventory;

	protected MixinPlayerEntity(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method = "getBlockBreakingSpeed", at = @At("HEAD"), cancellable = true)
	public void getBlockBreakingSpeed(BlockState block, CallbackInfoReturnable<Float> ci) {

		if (HackSupport.speedMine) {
			float breakingSpeed = inventory.getBlockBreakingSpeed(block);
			if (breakingSpeed > 1.0F) {
				int eff = EnchantmentHelper.getEfficiency(this);
				ItemStack itemStack_1 = this.getMainHandStack();
				if (eff > 0 && !itemStack_1.isEmpty()) {
					breakingSpeed += eff * eff + 1;
				}
			}

			if (StatusEffectUtil.hasHaste(this)) {
				breakingSpeed *= 1.0F + (StatusEffectUtil.getHasteAmplifier(this) + 1) * 0.2F;
			}

			breakingSpeed *= (float) 1.5f;

			ci.setReturnValue(breakingSpeed);
		} else {
			float breakingSpeed = inventory.getBlockBreakingSpeed(block);
			if (breakingSpeed > 1.0F) {
				int eff = EnchantmentHelper.getEfficiency(this);
				ItemStack itemStack_1 = this.getMainHandStack();
				if (eff > 0 && !itemStack_1.isEmpty()) {
					breakingSpeed += eff * eff + 1;
				}
			}

			if (StatusEffectUtil.hasHaste(this)) {
				breakingSpeed *= 1.0F + (StatusEffectUtil.getHasteAmplifier(this) + 1) * 0.2F;
			}

			ci.setReturnValue(breakingSpeed);
		}
	}
}