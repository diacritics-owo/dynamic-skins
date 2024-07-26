package diacritics.owo.mixin.client;

import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = PlayerListEntry.class, priority = Integer.MAX_VALUE)
public abstract class PlayerListEntryMixin {
  @Inject(method = "getSkinTexture", at = @At("HEAD"), cancellable = true)
  public void onGetSkinTexture(CallbackInfoReturnable<Identifier> cir) {
    cir.setReturnValue(Identifier.of("minecraft", "textures/entity/zombie/drowned_outer_layer.png"));
  }
}

