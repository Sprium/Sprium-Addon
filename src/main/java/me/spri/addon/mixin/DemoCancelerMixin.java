package me.spri.addon.mixin;

import me.spri.addon.modules.DemoCanceler;
import meteordevelopment.meteorclient.systems.modules.Modules;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.s2c.play.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public class DemoCancelerMixin {

    @Inject(method = "handlePacket", at = @At("HEAD"), cancellable = true)
    private static <T extends PacketListener> void injected(Packet<T> packet, PacketListener listener, CallbackInfo info) {
        if (!Modules.get().isActive(DemoCanceler.class) &&
            packet instanceof GameStateChangeS2CPacket||
            packet instanceof WorldBorderCenterChangedS2CPacket ||
            packet instanceof WorldBorderInitializeS2CPacket ||
            packet instanceof WorldBorderInterpolateSizeS2CPacket ||
            packet instanceof WorldBorderWarningTimeChangedS2CPacket ||
            packet instanceof WorldBorderWarningBlocksChangedS2CPacket
        )
            info.cancel();

    }
}
