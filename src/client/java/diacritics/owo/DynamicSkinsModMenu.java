package diacritics.owo;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class DynamicSkinsModMenu implements ModMenuApi {
  public ConfigScreenFactory<?> getModConfigScreenFactory() {
    return new DynamicSkinsModMenuConfigScreenFactory();
  }

  public static class DynamicSkinsModMenuConfigScreenFactory
      implements ConfigScreenFactory<DynamicSkinsConfigScreen> {

    @Override
    public DynamicSkinsConfigScreen create(Screen parent) {
      return new DynamicSkinsConfigScreen(parent);
    }
  }

  public static class DynamicSkinsConfigScreen extends Screen {
    public Screen parent;

    protected DynamicSkinsConfigScreen(Screen parent) {
      super(Text.literal("My tutorial screen"));
      this.parent = parent;
    }


    public ButtonWidget doneButton;
    public ButtonWidget reloadConfigButton;
    public ButtonWidget restartButton;

    @Override
    protected void init() {
      this.doneButton = ButtonWidget.builder(Text.literal("Done"), button -> {
        this.close();
      }).dimensions(this.width / 2 - 100, this.height - 40, 200, 20).build();

      this.reloadConfigButton =
          ButtonWidget.builder(Text.literal("Reload configuration"), button -> {
            DynamicSkins.config.resetCache();
          }).dimensions(this.width / 2 - 100, 20, 200, 20).build();

      this.addDrawableChild(this.doneButton);
      this.addDrawableChild(this.reloadConfigButton);

      if (DynamicSkins.dynamicSkinsError != null) {
        this.restartButton = ButtonWidget.builder(Text.literal("Restart"), button -> {
          DynamicSkins.config.resetCache();
          DynamicSkins.dynamicSkinsError = null;
        }).dimensions(this.width / 2 - 100, 50, 200, 20)
            .tooltip(Tooltip.of(Text.of(DynamicSkins.dynamicSkinsError.toString()))).build();
        this.addDrawableChild(this.restartButton);
      }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
      super.render(context, mouseX, mouseY, delta);
      this.renderBackground(context);
    }

    @Override
    public void close() {
      this.client.setScreen(this.parent);
    }
  }
}
