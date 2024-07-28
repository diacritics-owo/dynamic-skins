package diacritics.owo.scripting;

import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.annotations.JSFunction;
import org.mozilla.javascript.annotations.JSGetter;

public class Skin extends ScriptableObject {
  private SkinContainer container = null;

  public Skin() {
    this.container = new SkinContainer();
  }

  public Skin(SkinContainer container) {
    this.container = container;
  }

  @JSGetter
  public String value() {
    return this.container.value();
  }

  @JSFunction
  public void set(String value) {
    this.container.set(value);
  }

  @Override
  public String getClassName() {
    return "Skin";
  }

  public static class SkinContainer extends ScriptableObject {
    private String value = null;

    public String value() {
      return this.value;
    }

    public void set(String value) {
      this.value = value;
    }

    @Override
    public String getClassName() {
      return "SkinContainer";
    }
  }
}
