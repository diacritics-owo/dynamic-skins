package diacritics.owo.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.util.Identifier;
import org.json.JSONObject;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.mojang.authlib.GameProfile;
import diacritics.owo.DynamicSkins;
import diacritics.owo.scripting.Data;
import diacritics.owo.scripting.Skin;
import diacritics.owo.scripting.Skin.SkinContainer;

@Mixin(value = PlayerListEntry.class, priority = Integer.MAX_VALUE)
public abstract class PlayerListEntryMixin {
  @Shadow
  private GameProfile profile;

  @Inject(method = "getSkinTexture", at = @At("HEAD"), cancellable = true)
  public void onGetSkinTexture(CallbackInfoReturnable<Identifier> cir) {
    if (DynamicSkins.dynamicSkinsError == null) {
      Context cx = Context.enter();
      cx.getWrapFactory().setJavaPrimitiveWrap(false);

      MinecraftClient client = MinecraftClient.getInstance();

      try {
        Scriptable scope = cx.initSafeStandardObjects();
        ScriptableObject.defineClass(scope, Skin.class);

        SkinContainer skinContainer = new SkinContainer();
        Scriptable skin = cx.newObject(scope, "Skin", new Object[] {skinContainer});
        scope.put("skin", scope, skin);

        // TODO: neater way to do this?

        // https://stackoverflow.com/a/34776962
        cx.evaluateString(scope, """
            function deepFreeze (o) {
              Object.freeze(o);
              if (o === undefined) {
                return o;
              }

              Object.getOwnPropertyNames(o).forEach(function (prop) {
                if (o[prop] !== null
                && (typeof o[prop] === "object" || typeof o[prop] === "function")
                && !Object.isFrozen(o[prop])) {
                  deepFreeze(o[prop]);
                }
              });

              return o;
            };
            """, null, 0, null);

        JSONObject data = Data.buildData(
            Data.buildClientData(Data.buildPlayerData(client.player,
                Data.buildProfileData(client.player.getGameProfile()))),
            Data.buildTargetData(Data.buildProfileData(this.profile)));
        cx.evaluateString(scope, "const data = " + data + "; deepFreeze(data);", null, 0, null);

        scope.put("deepFreeze", scope, Context.getUndefinedValue());

        cx.evaluateString(scope, DynamicSkins.config.read(), null, 0, null);

        if (skinContainer.value() != null) {
          Identifier identifier = Identifier.tryParse(skinContainer.value());

          if (identifier != null) {
            cir.setReturnValue(identifier);
          }
        }
      } catch (Exception error) {
        DynamicSkins.LOGGER.warn(
            "encountered an error while evaluating the configuration! dynamic skins will stop and must be restarted from the mod menu screen",
            error);
        DynamicSkins.dynamicSkinsError = error;
      }

      Context.exit();
    }
  }
}
