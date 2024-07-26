package diacritics.owo.scripting;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import org.mozilla.javascript.engine.RhinoScriptEngineFactory;

public class Scripting {
  public static ScriptEngineManager createScriptEngineManager() {
    ScriptEngineManager manager = new ScriptEngineManager();
    manager.registerEngineName("rhino", new RhinoScriptEngineFactory());
    return manager;
  }

  public static ScriptEngine createScriptEngine(ScriptEngineManager manager) {
    ScriptEngine engine = manager.getEngineByName("rhino");
    return engine;
  }
}
