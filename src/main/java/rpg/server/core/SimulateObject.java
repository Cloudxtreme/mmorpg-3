package rpg.server.core;

import java.util.Map;
import java.util.Set;

import rpg.server.core.action.ActionHandler;
import rpg.server.core.action.SimpleGameAction;
import rpg.server.core.condition.ConditionHandler;
import rpg.server.core.condition.SimpleGameCondition;
import rpg.server.core.event.EventHandler;
import rpg.server.core.event.GameEvent;
import rpg.server.core.event.GameEvent.Channel;
import rpg.server.core.relation.AbstractRelation;
import rpg.server.core.relation.SOBRelationTag;
import rpg.server.core.script.GameScriptConfig;

public interface SimulateObject {
	// 关联对象///////////////////////////////////

	/**
	 * 获取指定关系且满足指定相互条件的关联对象
	 * 
	 * @param relation
	 *            以"."隔开的关联关系字符串（{@link SOBRelationTag}），例如：(self.)mate.team
	 * @return
	 */
	Set<SimulateObject> getRelatedSOBs(AbstractRelation relation);

	/**
	 * 根据关联关系标识获取关联对象（集合） <br/>
	 * 
	 * @param relations
	 * @return
	 */
	Set<SimulateObject> getRelatedSOBs(SOBRelationTag relation);

	// 事件///////////////////////////////////

	/**
	 * 注册事件处理器
	 * 
	 * @param type
	 * @param source
	 * @param agent
	 */
	void registerEventHandler(GameEvent.Type type, GameEvent.Channel source,
			EventHandler agent);

	/**
	 * 注销事件处理器
	 * 
	 * @param type
	 * @param source
	 * @param agent
	 */
	void removeEventHandler(GameEvent.Type type, GameEvent.Channel source,
			EventHandler agent);

	/**
	 * 注册批量事件处理器
	 * 
	 * @param types
	 *            需要注册的事件
	 * @param channel
	 *            事件源标识:self or neighbor
	 * @param handler
	 *            处理模块
	 */
	void registerEventHandlers(GameEvent.Type[] types, Channel channel,
			EventHandler handler);

	/**
	 * 注销批量事件处理器
	 * 
	 * @param types
	 * @param channel
	 * @param handler
	 */
	void removeEventHandlers(GameEvent.Type[] types, Channel channel,
			EventHandler handler);

	/**
	 * 接受事件
	 * 
	 * @param event
	 */
	void receiveEvent(GameEvent.Channel source, GameEvent event);

	/**
	 * 向指定的关联对象通报事件
	 * 
	 * @param related
	 * @param event
	 */
	void notifyEvent(String related, GameEvent event);

	// 条件///////////////////////////////////

	/**
	 * 注册条件检测器
	 * 
	 * @param classify
	 * @param agent
	 */
	void registerConditionChecker(String classify, ConditionHandler agent);

	/**
	 * 检测简单条件
	 * 
	 * @param condition
	 * @param vars
	 * @return
	 */
	boolean checkSimpleCondition(SimpleGameCondition condition,
			Map<String, Object> vars);

	// 行为///////////////////////////////////

	/**
	 * 注册行为执行器
	 * 
	 * @param classify
	 * @param agent
	 */
	void registerActionProcessor(String classify, ActionHandler agent);

	/**
	 * 执行动作，无临时变量
	 * 
	 * @param action
	 * @return
	 */
	boolean doSimpleAction(SimpleGameAction action);

	/**
	 * 执行简单行为
	 * 
	 * @param action
	 * @param vars
	 * @return
	 */
	boolean doSimpleAction(SimpleGameAction action, Map<String, Object> vars);

	// ///////////////////脚本///////////////////////////
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

	// /////////////////基本////////////////////////////////

	void remove();
	long getId();
}
