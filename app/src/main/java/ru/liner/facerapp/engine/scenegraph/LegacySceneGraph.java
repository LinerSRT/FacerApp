package ru.liner.facerapp.engine.scenegraph;

import ru.liner.facerapp.engine.script.ScriptEngine;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public class LegacySceneGraph extends BaseSceneGraph {
    private final ScriptEngine<String, String> scriptEngine;

    public LegacySceneGraph(ScriptEngine<String, String> scriptEngine) {
        this.scriptEngine = scriptEngine;
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.clearsky.scenegraph.BaseSceneGraph, com.jeremysteckling.facerrel.lib.engine.clearsky.scenegraph.SceneGraph
    public synchronized void update(long currentTimeMillis) {
        super.update(currentTimeMillis);
        if (this.scriptEngine != null) {
            this.scriptEngine.update(currentTimeMillis);
        }
    }
}