package me.spri.addon.mixin;

import me.spri.addon.modules.VanillaXray;
import meteordevelopment.meteorclient.systems.modules.Modules;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class GlowRenderMixin {
    @Inject(method = "isGlowing", at = @At("RETURN"), cancellable = true)
    private void injected(CallbackInfoReturnable<Boolean> info) {
        if (!Modules.get().isActive(VanillaXray.class)) info.cancel();

        LivingEntity livingEntity = (LivingEntity) (Object) this;
        MinecraftClient client = MinecraftClient.getInstance();

        // Doesn't work in ide client because no UUID
        if (client.player != null)
            if(livingEntity.getUuid() == client.player.getUuid())
                info.cancel();


        if (!info.isCancelled())
            info.setReturnValue(true);
    }
}
