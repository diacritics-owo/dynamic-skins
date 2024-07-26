package diacritics.owo.scripting;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;

public class Data {
  private ClientData client = new ClientData();

  public ClientData client() {
    return this.client;
  }

  public static class ClientData {
    private MinecraftClient client = MinecraftClient.getInstance();
    private PlayerData player = new PlayerData(client.player);

    public PlayerData player() {
      return this.player;
    }
  }

  public static class PlayerData {
    private AbstractClientPlayerEntity abstractClientPlayerEntity;

    public PlayerData(AbstractClientPlayerEntity abstractClientPlayerEntity) {
      this.abstractClientPlayerEntity = abstractClientPlayerEntity;
    }

    public Boolean isTouchingWater() {
      return this.abstractClientPlayerEntity.isTouchingWater();
    }

    public Boolean isSwimming() {
      return this.abstractClientPlayerEntity.isSwimming();
    }

    public Boolean isSneaking() {
      return this.abstractClientPlayerEntity.isSneaking();
    }

    public Boolean isOnFire() {
      return this.abstractClientPlayerEntity.isOnFire();
    }

    public Boolean isInLava() {
      return this.abstractClientPlayerEntity.isInLava();
    }

    public Boolean isSprinting() {
      return this.abstractClientPlayerEntity.isSprinting();
    }
  }
}
