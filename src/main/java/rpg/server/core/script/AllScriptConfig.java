package rpg.server.core.script;

import java.util.Map;

/**
 * 全部加载执行的复合脚本	<br/>
 * 加载时加载全部子脚本并执行（不考虑顺序）	<br/>
 * 所有子脚本执行成功，才算整个脚本执行成功	<br/>
 *
 */
public class AllScriptConfig extends ComplexGameScriptConfig {

	@Override
	GameScript getInstance(Map<String, Object> vars) {
		return new AllScript(this, vars);
	}

}
