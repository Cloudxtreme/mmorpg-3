package rpg.server.core.condition;

import java.util.Map;

/**
 * 条件检测处理
 * 
 */
public interface ConditionHandler {
	/**
	 * 检测条件
	 * 
	 * @param condition
	 * @param vars
	 *            临时参数表
	 * @return
	 */
	boolean checkCondition(SimpleGameCondition condition,
			Map<String, Object> vars);
}
