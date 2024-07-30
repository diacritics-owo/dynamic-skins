package diacritics.owo;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.text.Text;
import static net.minecraft.server.command.CommandManager.literal;

public class DynamicSkinsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(literal("dynamic-skins")
					.then(literal("cache")
							.then(literal("reload").executes(context -> {
								DynamicSkins.config.resetCache();
								context.getSource().sendFeedback(() -> Text.of("Successfully reloaded configuration cache"), false);
								return 1;
							})))
					.then(literal("error")
							.executes(context -> {
								context.getSource().sendFeedback(
										() -> Text.of(DynamicSkins.dynamicSkinsError == null ? "No errors found"
												: DynamicSkins.dynamicSkinsError.toString()),
										false);
								return 1;
							})
							.then(literal("reset").executes(context -> {
								DynamicSkins.config.resetCache();
								DynamicSkins.dynamicSkinsError = null;
								context.getSource().sendFeedback(
										() -> Text.of("Dynamic Skins has been restarted"),
										false);
								return 1;
							}))));
		});
	}
}
