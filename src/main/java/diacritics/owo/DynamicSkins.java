package diacritics.owo;

import net.fabricmc.api.ModInitializer;
import diacritics.owo.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DynamicSkins implements ModInitializer {
	public static final String MOD_ID = "dynamic-skins";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final Config config = new Config("dynamic-skins", "hello");

	public static Exception dynamicSkinsError = null;

	@Override
	public void onInitialize() {
		LOGGER.info("hello from dynamic skins!");

		// create the config if it doesn't exist
		config.read();
	}
}
