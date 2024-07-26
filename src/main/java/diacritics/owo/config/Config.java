package diacritics.owo.config;

import net.fabricmc.loader.api.FabricLoader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import diacritics.owo.DynamicSkins;

public class Config {
  private String filename;
  private String defaultValue;

  private String cache = null;

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
      this.cache = null;
    }

    if (this.cache != null) return this.cache;

    try {
      this.cache = Files.readString(this.path());
      return this.cache;
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

  public void resetCache() {
    this.cache = null;
  }
}
