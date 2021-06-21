package net.fabricmc.example.mixin;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.fabricmc.example.HackSupport;
import net.minecraft.block.BlockState;
import net.minecraft.block.FernBlock;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;

@Mixin(BlockRenderManager.class)
public class MixinBlockRenderManager {

	@Inject(method = "renderBlock", at = @At("HEAD"), cancellable = true)
	private void renderBlock_head(BlockState state, BlockPos pos, BlockRenderView world, MatrixStack matrix, VertexConsumer vertexConsumer, boolean cull, Random random, CallbackInfoReturnable<Boolean> ci) {
		if(HackSupport.xray) {
			if(!HackSupport.isVisible(state.getBlock())) {
				ci.setReturnValue(false);
			}
		}
		else if(HackSupport.wallhack) {
			ci.setReturnValue(false);
		}
	}

	@Inject(method = "renderBlock", at = @At("RETURN"))
	private void renderBlock_return(BlockState state, BlockPos pos, BlockRenderView world, MatrixStack matrix, VertexConsumer vertexConsumer, boolean cull, Random random, CallbackInfoReturnable<Boolean> ci) {
		vertexConsumer.unfixColor();
	}
}