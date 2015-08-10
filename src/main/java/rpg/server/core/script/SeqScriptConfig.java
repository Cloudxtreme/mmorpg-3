package rpg.server.core.script;

import java.util.Map;

/**
 * 顺序加载执行的复合脚本<br/>
 * 仅当序列中的前一个脚本执行成功后，后一个脚本才被添加
 * 仅当整个序列成功执行完毕（一轮）后，才算执行成功<br/>
 * 
 */
public class SeqScriptConfig extends ComplexGameScriptConfig {

	@Override
	GameScript getInstance(Map<String, Object> vars) {
		return new SeqScript(this, vars);
	}

}
