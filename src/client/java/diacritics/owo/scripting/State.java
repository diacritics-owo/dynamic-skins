package diacritics.owo.scripting;

import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.annotations.JSFunction;
import java.util.Map;
import java.util.HashMap;

public class State extends ScriptableObject {
  private StateContainer container = new StateContainer();

  public State() {
  }

  public State(StateContainer container) {
    this.container = container;
  }

  @JSFunction
  public Object get(String key) {
    return this.container.get(key);
  }

  @JSFunction
  public void set(String key, Object value) {
    this.container.set(key, value);
  }

  @Override
  public String getClassName() {
    return "State";
  }

  public static class StateContainer extends ScriptableObject {
    private Map<String, Object> state = new HashMap<>();

    public Object get(String key) {
      return this.state.get(key);
    }

    public void set(String key, Object value) {
      this.state.put(key, value);
    }

    @Override
    public String getClassName() {
      return "StateContainer";
    }
  }
}
