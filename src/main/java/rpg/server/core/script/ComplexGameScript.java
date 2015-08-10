package rpg.server.core.script;

import java.util.Map;


/**
 * 复合脚本实例
 *
 */
public abstract class ComplexGameScript extends GameScript implements ScriptListner {

	ComplexGameScript(GameScriptConfig config, Map<String, Object> vars) {
		super(config, vars);
	}
	

}
