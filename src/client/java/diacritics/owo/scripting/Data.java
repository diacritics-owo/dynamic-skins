package diacritics.owo.scripting;

import org.json.JSONObject;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.Registries;

public class Data {
  public static JSONObject buildData(JSONObject clientData, JSONObject targetData) {
    return new JSONObject().put("client", clientData).put("target", targetData);
  }

  public static JSONObject buildClientData(JSONObject playerData) {
    return new JSONObject().put("player", playerData);
  }

  public static JSONObject buildPlayerData(AbstractClientPlayerEntity abstractClientPlayerEntity) {
    return new JSONObject().put("profile", buildProfileData(abstractClientPlayerEntity.getGameProfile()))
        .put("equipment", buildEquipmentData(abstractClientPlayerEntity))
        .put("isTouchingWater", abstractClientPlayerEntity.isTouchingWater())
        .put("isClimbing", abstractClientPlayerEntity.isClimbing())
        .put("isSwimming", abstractClientPlayerEntity.isSwimming())
        .put("isSneaking", abstractClientPlayerEntity.isSneaking())
        .put("isOnFire", abstractClientPlayerEntity.isOnFire())
        .put("isInLava", abstractClientPlayerEntity.isInLava())
        .put("isSprinting", abstractClientPlayerEntity.isSprinting());
  }

  public static JSONObject buildProfileData(GameProfile profile) {
    return new JSONObject().put("username", profile.getName());
  }

  public static JSONObject buildEquipmentData(
      AbstractClientPlayerEntity abstractClientPlayerEntity) {
    return new JSONObject()
        .put("head", Registries.ITEM.getId(abstractClientPlayerEntity.getEquippedStack(EquipmentSlot.HEAD).getItem()))
        .put("chest", Registries.ITEM.getId(abstractClientPlayerEntity.getEquippedStack(EquipmentSlot.CHEST).getItem()))
        .put("legs", Registries.ITEM.getId(abstractClientPlayerEntity.getEquippedStack(EquipmentSlot.LEGS).getItem()))
        .put("feet", Registries.ITEM.getId(abstractClientPlayerEntity.getEquippedStack(EquipmentSlot.FEET).getItem()))
        .put("mainhand",
            Registries.ITEM.getId(abstractClientPlayerEntity.getEquippedStack(EquipmentSlot.MAINHAND).getItem()))
        .put("offhand",
            Registries.ITEM.getId(abstractClientPlayerEntity.getEquippedStack(EquipmentSlot.OFFHAND).getItem()));
  }

  public static JSONObject buildTargetData(JSONObject playerData) {
    return new JSONObject().put("player", playerData);
  }
}
