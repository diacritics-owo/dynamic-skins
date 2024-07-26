package diacritics.owo.config;

import net.fabricmc.loader.api.FabricLoader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import diacritics.owo.DynamicSkins;

public class Config {
  private String filename;
  private String defaultValue;

  public Config(String filename, String defaultValue) {
    this.filename = filename;
    this.defaultValue = defaultValue;
  }

  public String filename() {
    return this.filename;
  }

  public Path path() {
    return FabricLoader.getInstance().getConfigDir().resolve(this.filename + ".js");
  }

  public Boolean exists() {
    return Files.exists(this.path());
  }

  public String read() {
    if (!this.exists()) {
      DynamicSkins.LOGGER.info("could not find a configuration file for dynamic skins, so the default config will be written to {}", this.path());
      this.write(defaultValue);
    }

    try {
      return Files.readString(this.path());
    } catch (IOException error) {
      DynamicSkins.LOGGER.error("failed to read dynamic skins config at {}!", this.path(), error);
      return "";
    }
  }

  public void write(String value) {
    try {
      this.path().getParent().toFile().mkdirs();
      Files.writeString(this.path(), value);
    } catch (IOException error) {
      DynamicSkins.LOGGER.error("failed to write dynamic skins config at {}!", this.path(), error);
    }
  }
}
