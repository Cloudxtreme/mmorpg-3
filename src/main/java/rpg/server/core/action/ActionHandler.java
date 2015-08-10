package rpg.server.core.action;

import java.util.Map;

/**
 * action处理器
 * 
 */
@FunctionalInterface
public interface ActionHandler {
	/**
	 * 执行动作
	 * 
	 * @param action
	 * @param vars
	 *            临时参数表
	 * @return 执行结果
	 */
	boolean doAction(SimpleGameAction action, Map<String, Object> vars);
}
