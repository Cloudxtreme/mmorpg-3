package rpg.server.core.obj;

import java.util.Map;
import java.util.Set;

import rpg.server.core.action.SimpleGameAction;
import rpg.server.core.attribute.FormulaAttrConfig;
import rpg.server.core.condition.SimpleGameCondition;
import rpg.server.core.event.EventHandler;
import rpg.server.core.event.GameEvent;
import rpg.server.core.event.GameEventChannel;
import rpg.server.core.event.GameEventType;
import rpg.server.core.relation.AbstractRelation;
import rpg.server.core.relation.SOBRelationTag;
import rpg.server.core.script.GameScriptConfig;

public interface GameObject {
	// ********************************************
	// **********基本接口
	// ********************************************
	GameObject getTarget();

	void remove();

	long getId();

	int getX();

	int getY();

	boolean isFriend(GameObject obj);

	void tick();

	// ********************************************
	// **********事件相关event
	// ********************************************

	/**
	 * 注册事件处理器
	 * 
	 * @param source
	 *            事件来源渠道
	 * @param handler
	 *            事件发生后的处理接口
	 * @param type
	 *            关注的事件类型
	 */
	void registerEventHandler(GameEventChannel source, EventHandler handler,
			GameEventType... type);

	/**
	 * 注销事件处理器
	 * 
	 * @param source
	 *            事件来源渠道
	 * @param handler
	 *            事件发生后的处理接口
	 * @param type
	 *            关注的事件类型
	 * 
	 */
	void removeEventHandler(GameEventChannel source, EventHandler handler,
			GameEventType... type);

	/**
	 * 接受事件
	 * 
	 * @param event
	 */
	void receiveEvent(GameEventChannel source, GameEvent event);

	/**
	 * 向指定的关联对象通报事件
	 * 
	 * @param related
	 * @param event
	 */
	void notifyEvent(String related, GameEvent event);

	// ********************************************
	// **********条件相关condition
	// ********************************************

	/**
	 * 检测简单条件
	 * 
	 * @param condition
	 * @param vars
	 * @return
	 */
	boolean checkSimpleCondition(SimpleGameCondition condition,
			Map<String, Object> vars);

	// ********************************************
	// **********行为相关action
	// ********************************************

	/**
	 * 执行简单行为
	 * 
	 * @param action
	 * @param vars
	 * @return
	 */
	boolean doSimpleAction(SimpleGameAction action, Map<String, Object> vars);

	// ********************************************
	// **********脚本相关script
	// ********************************************
	/**
	 * 加载脚本
	 * 
	 * @param script
	 */
	Set<Long> addScript(GameScriptConfig script);

	/**
	 * 加载脚本并设置临时参数表
	 * 
	 * @param script
	 * @param vars
	 *            临时参数名-参数值的映射表（默认无临时参数，{@link #addScript(GameScriptConfig)}）
	 */
	Set<Long> addScript(GameScriptConfig script, Map<String, Object> vars);

	// ********************************************
	// **********关系相关relation
	// ********************************************
	/**
	 * 获取指定关系且满足指定相互条件的关联对象
	 * 
	 * @param relation
	 *            以"."隔开的关联关系字符串（{@link SOBRelationTag}），例如：(self.)mate.team
	 * @return
	 */
	Set<GameObject> getRelatedSOBs(AbstractRelation relation);

	/**
	 * 根据关联关系标识获取关联对象（集合） <br/>
	 * 
	 * @param relations
	 * @return
	 */
	Set<GameObject> getRelatedSOBs(SOBRelationTag relation);

	FormulaAttrConfig getAttrConfig();
}
