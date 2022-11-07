package me.spri.addon.mixin;

import me.spri.addon.modules.DemoCanceler;
import meteordevelopment.meteorclient.systems.modules.Modules;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.mob.GuardianEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PatrolEntity;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WorldRenderer.class)
public abstract class ChangeTeamColorMixin {
    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getTeamColorValue()I"))
    private int injected(Entity entity) {
        int i = entity.getTeamColorValue(); // default

        if (!Modules.get().isActive(DemoCanceler.class)) return i;
        if (i != 0xFFFFFF) return i;


        if (entity instanceof PlayerEntity)
            return 0xFFAA00; // GOLD

        if (entity instanceof EnderDragonEntity || entity instanceof WitherEntity)
            return 0xFF55FF; // LIGHT PURPLE

        if (entity instanceof PatrolEntity)
            return 170; // BLUE

        if (entity instanceof WaterCreatureEntity)
            return 43690; // DARK AQUA

        if (entity instanceof GuardianEntity)
            return 0xAAAAAA; // GRAY

        if (entity instanceof MerchantEntity)
            return 0xFFFF55; // YELLOW

        if (entity instanceof PassiveEntity)
            return 43520; // DARK GREEN

        if (entity instanceof HostileEntity)
            return 0xAA0000; // DARK RED

        return i;
    }
    /*
          BLACK("BLACK", '0', 0, 0)
          DARK_BLUE("DARK_BLUE", '1', 1, 170)
          DARK_GREEN("DARK_GREEN", '2', 2, 43520)
          DARK_AQUA("DARK_AQUA", '3', 3, 43690)
          DARK_RED("DARK_RED", '4', 4, 0xAA0000)
          DARK_PURPLE("DARK_PURPLE", '5', 5, 0xAA00AA)
          GOLD("GOLD", '6', 6, 0xFFAA00)
          GRAY("GRAY", '7', 7, 0xAAAAAA)
          DARK_GRAY("DARK_GRAY", '8', 8, 0x555555)
          BLUE("BLUE", '9', 9, 0x5555FF)
          GREEN("GREEN", 'a', 10, 0x55FF55)
          AQUA("AQUA", 'b', 11, 0x55FFFF)
          RED("RED", 'c', 12, 0xFF5555)
          LIGHT_PURPLE("LIGHT_PURPLE", 'd', 13, 0xFF55FF)
          YELLOW("YELLOW", 'e', 14, 0xFFFF55)
          WHITE("WHITE", 'f', 15, 0xFFFFFF)
     */
}
