package com.lay.betterbadges.common.mixin.entity;

import com.lay.betterbadges.common.render.entity.HighlightEntityManager;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Entity.class)
public class EntityMixin {

    @ModifyReturnValue(
            method = "isCurrentlyGlowing",
            at = @At("RETURN")
    )
    private boolean betterbadges$isCurrentlyGlowing(boolean original){
        for (HighlightEntityManager manager : HighlightEntityManager.managers) {
            if (manager.isHighlighted((Entity) (Object) this)) return true;
        }
        return original;
    }

    @ModifyReturnValue(
            method = "getTeamColor",
            at = @At("RETURN")
    )
    private int betterbadges$geTeamColor(int original){
        Entity entity = (Entity) (Object) this;
        for (HighlightEntityManager manager : HighlightEntityManager.managers) {
            if (manager.isHighlighted(entity)) return manager.getColor(entity);
        }
        return original;
    }

}
