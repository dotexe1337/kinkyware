package net.fabricmc.example.mixin;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.example.Client;
import net.fabricmc.example.ClientSupport;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.util.Identifier;

@Mixin(PlayerListEntry.class)
public abstract class PlayerListEntryMixin implements ClientSupport {
    @Accessor
    public abstract Map<MinecraftProfileTexture.Type, Identifier> getTextures();

    @Accessor
    public abstract GameProfile getProfile();

    @Environment(EnvType.CLIENT)
    @Inject(method = "getCapeTexture", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/PlayerListEntry;loadTextures()V"))
    public void injectIntoTextures(CallbackInfoReturnable<Identifier> cir) {
        if(Client.friends.isFriend(getProfile().getName())) {
        	Identifier cape = new Identifier("kinkyware", "cape.png");
            getTextures().put(MinecraftProfileTexture.Type.CAPE, cape);
        } else {
            getTextures().remove(MinecraftProfileTexture.Type.CAPE);
        }
    }
}