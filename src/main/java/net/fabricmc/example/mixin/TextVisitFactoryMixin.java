package net.fabricmc.example.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import net.fabricmc.example.Client;
import net.minecraft.client.font.TextVisitFactory;

@Mixin(TextVisitFactory.class)
public abstract class TextVisitFactoryMixin
{
	@ModifyArg(at = @At(value = "INVOKE",
		target = "Lnet/minecraft/client/font/TextVisitFactory;visitFormatted(Ljava/lang/String;ILnet/minecraft/text/Style;Lnet/minecraft/text/Style;Lnet/minecraft/text/CharacterVisitor;)Z",
		ordinal = 0),
		method = {
			"visitFormatted(Ljava/lang/String;ILnet/minecraft/text/Style;Lnet/minecraft/text/CharacterVisitor;)Z"},
		index = 0)
	private static String adjustText(String text)
	{
		return Client.friends.protect(text);
	}
}