package net.fabricmc.example;

import net.minecraft.client.MinecraftClient;

public interface ClientSupport {
	
	public MinecraftClient mc = MinecraftClient.getInstance();
	
}
