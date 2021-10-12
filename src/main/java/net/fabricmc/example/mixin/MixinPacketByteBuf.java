package net.fabricmc.example.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtTagSizeTracker;
import net.minecraft.network.PacketByteBuf;

@Mixin(PacketByteBuf.class)
public class MixinPacketByteBuf extends PacketByteBuf {
	
	public MixinPacketByteBuf(ByteBuf parent) {
		super(parent);
	}

	@Inject(method = "readNbt", at = @At("HEAD"), cancellable = true)
	public void readNbt(CallbackInfoReturnable<NbtCompound> callback) {
		callback.setReturnValue(this.readNbt(new NbtTagSizeTracker(999999999L)));
	}
	
}
