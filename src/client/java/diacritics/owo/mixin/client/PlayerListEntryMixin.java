package diacritics.owo.mixin.client;

import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import diacritics.owo.DynamicSkins;
import diacritics.owo.scripting.Data;
import diacritics.owo.scripting.Skin;
import javax.script.ScriptException;

@Mixin(value = PlayerListEntry.class, priority = Integer.MAX_VALUE)
public abstract class PlayerListEntryMixin {
  @Inject(method = "getSkinTexture", at = @At("HEAD"), cancellable = true)
  public void onGetSkinTexture(CallbackInfoReturnable<Identifier> cir) {
    if (DynamicSkins.dynamicSkinsError == null) {
      try {
        Skin skin = new Skin();

        DynamicSkins.scriptEngine.put("data", new Data());
        DynamicSkins.scriptEngine.put("skin", skin);

        DynamicSkins.scriptEngine.eval(DynamicSkins.config.read());

        if (skin.get() != null) {
          Identifier identifier = Identifier.tryParse(skin.get());

          if (identifier != null) {
            cir.setReturnValue(identifier);
          }
        }
      } catch (ScriptException error) {
        DynamicSkins.LOGGER.warn(
            "encountered an error while evaluating the configuration! dynamic skins will stop and you must be restarted from the mod menu screen",
            error);
        DynamicSkins.dynamicSkinsError = error;
      }
    }
  }
}
